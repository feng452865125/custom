package com.chunhe.custom.framework.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.exception.RFException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.chunhe.custom.framework.exception.BadRequestException;
import com.chunhe.custom.framework.exception.ServerErrorException;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> badRequestHandler(BadRequestException e) {
        return e.errorObj();
    }

    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> serverError(ServerErrorException e) {
        return e.errorObj();
    }

    @ExceptionHandler(RFException.class)
    @ResponseBody
    public String sendToApp(RFException e) {
        return JSON.toJSONString(e.getResult());
    }

}
