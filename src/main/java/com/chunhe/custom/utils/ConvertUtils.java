package com.chunhe.custom.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertUtils {

    private static Logger logger = LogManager.getLogger(ConvertUtils.class);

    public static <T> T convert(Object value, final Class<T> targetType) {
        if (value == null) {
            if (targetType.getName().equals("java.lang.String")) {
                value = "";
            }
        } else {
            value = (T) org.apache.commons.beanutils.ConvertUtils.convert(value, targetType);
        }
        return (T) value;
    }

    /**
     * String以逗号隔开,转换成String列表
     */
    public static List<String> String2ListString(String str) {
        List<String> list;
        if (!StringUtils.isEmpty(str)) {
            list = Arrays.asList(str.split(","));
        } else {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * String以逗号隔开,转换成int列表
     */
    public static List<Integer> String2ListInteger(String str) {
        return String2ListInteger(str, null);
    }

    /**
     * String以逗号隔开,转换成int列表
     *
     * @param ignore 要忽略字符
     */
    public static List<Integer> String2ListInteger(String str, String ignore) {
        List<Integer> list = new ArrayList<>();
        if (str == null) {
            return list;
        }
        String[] s = str.split(",");
        for (String string : s) {
            if (!StringUtils.isEmpty(ignore))
                string = string.replace(ignore, "");
            Integer tmp = NumberUtils.toInt(string, -1);
            if (tmp == -1) {
                continue;
            }
            list.add(tmp);
        }
        return list;
    }

    /**
     * 列表转换成字符串,逗号隔开
     */
    public static String ListInteger2String(List<Integer> intList) {
        return ListInteger2String(intList, null, null);
    }

    /**
     * 列表转换成字符串,逗号隔开
     *
     * @param prefix 前缀
     * @param suffix 后缀
     */
    public static String ListInteger2String(List<Integer> intList, String prefix, String suffix) {
        StringBuffer result = new StringBuffer(1024);
        if (StringUtils.isEmpty(intList)) {
            return "";
        }
        for (int i = 0; i < intList.size(); i++) {
            if (!StringUtils.isEmpty(prefix))
                result.append(prefix);
            result.append(intList.get(i));
            if (!StringUtils.isEmpty(suffix))
                result.append(suffix);
            if (i < intList.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

    /**
     * 数组转换成以","隔开的字符串
     */
    public static String array2InString(Integer[] arr) {
        return array2InString(arr, null, null);
    }

    /**
     * 数组转换成以","隔开的字符串
     *
     * @param prefix 前缀
     * @param suffix 后缀
     */
    public static String array2InString(Integer[] arr, String prefix, String suffix) {
        if (StringUtils.isEmpty(arr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (!StringUtils.isEmpty(prefix))
                sb.append(prefix);
            sb.append(arr[i]);
            if (!StringUtils.isEmpty(suffix))
                sb.append(suffix);
            if (i < arr.length - 1)
                sb.append(",");

        }
        return sb.toString();
    }

    /**
     * 数字转中文
     */
    public static String num2ChineseNum(int d) {
        String[] str = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String ss[] = new String[]{"", "十", "百", "千", "万", "十", "百", "千", "亿"};
        String s = String.valueOf(d);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb = sb.append(str[Character.digit(s.charAt(i), 10)]);
        }
        String sss = sb.toString();
        int i = 0;
        for (int j = sss.length(); j > 0; j--) {
            sb = sb.insert(j, ss[i++]);
        }
        if (sss.length() == 2 && str[1].equals(String.valueOf(sss.charAt(0)))) {
            sb.deleteCharAt(0);
        }
        if (str[0].equals(String.valueOf(sb.charAt(sb.length() - 1)))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb + "";
    }


}
