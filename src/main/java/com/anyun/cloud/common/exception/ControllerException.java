package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/12/2017
 */
public class ControllerException extends RuntimeException{
    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
