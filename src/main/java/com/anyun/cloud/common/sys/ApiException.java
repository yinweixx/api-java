package com.anyun.cloud.common.sys;

/**
 * Created by sxt on 8/28/17.
 */
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public Response<ApiException> buildJson(String type) {
        Response<ApiException> response = new Response<>();
//        response.setException(this);
//        response.setType(type);
        return response;
    }
}
