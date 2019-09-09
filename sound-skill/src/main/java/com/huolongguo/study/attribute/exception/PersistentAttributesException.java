package com.huolongguo.study.attribute.exception;

/**
 * @author Caris W
 */
public class PersistentAttributesException extends Exception {

    public PersistentAttributesException() {
    }

    public PersistentAttributesException(String message) {
        super(message);
    }

    public PersistentAttributesException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistentAttributesException(Throwable cause) {
        super(cause);
    }

    public PersistentAttributesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
