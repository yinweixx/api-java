package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;

import com.anyun.cloud.common.tools.IP2Long;
import com.anyun.cloud.dao.*;

import com.anyun.cloud.dao.impl.AbstractIciqlDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.host.Host;
import com.anyun.cloud.model.entity.*;

import com.anyun.cloud.model.param.DataCenterCreateParam;
import com.anyun.cloud.model.param.DataCenterUpdateParam;
import com.anyun.cloud.service.DataCenterService;


import com.anyun.cloud.service.HostManagementService;

import com.google.inject.Inject;
import com.iciql.SubQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 9:54
 */
public class DataCenterServiceImpl extends AbstractIciqlDao implements DataCenterService{
    private final static Logger LOGGER = LoggerFactory.getLogger(DataCenterService.class);
    private HostManagementService hostManagementService;
    private  DataCenterDao dataCenterDao;
    private  Response response;
    private  Context context;
    private HostManagementDao hostManagementDao;
    @Inject
    public DataCenterServiceImpl(Context context ,Database database)
    {
        super(database);
        this.context = context;
        this.dataCenterDao= context.getBeanByClass(DataCenterDao.class);
        this.hostManagementDao=context.getBeanByClass(HostManagementDao.class);
        this.hostManagementService=context.getBeanByClass(HostManagementService.class);
    }
    @Override
    public Response getDetails(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id==0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
            return response;
        }

        try {
            response = new Response<DataCenterEntity>();
            DataCenterEntity dataCenterEntity = dataCenterDao.selectDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(dataCenterEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }


    @Override
    public Response delete(int id) {

        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<DataCenterEntity>();
            long ipUsed=dataCenterDao.selectIpUsedByCenterId(id);
            if(ipUsed == 0) {
                dataCenterDao.deleteById(id);
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(null);
                return response;
            }
            else{
                response = new Response<String>();
                response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() +":"+"此数据中心存在已使用IP，不能删除");
                return response;
            }
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;

    }

    @Override
    public Response create(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        DataCenterCreateParam param;
        try {
            param = JsonUtil.fromJson(DataCenterCreateParam.class, body);

            }
         catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }

        try {
            response = new Response<>();
            DataCenterEntity dataCenterEntity=dataCenterDao.selectDataCenterByCategory(param.getCenterCategory());
            if(param.getCenterCategory().equals("主中心")&& dataCenterEntity != null){
                response = new Response<String>();
                response.setCode(ErrorCode.PARAMETER_ERROR.code());
                response.setContent(ErrorCode.PARAMETER_ERROR.name() + "已存在主中心，且主中心只有一个，不能再增设主中心");
                return response;}
                else {
                DataCenterEntity p = new DataCenterEntity();
                p.setCenterName(param.getCenterName());
                p.setDnsName(param.getDnsName());
                p.setCenterAddress(param.getCenterAddress());
                p.setCenterCategory(param.getCenterCategory());
                p = dataCenterDao.insert(p);
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(p);
                return response;
            }
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }


    @Override
    public Response update(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        DataCenterEntity p = new DataCenterEntity();
        try {
            DataCenterUpdateParam up = JsonUtil.fromJson(DataCenterUpdateParam.class, body);
            p.setCenterId(up.getCenterId());
            p.setCenterName(up.getCenterName());
            p.setDnsName(up.getDnsName());
            p.setCenterAddress(up.getCenterAddress());
            p.setCenterCategory(up.getCenterCategory());
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "格式转换错误！");
            return response;
        }

