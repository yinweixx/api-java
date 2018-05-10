package com.anyun.cloud.model;

/**
 * 错误代码类
 */
public enum ErrorCode {
    /**
     * 没有错误
     */
    NO_ERROR(0),


    /**
     * 未知错误
     */
    UNKNOWN_ERROR(1000),


    /**
     * 参数错误
     */
    PARAMETER_ERROR(2000),


    /**
     *
     * 资源不存在
     *
     */
    RESOURCE_NO_EXIST_ERROR(3000),


    /**
     * 查询资源出错
     */
    QUERY_RESOURCE_ERROR(3001),


    /**
     * 修改资源出错
     */
    POST_RESOURCE_ERROR(3002),


    /**
     * 删除资源出错
     */
    DELETE_RESOURCE_ERROR(3003),


    /**
     * 新增资源出错
     */
    PUT_RESOURCE_ERROR(3004),


    /**
     * 业务处理出错
    */
    BUSINESS_PROCESS_ERROR(5001);


    /**
     * 错误代码
     */
    private ErrorCode(int code) {
        this.code = code;
    }


    /**
     * 错误代码
     *
     * @param code
     * @return
     */
    private int code;

    public int code() {
        return code;
    }

}
