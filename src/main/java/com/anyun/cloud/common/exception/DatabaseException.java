package com.anyun.cloud.common.exception;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/12/2017
 */
public class DatabaseException extends ControllerException {
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
