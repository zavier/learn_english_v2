package com.zavier.lenglish.common;

public class BusinessProcessException extends RuntimeException {
    public BusinessProcessException() {
        super();
    }

    public BusinessProcessException(String message) {
        super(message);
    }

    public BusinessProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessProcessException(Throwable cause) {
        super(cause);
    }

    protected BusinessProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
