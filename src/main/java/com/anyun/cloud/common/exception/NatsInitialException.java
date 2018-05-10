package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class NatsInitialException extends ControllerException {
    public NatsInitialException() {
    }

    public NatsInitialException(String message) {
        super(message);
    }

    public NatsInitialException(String message, Throwable cause) {
        super(message, cause);
    }

    public NatsInitialException(Throwable cause) {
        super(cause);
    }

    public NatsInitialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
