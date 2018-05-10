package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class ParamsBuilderException extends ControllerException {
    public ParamsBuilderException() {
    }

    public ParamsBuilderException(String message) {
        super(message);
    }

    public ParamsBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsBuilderException(Throwable cause) {
        super(cause);
    }

    public ParamsBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
