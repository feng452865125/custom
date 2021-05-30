package com.chunhe.custom.exception;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.exceptionEnum.CommonEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultBody {

    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody() {

    }

    public ResultBody(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ResultBody success(String code, String message, Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(data);
        return rb;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultBody success() {
        return success(null);
    }


    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        return success(CommonEnum.SUCCESS.getResultCode(), CommonEnum.SUCCESS.getResultMsg(), data);
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultBody success(String message, Object data) {
        return success(CommonEnum.SUCCESS.getResultCode(), message, data);
    }


    /**
     * 失败
     */
    public static ResultBody error(BaseErrorInfoInterface errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     *
     * @param data
     * @return
     */
    public static ResultBody error(String code, String message, Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(String code, String message) {
        return error(code, message, null);
    }

    /**
     * 失败
     */
    public static ResultBody error(String message) {
        return error(CommonEnum.ERROR_PARAM.getResultCode(), message, null);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
