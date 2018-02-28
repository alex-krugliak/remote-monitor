package com.web.exception;

/**
 * Created by alex on 18.12.17.
 */
public class ComportException extends RuntimeException {

    public ComportException() {
    }

    public ComportException(String message) {
        super(message);
    }

    public ComportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComportException(Throwable cause) {
        super(cause);
    }

    public ComportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
