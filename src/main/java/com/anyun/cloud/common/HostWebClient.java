package com.anyun.cloud.common;

import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sxt on 8/28/17.
 */
public class HostWebClient {
    private static Logger LOGGER = LoggerFactory.getLogger(HostWebClient.class);
    private String rootUrl;
    private Client rsClient;
    private WebTarget webTarget;

    private HostWebClient(String rootUrl) {
        this.rootUrl = rootUrl;
        rsClient = ClientBuilder.newClient();
    }

    public static HostWebClient build(Configuration configuration) {
        StringBuilder rootUrl = new StringBuilder();
        rootUrl.append(configuration.isHttps() ? "https://" : "http://");
        rootUrl.append(configuration.getPlatformAddress());
        rootUrl.append(":").append(configuration.getPort());
        rootUrl.append(configuration.getBaseUrl());
        return new HostWebClient(rootUrl.toString());
    }

    public String get(String path, Map<String, Object> params) throws Exception {
        LOGGER.debug("ROOT URL [{}]", rootUrl);
        LOGGER.debug("API path [{}]", rootUrl + path);
        webTarget = rsClient.target(rootUrl).path(path);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }
        LOGGER.debug("webTarget.getUri():{}",webTarget.getUri());
        Invocation.Builder builder = webTarget.request();
        Response response = builder.get();
        return readResponse(response);
    }

    public String put(String path, Object content) throws Exception {
        LOGGER.debug("ROOT URL [{}]", rootUrl);
        LOGGER.debug("API path [{}]", rootUrl + path);
        webTarget = rsClient.target(rootUrl).path(path);
        Invocation.Builder builder = webTarget.request();
        Entity entity = Entity.entity(content, MediaType.APPLICATION_JSON);
        Response response = builder.put(entity);
        return readResponse(response);
    }

    public String delete(String path, Map<String, Object> params) throws Exception {
        LOGGER.debug("ROOT URL [{}]", rootUrl);
        LOGGER.debug("API path [{}]", rootUrl + path);
        webTarget = rsClient.target(rootUrl).path(path);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }
        Invocation.Builder builder = webTarget.request();
        Response response = builder.delete();
        return readResponse(response);
    }

    public String post(String path, Object content) throws Exception {
        LOGGER.debug("ROOT URL [{}]", rootUrl);
        LOGGER.debug("API path [{}]", rootUrl + path);
        webTarget = rsClient.target(rootUrl).path(path);
        Invocation.Builder builder = webTarget.request();
        Entity entity = Entity.entity(content, MediaType.APPLICATION_JSON);
        Response response = builder.post(entity);
        return readResponse(response);
    }

    public static <T> T convertToAnyunEntity(Class<T> type, String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            String contentJson = root.findValue("success").findValue("content").toString();
            return mapper.readValue(contentJson, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> convertToListObject(Class<T> type, String response) {
        List<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            String contentJson = root.findValue("success").findValue("content").toString();
            List<Map<String, Object>> entities = mapper.readValue(contentJson, List.class);
            for (Map<String, Object> entry : entities) {
                T _entry = new ObjectMapper().readValue(toJson(entry), type);
                list.add(_entry);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return list;
        }
        return list;
    }

    public static String toJson(Object obj) {
        try {
            GsonBuilder ex = new GsonBuilder();
            ex.disableHtmlEscaping();
            ex.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            ex.setPrettyPrinting();
            Gson gson = ex.create();
            return gson.toJson(obj);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private String readResponse(Response response) throws Exception {
        if (response.getStatus() != 200) {
            LOGGER.error("Http Response Code [{}]", response.getStatus());
            throw new Exception("Server error : " + response.readEntity(String.class));
        }
        String responseEntity = response.readEntity(String.class);
        return responseEntity;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public Client getRsClient() {
        return rsClient;
    }

    public WebTarget getWebTarget() {
        return webTarget;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public static class Configuration extends AbstractEntity {
        private String platformAddress;
        private int port = 8080;
        private String baseUrl = "/api/v1.0";
        private boolean https = false;

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public static Configuration buildConfiguration(String jsonBody) {
            return JsonUtil.fromJson(Configuration.class, jsonBody);
        }

        public Configuration() {
        }

        public String getPlatformAddress() {
            return platformAddress;
        }

        public void setPlatformAddress(String platformAddress) {
            this.platformAddress = platformAddress;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public boolean isHttps() {
            return https;
        }

        public void setHttps(boolean https) {
            this.https = https;
        }
    }
}
