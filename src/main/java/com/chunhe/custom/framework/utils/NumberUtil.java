package com.chunhe.custom.framework.utils;

public class NumberUtil {
    /**
     * 比较连个Integer是否相等
     */
    public static boolean sameInteger(Integer t1, Integer t2) {
        return t1 == null && t2 == null || t1 != null && t2 != null && t1.equals(t2);
    }

    /**
     * 装箱
     */
    public static Integer[] box(int[] tar) {
        Integer[] result = new Integer[tar.length];
        for (int i = 0; i < tar.length; i++) {
            result[i] = tar[i];
        }
        return result;
    }

}
