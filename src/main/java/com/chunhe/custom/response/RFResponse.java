//package com.chunhe.custom.response;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.Serializable;
//
///**
// * Created by qiumy on 2017/4/11.
// */
//@Getter
//@Setter
//public class RFResponse implements Serializable {
//    /**
//     * 成功
//     */
//    public static final int CODE_SUCCESS = 0;
//    /**
//     * 失败
//     */
//    public static final int CODE_FAIL = 1;
//    /**
//     * token失效
//     */
//    public static final int CODE_TOKEN_FAIL = 2;
//    /**
//     * 没有权限
//     */
//    public static final int CODE_UNPOWER = 3;
//    /**
//     * 参数验证失败
//     */
//    public static final int CODE_VALID_FAIL = 4;
//
//    private int code;
//
//    private String message;
//
//    private Object data;
//
//    /**
//     * 处理成功
//     */
//    public RFResponse() {
//    }
//
//    /**
//     * 根据状态码自动赋值message
//     */
//    public RFResponse(int code) {
//        this(code, null);
//    }
//
//    /**
//     * 处理成功
//     */
//    public RFResponse(Object data) {
//        this(CODE_SUCCESS, data);
//    }
//
//    /**
//     * 处理失败
//     */
//    public RFResponse(String message) {
//        this();
//        if (!StringUtils.isEmpty(message)){
//            this.code = RFResponse.CODE_FAIL;
//            this.message = message;
//        }
//    }
//
//
//    public RFResponse(int code, String message) {
//        this(code, message, null);
//    }
//
//    public RFResponse(int code, Object object) {
//        this(code, null, object);
//    }
//
//    public RFResponse(int code, String message, Object data) {
//        this.code = code;
//        this.data = data;
//        this.message = message;
//        if (StringUtils.isBlank(message)) {
//            initMsg();
//        }
//    }
//
//    public String getMessage() {
//        initMsg();
//        return message;
//    }
//
//    /**
//     * 判断result合法性
//     * 当message为空时，根据result给message赋值
//     */
//    private void initMsg() {
//        if (StringUtils.isBlank(message)) {
//            switch (code) {
//                case CODE_SUCCESS:
//                    this.message = "处理成功";
//                    break;
//                case CODE_TOKEN_FAIL:
//                    this.message = "token失效";
//                    break;
//                case CODE_UNPOWER:
//                    this.message = "没有权限";
//                    break;
//                case CODE_VALID_FAIL:
//                    this.message = "参数验证失败";
//                    break;
//                default:
//                    code = CODE_FAIL;
//                    this.message = "处理失败";
//            }
//        }
//    }
//}
