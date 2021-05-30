/**
 * Copyright © 2014-2016 杭州沃赛科技有限公司 All Rights Reserved. 浙ICP备15001708号
 */
package com.chunhe.custom.framework.exception;

import com.chunhe.custom.framework.controller.ExceptionHandlerAdvice;
import com.chunhe.custom.framework.response.RFResponse;

/**
 * 在Controller中拦截，返回JSON字符串
 * @see ExceptionHandlerAdvice
 */
public class RFException extends RuntimeException {

    private RFResponse result;

    public RFException() {
        result = new RFResponse();
    }

    public RFException(String message) {
        result = new RFResponse(message);
    }

    public RFException(int code) {
        result = new RFResponse(code);
    }

    public RFException(int code, String message) {
        result = new RFResponse(code, message);
    }

    /**
     * 接口成功
     */
    public RFException(Object object) {
        result = new RFResponse(object);
    }

    public RFException(int code, Object object) {
        result = new RFResponse(code, object);
    }


    public RFException(int code, String message, Object object) {
        result = new RFResponse(code, message, object);
    }

    public RFResponse getResult() {
        return result;
    }
}
