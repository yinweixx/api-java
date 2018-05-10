package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class EtcdException extends ControllerException {
    public EtcdException() {
    }

    public EtcdException(String message) {
        super(message);
    }

    public EtcdException(String message, Throwable cause) {
        super(message, cause);
    }

    public EtcdException(Throwable cause) {
        super(cause);
    }

    public EtcdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
