package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.common.tools.IP2Long;
import com.anyun.cloud.dao.HostManagementDao;
import com.anyun.cloud.dao.IpBlockDao;
import com.anyun.cloud.dao.IpUseDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.HostBaseEntity;
import com.anyun.cloud.model.entity.IpBlockEntity;
import com.anyun.cloud.model.entity.IpUseEntity;
import com.anyun.cloud.model.param.IpUseCreateParam;
import com.anyun.cloud.service.IpUseService;
import com.google.inject.Inject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/1 20:15
 */
public class IpUseServiceImpl implements IpUseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(IpUseServiceImpl.class);
    private IpUseDao ipUseDao;
    private IpBlockDao ipBlockDao;
    private HostManagementDao hostManagementDao;
    private Context context;
    private Response response;

    @Inject
    public IpUseServiceImpl(Context context) {
        this.context = context;
        this.ipUseDao = context.getBeanByClass(IpUseDao.class);
        this.ipBlockDao = context.getBeanByClass(IpBlockDao.class);
        this.hostManagementDao=context.getBeanByClass(HostManagementDao.class);
    }

    @Override
    public Response getDetails(int id) {

        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<IpUseEntity>();
            IpUseEntity ipUseEntity = ipUseDao.selectDetailsById(id);

            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipUseEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;

    }

    @Override
    public Response getIpList(int count, int blockId,String des) {
        List<String> ipList = new ArrayList<>();
        IpBlockEntity p = ipBlockDao.selectDetailsById(blockId);
        if (p == null) {
            response = new <String>Response();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipList);
            return response;
        }
        String start = p.getBlockStartIp();
        long st = IP2Long.ipToLong(start);
        String end = p.getBlockEndIp();
        long en = IP2Long.ipToLong(end);
        LOGGER.debug("st----------------------------------------------------:[{}]", st);
        LOGGER.debug("en----------------------------------------------------:[{}]", en);
        LOGGER.debug("start----------------------------------------------------:[{}]", start);
        LOGGER.debug("end----------------------------------------------------:[{}]", end);
            for (long s = st; s <en+1; s++) {
                if(ipList!=null){
                    if(ipList.size()==count){
                        break;
                    }
                }
                String ip = IP2Long.longToIP(s);
                IpUseEntity ipUseEntity = ipUseDao.selectIpUseEntityByBlockIdAndIp(blockId, ip);
                LOGGER.debug("IpUseEntity ipUseEntity:[{}]",ipUseEntity);
                if (ipUseEntity != null){
                    LOGGER.debug("IpUseEntity ipUseEntity:[{}]",ipUseEntity.asJson());
                    continue;
                }
                IpUseEntity ipUseEntity1 = new IpUseEntity();
                ipUseEntity1.setBlockId(blockId);
                ipUseEntity1.setDes(des);
                Date date =new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date1 = sdf.format(date);
                ipUseEntity1.setCreateDate(date1);
                //获取分配的IP
                ipUseEntity1.setIp(ip);
                ipUseDao.insert(ipUseEntity1);
                ipList.add(ip);
            }
        response = new Response<List<String>>();
        response.setCode(ErrorCode.NO_ERROR.code());
        response.setContent(ipList);
        return response;

    }
    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection, int blockId) {
        try {
            response = new Response<PageDto<IpUseEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipUseDao.selectPageList(index, limit, sortBy, sortDirection,blockId ));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
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
            response = new Response<IpUseEntity>();
            ipUseDao.deleteById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(null);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;

    }


}
