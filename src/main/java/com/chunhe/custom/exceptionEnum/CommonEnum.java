package com.chunhe.custom.exceptionEnum;

import com.chunhe.custom.exception.BaseErrorInfoInterface;

public enum CommonEnum implements BaseErrorInfoInterface {

    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    ERROR_SIGN("401", "签名过期，重新登录!"),
    ERROR_PARAM("406", "请求的数据或格式错误!"),
    ERROR_BEAN("406", "请求的对象错误或不存在!"),
    NOT_FOUND("404", "未找到该资源!"),
    ERROR_SERVE("500", "服务器内部错误!"),
    BUSY_SERVE("503", "服务器繁忙，请稍后再试!");

    /**
     * 返回码
     */
    private String resultCode;

    /**
     * 返回描述
     */
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}
