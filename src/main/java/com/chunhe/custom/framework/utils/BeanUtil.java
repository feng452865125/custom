package com.chunhe.custom.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.chunhe.custom.framework.exception.RFException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BeanUtil {
    private static Logger log = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 复制对象属性
     *
     * @param srcObj     源对象
     * @param tarObj     目标对象
     * @param ignoreNull 是否忽略空属性。默认false，即复制空属性。
     */
    public static void copyObject(Object srcObj, Object tarObj, boolean... ignoreNull) {
        boolean flag = false;
        if (ignoreNull != null && ignoreNull.length > 0)
            flag = ignoreNull[0];
        try {
            copyProperty(srcObj, tarObj, flag);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.info("copyObject error", e);
        }
    }

    private static void copyProperty(Object srcObj, Object tarObj, boolean ignoreNull) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class tarClass = tarObj.getClass();
        Class srcClass = srcObj.getClass();
        //该类所有的属性
        Field[] srcFields = srcClass.getDeclaredFields();
        //新的属性
        Field srcField;
        //老的属性
        Field tarField;
        for (Field f : srcFields) {
            //类中的属性名称
            String fieldName = f.getName();
            //通过属性名称获取属性
            srcField = srcClass.getDeclaredField(fieldName);
            //静态和final字段
            if (Modifier.isStatic(srcField.getModifiers()) || Modifier.isFinal(srcField.getModifiers()))
                continue;
            //获取属性的值时需要设置为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
            //值为 false 则指示反射的对象应该实施 Java 语言访问检查。
            srcField.setAccessible(true);
            //根据属性获取对象上的值
            Object srcObject = srcField.get(srcObj);

            //过滤空的属性或者一些默认值
            if (srcObject == null && ignoreNull) {
                continue;
            }
            tarField = tarClass.getDeclaredField(fieldName);
            tarField.setAccessible(true);
            tarField.set(tarObj, srcObject);
        }
    }

    /**
     * 调用接口JSON转实体类
     * (不会返回null)
     *
     * @param req
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T checkContent(HttpServletRequest req, Class<T> clazz) {
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            try {
                return JSON.parseObject(content, clazz);
            } catch (JSONException e) {
                throw new RFException("content参数JSON格式错误，" + e.getMessage());
            }
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
