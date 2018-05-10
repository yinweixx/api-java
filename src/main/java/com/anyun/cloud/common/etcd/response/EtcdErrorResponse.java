package com.anyun.cloud.common.etcd.response;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 24/05/2017
 */
public class EtcdErrorResponse extends AbstractEtcdResponse{
    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("message")
    private String message;

    @SerializedName("cause")
    private String cause;

    @SerializedName("index")
    private int index;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