        try {
            response = new Response<DataCenterEntity>();
            response.setContent(dataCenterDao.update(p));
            response.setCode(ErrorCode.NO_ERROR.code());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<DataCenterEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(dataCenterDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getHostsDetails(int id) {
        if (id==0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
            return response;
        }

        try {
            response = new Response<DataCenterEntity>();
            DataCenterEntity p = new DataCenterEntity();
            IpPoolEntity s = new IpPoolEntity();
            IpBlockEntity b = new IpBlockEntity();
            IpUseEntity u=new IpUseEntity();
            //Ip数目统计
            LOGGER.debug("id:------------------------------------------------------------[{}]", id);
            p.setCenterId(id);
            SubQuery<IpPoolEntity, Integer> i = db.from(s).where(s.environment).is("测试环境").and(s.centerId).is(id).subQuery(s.getPoolId());
            SubQuery<IpBlockEntity, Integer> q = db.from(b).where(b.poolId).in(i).subQuery(b.blockId);
             long testIpUseCount = db.from(u).where(u.blockId).in(q).selectCount();
            //测试环境下已分配IP数目
            long begin = System.currentTimeMillis();
            List<IpBlockEntity> list = db.from(b).where(b.blockId).in(q).select();
            LOGGER.debug("list:[{}]",JsonUtil.toJson(list));
            long testHostsNum = 0;
            long testHostsNum1 = 0;
            long testIpTotal = 0;
            List<Host> list2 = hostManagementService.getHosts();
            LOGGER.debug("Time1:----------------------------[{}]",System.currentTimeMillis()-begin);
            for (IpBlockEntity blockEntity : list) {
                if (blockEntity == null)
                    continue;
                Long start = IP2Long.ipToLong(blockEntity.getBlockStartIp());
                Long end = IP2Long.ipToLong(blockEntity.getBlockEndIp());
                long sum = end - start + 1;
                LOGGER.debug("sum--------------------------------------[{}]", sum);
                 testIpTotal = testIpTotal + sum;
                HostBaseEntity h = new HostBaseEntity();
                List<HostBaseEntity> hostBaseEntities = db.from(h).select();
                LOGGER.debug("Time2:----------------------------[{}]",System.currentTimeMillis()-begin);

                   for (HostBaseEntity host : hostBaseEntities) {
                 String a = host.getHostIp();
                 LOGGER.debug("a------------------------------------------------:[{}]", a);
                 Long ip = IP2Long.ipToLong(a);
                 if (ip <= end && ip >= start) {
                     testHostsNum++;
                     LOGGER.debug("ip------------------------------------------------:[{}]", ip);
                     for (Host ho : list2) {
                       if(a.equals(ho.getAddress())){
                           testHostsNum1++;
                     }
                  }

                 }

                }

            }
            LOGGER.debug("Time3:----------------------------[{}]",System.currentTimeMillis()-begin);
            //数据中心测试环境宿主机数目
            p.setTestHostsNum(testHostsNum);
            //数据中心下测试环境IP数目
            p.setTestIpTotal(testIpTotal);
            //测试环境下正常运行宿主机数目
            p.setTestHostsNum1(testHostsNum1);
            //测试环境下已使用IP总数
            p.setTestIpUseTotal(testIpUseCount );

            SubQuery<IpPoolEntity, Integer> m = db.from(s).where(s.environment).is("生产环境").and(s.centerId).is(id).subQuery(s.getPoolId());
            LOGGER.debug("subQuery1:[{}]", m.toString());
            SubQuery<IpBlockEntity, Integer> n = db.from(b).where(b.poolId).in(m).subQuery(b.blockId);
            LOGGER.debug("subQuery2:[{}]", n.toString());

            long productIpUseCount = db.from(u).where(u.blockId).in(n).selectCount();
            //生产环境下已分配IP数目
            long productIpTotal = 0;
            long productHostsNum = 0;
            long productHostsNum1 = 0;
            List<IpBlockEntity> list1 = db.from(b).where(b.blockId).in(n).select();
            for (IpBlockEntity blockEntity : list1) {
                if (blockEntity == null)
                    continue;
                Long start = IP2Long.ipToLong(blockEntity.getBlockStartIp());
                Long end = IP2Long.ipToLong(blockEntity.getBlockEndIp());
                long sum = end - start + 1;
                LOGGER.debug("sum--------------------------------------[{}]", sum);
                productIpTotal = productIpTotal + sum;
                HostBaseEntity h = new HostBaseEntity();
                List<HostBaseEntity> hostBaseEntities = db.from(h).select();
                for (HostBaseEntity host : hostBaseEntities) {

                    String c = host.getHostIp();
                    Long ip = IP2Long.ipToLong(c);
                    if (ip <= end && ip >= start) {
                        productHostsNum++;
                        for (Host ho : list2) {
                            if (c.equals(ho.getAddress())) {
                                productHostsNum1++;

                            }
                        }

                    }
                }
                }

                //生产环境下IP总数
                p.setProductIpTotal(productIpTotal);
                //生产环境下已使用IP数目
                p.setProductIpUseTotal(productIpUseCount);
                //生产环境下宿主机数目
                p.setProductHostsNum(productHostsNum);
                //生产环境下正常运行宿主机数目
                p.setProductHostsNum1(productHostsNum1);
                //测试环境已使用IP所占百分比
                /*
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(0);*/
              //  DecimalFormat df = new DecimalFormat();
                LOGGER.debug("testIpTotal--------------------------------------[{}]", testIpTotal);
                     if(testIpTotal == 0){
                         p.setTestPct("0");
                     }else {
                         String testPercent = new DecimalFormat("0").format(((double) testIpUseCount / (double) testIpTotal * 100));
                         float testPer = (float) testIpUseCount / (float) testIpTotal * 100;
                         if (testPer > 0 && testPer < 1) {
                             p.setTestPct("1");
                         } else {
                             p.setTestPct(testPercent);
                         }
                     }

                //生产环境下已使用IP所占百分比
                     if(productIpTotal==0){
                         p.setProductPct("0");
                     }
                     else {
                         String productPercent = new DecimalFormat("0").format(((double) productIpUseCount / (double) productIpTotal * 100));
                         float proPer = (float) productIpUseCount / (float) productIpTotal * 100;
                         if (proPer > 0 && proPer < 1) {
                             p.setProductPct("1");
                         } else {
                             p.setProductPct(productPercent);

                         }
                     }

            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(p);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

}
