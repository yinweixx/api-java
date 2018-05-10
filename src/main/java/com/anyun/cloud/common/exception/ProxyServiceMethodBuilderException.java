package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 11/12/2017
 */
public class ProxyServiceMethodBuilderException extends ControllerException {
    public ProxyServiceMethodBuilderException() {
    }

    public ProxyServiceMethodBuilderException(String message) {
        super(message);
    }

    public ProxyServiceMethodBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProxyServiceMethodBuilderException(Throwable cause) {
        super(cause);
    }

    public ProxyServiceMethodBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
