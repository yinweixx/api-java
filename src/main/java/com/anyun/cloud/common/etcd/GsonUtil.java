package com.anyun.cloud.common.etcd;


import com.anyun.cloud.common.etcd.response.EtcdActionNode;
import com.anyun.cloud.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.common.etcd.response.EtcdErrorResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class GsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtil.class);
    private static GsonUtil util;
    private Gson gson;

    private GsonUtil() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    public static GsonUtil getUtil() {
        synchronized (GsonUtil.class) {
            if (util == null)
                util = new GsonUtil();
        }
        return util;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * @param jsonResponse
     * @return
     * @throws EtcdErrorResponseException
     */
    public EtcdActionResponse getReponseEntity(String jsonResponse)
            throws EtcdErrorResponseException {
        Gson gson = GsonUtil.getUtil().getGson();
        try {
            if (jsonResponse.startsWith("{\"errorCode\":")) {
                EtcdErrorResponse errorResponse = gson.fromJson(jsonResponse, EtcdErrorResponse.class);
                throw new EtcdErrorResponseException(errorResponse);
            }
            return gson.fromJson(jsonResponse, EtcdActionResponse.class);
        } catch (JsonSyntaxException ex) {
            LOGGER.error("Json format error: {}", ex.getMessage(), ex);
            throw new EtcdErrorResponseException(ex.getMessage(), ex);
        }
    }

    /**
     * @param response
     * @return
     * @throws EtcdErrorResponseException
     */
    public String getReponseNodeValue(EtcdActionResponse response) throws EtcdErrorResponseException {
        EtcdActionNode node = response.getActionNode();
        if (node == null)
            throw new EtcdErrorResponseException("Node is null");
        return node.getValue();
    }

    public Gson getGson() {
        return gson;
    }

}
