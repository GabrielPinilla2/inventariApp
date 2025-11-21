package com.erp.inventariapp.exceptions;

public class ResourceDeleteException extends RuntimeException {
    public ResourceDeleteException(String message, Exception e){
        super(message, e);
    }
}
