package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class ContextInitException extends ControllerException {
    public ContextInitException() {
    }

    public ContextInitException(String message) {
        super(message);
    }

    public ContextInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextInitException(Throwable cause) {
        super(cause);
    }

    public ContextInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
