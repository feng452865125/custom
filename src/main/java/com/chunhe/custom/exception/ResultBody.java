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

    /*******************************************************************************************/

    /**
     * 成功
     * @param code
     * @param message
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
     * @param message
     * @param data
     * @return
     */
    public static ResultBody success(String message, Object data) {
        return success(CommonEnum.SUCCESS.getResultCode(), message, data);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        return success(CommonEnum.SUCCESS.getResultCode(), CommonEnum.SUCCESS.getResultMsg(), data);
    }

    /**
     * 成功
     * @param message
     * @return
     */
    public static ResultBody success(String message) {
        return success(CommonEnum.SUCCESS.getResultCode(), message, null);
    }

    /**
     * 成功
     * @return
     */
    public static ResultBody success() {
        return success(null);
    }


    /*******************************************************************************************/

    /**
     * 失败
     * @param code
     * @param message
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
     * @param message
     * @param data
     * @return
     */
    public static ResultBody error(String message, Object data) {
        return success(CommonEnum.ERROR.getResultCode(), message, data);
    }

    /**
     * 失败
     * @param data
     * @return
     */
    public static ResultBody error(Object data) {
        return success(CommonEnum.ERROR.getResultCode(), CommonEnum.ERROR.getResultMsg(), data);
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static ResultBody error(String message) {
        return success(CommonEnum.ERROR.getResultCode(), message, null);
    }

    /**
     * 失败
     * @return
     */
    public static ResultBody error() {
        return success(null);
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
