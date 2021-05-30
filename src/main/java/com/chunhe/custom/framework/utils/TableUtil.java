package com.chunhe.custom.framework.utils;


/**
 * 数据库表方法
 */
public class TableUtil{


    /**
     * 生成模糊查询字段
     */
    public static String toFuzzySql(String str) {
        return "%" + str + "%";
    }
	
}
