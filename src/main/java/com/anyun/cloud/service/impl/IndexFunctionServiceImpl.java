package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.IndexFunctionDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.IndexFunctionDto;
import com.anyun.cloud.model.entity.DataCenterEntity;
import com.anyun.cloud.model.entity.ServiceEntity;
import com.anyun.cloud.service.IndexFunctionService;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.options.GetOption;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexFunctionServiceImpl implements IndexFunctionService {
    private final static Logger LOGGER = LoggerFactory.getLogger(IndexFunctionServiceImpl.class);
    private Context context;
    private Response response;
    private IndexFunctionDao indexFunctionDao;
    static Etcd etcd;

    @Inject
    public IndexFunctionServiceImpl(Context context) {
        this.context = context;
        this.indexFunctionDao=context.getBeanByClass(IndexFunctionDao.class);
        this.etcd = context.getEtcd();
    }


    @Override
    public Response getFunctionSituation(int id) {
        try {
            DataCenterEntity da = indexFunctionDao.selectDateNameById(id);
            IndexFunctionDto dto =indexFunctionDao.selectFunctionNum();
            try {
                List<KeyValue> list = etcd.getWithPrefix("/container_services/", true);
                LOGGER.debug("list:[{}]", JsonUtil.toJson(list));
                if (list !=null){
                    List<String> sl=new ArrayList<>();
                    list.forEach(l -> {
                        String s = new String(l.getKey().getBytes());
                        LOGGER.debug("------s:[{}]", s);
                        String ser = s.split("/container_services/")[1].split("/")[0];
                        LOGGER.debug("------ser:[{}]", ser);
                        String vl = new String(etcd.getWithPrefix(s,false).get(0).getValue().getBytes());
                        LOGGER.debug("------vl:[{}]", vl);
                        if(sl.isEmpty()){
                            sl.add(ser);
                            LOGGER.debug("--sl.size--:[{}]", sl.size());
                        }else{
                            if(!sl.contains(ser)){
                                sl.add(ser);
                                LOGGER.debug("--sl.size1--:[{}]", sl.size());
                            }
                        }
                    });
                    int test = 0;
                    int testNode = 0;
                    for (String sid: sl) {
                        Boolean  environment = indexFunctionDao.selectEnvironmentBySid(sid);
                        LOGGER.debug("--environment--:[{}]",environment);
                        if (environment == true) {
                            test=test+1;
                            LOGGER.debug("------test:[{}]", test);
                            List<KeyValue> tl = etcd.getWithPrefix("/container_services/"+sid, true);
                            if (tl != null){
                                testNode =testNode+tl.size();
                                LOGGER.debug("------test:[{}]", test);
                            }
                        }

                    }

                    dto.setTestserviceNode(testNode);
                    dto.setServiceNode(list.size()-testNode);
                    dto.setTestservice(test);
                    dto.setService(sl.size()-test);
                    dto.setDataCenter(da.getCenterName());
                    LOGGER.debug("dto:[{}]", JsonUtil.toJson(dto));
                    response = new Response< IndexFunctionDto>();
                    response.setCode(ErrorCode.NO_ERROR.code());
                    response.setContent(dto);
                    return response;
                }else {
                    response = new Response<String>();
                    response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + "暂无数据");
                    return response;
                }
            }catch (Exception e){
                response = new Response<String>();
                response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + "etcd暂无数据");
                return response;
            }
        }catch (Exception e){
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }

      /*  String key="/discovery/node/";
        List<KeyValue> list = etcd.getWithPrefix(key, true);
        LOGGER.debug("list:[{}]", JsonUtil.toJson(list));
        List<IndexFunctionDto> dtos = new ArrayList<>();
        *//*list.forEach(l -> {
            String s = new String(l.getKey().getBytes());
            LOGGER.debug("------s:[{}]", s);


        });*//*
        for (IndexFunctionDto dto: dtos) {
            dto.setService(String.valueOf(list.size()));
            dto.setTask(String.valueOf(0));
            dto.setApplication(String.valueOf(0));
            dto.setServiceNode(String.valueOf(0));
            dto.setTaskNode(String.valueOf(0));
            dto.setApplicationNode(String.valueOf(0));
            LOGGER.debug("dto:[{}]",dto);
            dtos.add(dto);
        }
        LOGGER.debug("dtos:[{}]",dtos);*/

    }
}
