package com.anyun.cloud.common.sys;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * 统一返回格式
 */
public class Responses<T> extends AbstractEntity {


    /**
     * 头信息
     */
    private Header header;


    /**
     * 错误代码
     * 0   ：正确
     * 非0 ：错误
     */
    private int code;


    /**
     * 返回内容
     */
    private Result<T> result;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result<T> getResult() {
        return result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }
}
