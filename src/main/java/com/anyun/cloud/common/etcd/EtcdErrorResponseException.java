package com.anyun.cloud.common.etcd;


import com.anyun.cloud.common.etcd.response.EtcdErrorResponse;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 24/05/2017
 */
public class EtcdErrorResponseException extends RuntimeException {
    private EtcdErrorResponse errorReponse;
    private boolean isJsonFormatException = false;
    /**
     *
     * @param message
     */
    public EtcdErrorResponseException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public EtcdErrorResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public EtcdErrorResponseException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param errorReponse
     */
    public EtcdErrorResponseException(EtcdErrorResponse errorReponse) {
        isJsonFormatException = true;
        this.errorReponse = errorReponse;
    }

    /**
     *
     * @return
     */
    public EtcdErrorResponse getErrorReponse() {
        return errorReponse;
    }

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        if (!isJsonFormatException)
            return super.getMessage();
        if (errorReponse == null)
            return "No message";
        return errorReponse.getMessage();
    }
}
