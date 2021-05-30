package com.chunhe.custom.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    private static Logger log = LoggerFactory.getLogger(CheckUtil.class);

    public static boolean checkNull(List<?> list) {
        return (list == null || list.isEmpty());
    }

    public static boolean checkNull(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean checkNull(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean checkNull(Integer it) {
        return (it == null || it == 0);
    }

    public static boolean checkNull(Object[] array) {
        return (array == null || array.length <= 0);
    }

    public static boolean checkNull(Object object) {
        return (object == null);
    }

    public static boolean isNumber(String number) {
        if (checkNull(number)) {
            return false;
        }
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象是否在数组内
     */
    public static <T> boolean objInArray(T obj, T[] arr) {
        if (CheckUtil.checkNull(arr)) {
            return false;
        }
        for (T el : arr) {
            if (obj == el || (obj != null && obj.equals(el))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 校验是否手机号
     */
    public static boolean isMobileNO(String mobile) {
        boolean result = false;
        /* 精确校验号码段 */
        StringBuffer regExp = new StringBuffer("^[1]([3][0-9]{1}");
        regExp.append("|45|47|50|51|52|53|55|56|57|58|59");
        regExp.append("|73|76|77|78");
        regExp.append("[8][0-9]{1})");
        regExp.append("[0-9]{8}$");
        Pattern p = Pattern.compile(regExp.toString());
        Matcher m = p.matcher(mobile);
        result = m.matches();
        if (!result) {
            /* 仅校验11位数字 */
            regExp = new StringBuffer("^[0-9]{11}$");
            p = Pattern.compile(regExp.toString());
            m = p.matcher(mobile);
            result = m.matches();
            if (result) {
                log.warn("号码段未知:" + mobile);
            }
        }
        return result;
    }

    /**
     * 只校验位数和是否纯数字
     */
    public static boolean isMobileSimple(String mobile) {
        StringBuffer regExp = new StringBuffer("^[0-9]{11}$");
        Pattern p = Pattern.compile(regExp.toString());
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 只校验是否字母
     */
    public static boolean isEnglishLetter(String letter) {
        StringBuffer regExp = new StringBuffer("^[A-Z]{1}$");
        Pattern p = Pattern.compile(regExp.toString());
        Matcher m = p.matcher(letter);
        return m.matches();
    }

    /**
     * 只校验是否整数
     */
    public static boolean isIntegerNumber(String letter) {
        StringBuffer regExp = new StringBuffer("^-?[0-9]\\d*$");
        Pattern p = Pattern.compile(regExp.toString());
        Matcher m = p.matcher(letter);
        return m.matches();
    }
}
