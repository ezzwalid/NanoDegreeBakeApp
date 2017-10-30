package com.ezz.bakeappudacity.base.exception;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class UiException extends Exception{
    private String message;

    public UiException(String detailMessage) {
        super(detailMessage);
        this.message = detailMessage;
    }

    public UiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.message = detailMessage;
    }

    public UiException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }
}
