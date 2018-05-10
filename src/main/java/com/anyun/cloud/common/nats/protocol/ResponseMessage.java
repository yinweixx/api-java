package com.anyun.cloud.common.nats.protocol;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 24/11/2017
 */
public class ResponseMessage<T> {
    @SerializedName("header")
    private MessageHeader messageHeader;
    @SerializedName("code")
    private long code;
    @SerializedName("result")
    private RespResult<T> result;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public RespResult<T> getResult() {
        return result;
    }

    public void setResult(RespResult<T> result) {
        this.result = result;
    }

    public static class RespResult<T> {
        @SerializedName("type")
        private String type;
        @SerializedName("content")
        private T content;

        public RespResult(String type, T content) {
            this.type = type;
            this.content = content;
        }

        public RespResult() {
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }
    }
}

