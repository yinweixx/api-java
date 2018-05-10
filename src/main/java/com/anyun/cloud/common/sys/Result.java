package com.anyun.cloud.common.sys;

public class Result<T> {

    /*
    *
    * 返回类型
    *
    * code=0  为  T
    * code!=0 为  String
    *
    */
    String type;

    /*
    * 内容
    *
    * code=0   为正确 内容
    *
    * code！=0 为 String 类型 错误内容
    *
    */
    private T content;

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
