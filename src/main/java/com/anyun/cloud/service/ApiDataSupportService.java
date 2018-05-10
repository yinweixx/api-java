package com.anyun.cloud.service;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.dao.ApiGatewayDao;
import com.anyun.cloud.dao.ApiManagementDao;
import com.anyun.cloud.dao.IpPoolDao;
import com.anyun.cloud.model.dto.ApiVerificationDto;
import com.anyun.cloud.model.dto.ApiVerificationQueryDto;
import com.anyun.cloud.model.dto.HostDistributionDto;
import com.anyun.cloud.model.entity.DataCenterEntity;
import com.anyun.cloud.model.entity.HostBaseEntity;
import com.anyun.cloud.model.entity.IpBlockEntity;
import com.anyun.cloud.model.entity.IpPoolEntity;
import com.anyun.cloud.model.param.ApiVerificationParam;
import com.anyun.cloud.model.param.HostDistributionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ApiDataSupportService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApiDataSupportService.class);
    private ApiGatewayDao apiGatewayDao;
    private ApiManagementDao apiManagementDao;
    private IpPoolDao ipPoolDao;

    public ApiDataSupportService() {
        apiGatewayDao = ControllerIOC.getIOC().getInstance(ApiGatewayDao.class);
        apiManagementDao = ControllerIOC.getIOC().getInstance(ApiManagementDao.class);
        ipPoolDao = ControllerIOC.getIOC().getInstance(IpPoolDao.class);
    }

    public String getApiVerification(String json) {
        ApiVerificationParam param = JsonUtil.fromJson(ApiVerificationParam.class, json);
        LOGGER.debug("param[{}]", param.asJson());
//        //验证url地址
//        String   resourceUrl=param.getUrl();
//        LOGGER.debug("resourceUrl:[{}]",resourceUrl);
//        StringBuffer  stringBuffer= new   StringBuffer(resourceUrl.substring(0,resourceUrl.indexOf("?")-1).trim()) ;
//        LOGGER.debug("stringBuffer:[{}]",stringBuffer.toString());

        //查询所有所有符合条件的api信息
        List<ApiVerificationQueryDto> list = apiGatewayDao.selectVerificationParam();
        LOGGER.debug("list:[{}]", JsonUtil.toJson(list));
        ApiVerificationDto verificationDto = new ApiVerificationDto();
        String sid = null;
        //如果有值
        if (list != null && list.size() != 0) {
            //遍历
            for (ApiVerificationQueryDto dto : list) {
//                String baseUrl = dto.getBaseUrl().replace("{version}", dto.getVersion())
//                        .replace(".", "_");
//                LOGGER.debug("baseUrl:[{}]",baseUrl);
//                //拼接baseUrl
//                String url = baseUrl+dto.getPath();
                String url = "/api"+ dto.getFinalPath();
                LOGGER.debug("url:[{}]", url);

                LOGGER.debug("paramUrl:[{}]", param.getUrl());
                //将符合条件的api信息放入新的集合中

                if (url.equals(param.getUrl()) && dto.getMethod().equals(param.getMethod())) {
                    LOGGER.debug("dto:[{}]", dto.asJson());
                    sid = dto.getServiceId();
                }
            }
            verificationDto.setServiceId(sid);
            verificationDto.setUrl(param.getUrl());
            LOGGER.debug("dtverificationDtoo:[{}]", verificationDto.asJson());
            return verificationDto.asJson();
        } else {
            return new ApiVerificationDto().asJson();
        }
    }

    public String getHostIp(String json) {
        HostDistributionParam param = JsonUtil.fromJson(HostDistributionParam.class, json);
        LOGGER.debug("param[{}]", param.asJson());
        //根据dns域名查询数据中心信息
        DataCenterEntity data = ipPoolDao.selectDataCenterByDns(param.getDnsname());
        LOGGER.debug("data[{}]",data.asJson());
       /* List<IpPoolEntity> ipPool = ipPoolDao.selectPoolIdByCondition(param);
        LOGGER.debug("ipPool[{}]", ipPool);
        List<IpBlockEntity> block = new ArrayList<>();
        if (ipPool != null && ipPool.size() != 0) {
            for (IpPoolEntity i : ipPool) {
                block = ipPoolDao.selectIpBlockByCondition(param.getBlockCategory(), i.poolId);
            }
        }*/
       if(data == null ){
           return  "没有此数据中心";
       }
        //根据条件查询宿主机信息
        List<HostBaseEntity> list = ipPoolDao.selectHostBase(param.getHostcategory(),param.getEnvironment(),data.getCenterName());
        LOGGER.debug("list[{}]", JsonUtil.toJson(list));
        List<HostDistributionDto> hl = new ArrayList<>();
        //筛选符合条件得宿主机ip放入集合中
        for (HostBaseEntity h: list) {
            HostDistributionDto dto = new HostDistributionDto();
            dto.setHostIp(h.getHostIp());
            LOGGER.debug("HostDistributionDto[{}]", JsonUtil.toJson(dto));
            hl.add(dto);
        }
        LOGGER.debug("hl[{}]", JsonUtil.toJson(hl));
        return JsonUtil.toJson(hl);
//        if(hl.size()>=param.getNumber()){
//            return JsonUtil.toJson(hl);
//        }else {
//            return JsonUtil.toJson(new ArrayList<HostDistributionDto>());
//        }
    }
}
