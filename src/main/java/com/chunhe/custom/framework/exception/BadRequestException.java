package com.chunhe.custom.framework.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Getter
public class BadRequestException extends GlobalException {

    public static final int REQUEST_CODE = 1;

    private String parameterKey;

    public BadRequestException(String message) {
        this(null, message);
    }

    public BadRequestException(String parameterKey, String message) {
        super(REQUEST_CODE, message);
        this.parameterKey = parameterKey;
    }

    @Override
    protected void extern(Map<String, Object> errorObj) {
        if (StringUtils.isNotBlank(parameterKey)) {
            errorObj.put("parameterKey", parameterKey);
        }
    }
}
