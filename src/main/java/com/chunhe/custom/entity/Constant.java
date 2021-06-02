package com.chunhe.custom.entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: white
 * @Date: 2021/05/16/12:30
 * @Description:
 */
public class Constant {

    /****其他配置**********************************************************/

    //登陆类型，0后端，1前端
    public static int TOKEN_USER_TYPE_PC = 0;
    public static int TOKEN_USER_TYPE_APP = 1;

    public static String USER_ADMIN = "admin";

    /****系统配置**********************************************************/

    //超时登录
    public static final String TOKEN_OVER_TIME = "tokenOverTime";
    //是否同时登录
    public static final String IS_LOGIN_MULTIPLE = "isLoginMultiple";

    /****返回状态**********************************************************/

    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_ERROR = "创建失败";
    public static final String MESSAGE_UPDATE_SUCCESS = "更新成功";
    public static final String MESSAGE_UPDATE_ERROR = "更新失败";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_ERROR = "删除失败";
    public static final String MESSAGE_VIEW_SUCCESS = "查询成功";
    public static final String MESSAGE_VIEW_ERROR = "查询失败";

    /****数据字典**********************************************************/

    public static String DICT_IS_ENABLE = "isEnable";
    public static String DICT_IS_LOCK = "isLock";
}
