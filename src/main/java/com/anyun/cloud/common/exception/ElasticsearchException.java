package com.anyun.cloud.common.exception;

/**
 * @author Shen Xutao
 * @project api-management
 * @email 314255358@qq.com
 * @date 2018/3/26 14:40 星期一
 */
public class ElasticsearchException extends ControllerException {
    public ElasticsearchException() {
    }

    public ElasticsearchException(String message) {
        super(message);
    }

    public ElasticsearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticsearchException(Throwable cause) {
        super(cause);
    }

    public ElasticsearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
