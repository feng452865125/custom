package com.chunhe.custom.framework.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class GlobalException extends RuntimeException {

    private final int code;
    private final String message;
    private Map<String, Object> errorObj = new HashMap<>();

    public Map<String, Object> errorObj() {
        errorObj.put("code", code);
        errorObj.put("message", message);
        extern(errorObj);
        return errorObj;
    }

    protected abstract void extern(Map<String, Object> errorObj);

}
