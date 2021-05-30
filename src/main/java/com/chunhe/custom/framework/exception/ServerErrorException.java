package com.chunhe.custom.framework.exception;

import java.util.Map;


public class ServerErrorException extends GlobalException {

    public static final int REQUEST_CODE = 500;

    public ServerErrorException(String message) {
        super(REQUEST_CODE, message);
    }

    @Override
    protected void extern(Map<String, Object> errorObj) {
    }

}
