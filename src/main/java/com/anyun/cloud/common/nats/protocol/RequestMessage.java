package com.anyun.cloud.common.nats.protocol;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 24/11/2017
 */
public class RequestMessage<T> {
    @SerializedName("header")
    private MessageHeader messageHeader;
    @SerializedName("business")
    private String business;
    @SerializedName("content")
    private T content;

    public RequestMessage() {
    }

    public RequestMessage(MessageHeader messageHeader, String business, T content) {
        this.messageHeader = messageHeader;
        this.business = business;
        this.content = content;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "messageHeader=" + messageHeader +
                ", business='" + business + '\'' +
                ", content=" + content +
                '}';
    }
}
