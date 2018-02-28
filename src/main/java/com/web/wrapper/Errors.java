package com.web.wrapper;

/**
 * Created on 02.09.15.
 */
public class Errors {

    String message;

    String StackTrace;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }
}
