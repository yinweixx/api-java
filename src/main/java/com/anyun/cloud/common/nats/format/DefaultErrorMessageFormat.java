package com.anyun.cloud.common.nats.format;

import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.nats.protocol.ResponseMessage;

public class DefaultErrorMessageFormat extends AbstractAnyunCloudMessageFormat {

    @Override
    public byte[] format(String business, Object obj) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(buildHeader());
        responseMessage.setCode(503);
        ResponseMessage.RespResult result = buildErrorResult(business, (Exception) obj);
        responseMessage.setResult(result);
        String json = JsonUtil.toJson(responseMessage);
        return json.getBytes();
    }

    private ResponseMessage.RespResult buildErrorResult(String business, Exception ex) {
        ResponseMessage.RespResult result = new ResponseMessage.RespResult();
        result.setContent(ex.getMessage());
        result.setType(business);
        return result;
    }

    @Override
    public String getApplication() {
        return "container-controller";
    }
}
