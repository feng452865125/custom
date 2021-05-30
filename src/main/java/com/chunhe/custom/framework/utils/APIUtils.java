package com.chunhe.custom.framework.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiumy on 2017/5/17.
 */
public class APIUtils {

    /**
     * 成功,APP调用接口返回code
     */
    public static final int STATUS_CODE_SUCCESS = 0;

    public static final int STATUS_CODE_FAIL = 1;

    /**
     * 失败，对应BJUI的错误弹窗
     */
    public static final String STATUS_CODE_ERROR = "300";

    /**
     * 超时，对应BJUI的超时弹窗
     */
    public static final String STATUS_CODE_TIMEOUT = "301";

    public static final String MESSAGE_CHECK_SUCCESS = "查询成功";
    public static final String MESSAGE_CREATE_SUCCESS = "添加成功";
    public static final String MESSAGE_SAVE_SUCCESS = "保存成功";
    public static final String MESSAGE_MODIFY_SUCCESS = "设置成功";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";

    public static final String MESSAGE_RELOGIN = "请重新登录";

    public static String getResult(String statusCode, String message) {
        Map map = new HashMap();
        map.put("statusCode", statusCode);
        map.put("message", message);
        return JSON.toJSONString(map);
    }

    public static String getResult(String statusCode, String message, Object object) {
        Map map = new HashMap();
        map.put("statusCode", statusCode);
        map.put("message", message);
        map.put("object", object);
        return JSON.toJSONString(map);
    }

}
