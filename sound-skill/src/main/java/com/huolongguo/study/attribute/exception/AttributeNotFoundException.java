package com.huolongguo.study.attribute.exception;

/**
 * @author Caris W
 */
public class AttributeNotFoundException extends Exception {

    public AttributeNotFoundException() {
    }

    public AttributeNotFoundException(String message) {
        super(message);
    }

    public AttributeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttributeNotFoundException(Throwable cause) {
        super(cause);
    }

    public AttributeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
