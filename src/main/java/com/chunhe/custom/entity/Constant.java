package com.chunhe.custom.entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: white
 * @Date: 2021/05/16/12:30
 * @Description:
 */
public class Constant {

    /****系统配置**********************************************************/

    //超时登录
    public static final String TOKEN_OVER_TIME = "tokenOverTime";
    //是否同时登录
    public static final String IS_LOGIN_MULTIPLE = "isLoginMultiple";


    /****其他配置**********************************************************/

    //登陆类型，0后端，1前端
    public static int TOKEN_USER_TYPE_PC = 0;
    public static int TOKEN_USER_TYPE_APP = 1;

    public static String USER_ADMIN = "admin";
}
