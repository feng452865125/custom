package com.chunhe.custom.utils;

import com.chunhe.custom.mybatis.PageList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @Author: fengzhiyuan
 * @Date: 2020/6/19 12:01
 */
public class RequestUtils {

    private static Logger log = LogManager.getLogger(RequestUtils.class);

    /**
     * @author:fengzhiyuan
     * @date:2020/6/19 12:01
     * @description: 插件分页
     * @param: req
     * @return:void
     */
    public static void startPageHelp(HttpServletRequest request) {
        String pcStr = request.getParameter("pageCurrent");
        String psStr = request.getParameter("pageSize");
        int pageCurrent = NumberUtils.toInt(pcStr, 0);
        int pageSize = NumberUtils.toInt(psStr, 10);
        PageHelper.startPage(pageCurrent, pageSize);
    }

    /**
     * @author:fengzhiyuan
     * @date:2020/6/19 12:01
     * @description: 逻辑分页
     * 适用情况: 用一条sql查询太复杂或无法实现，先查出来所有，后做一些判断进行删除操作，物理分页失效
     * @param: list
     * @return:com.github.pagehelper.PageInfo<E>
     */
    public static <E> PageInfo<E> logicalPage(List<E> list) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String pcStr = req.getParameter("pageCurrent");
        String psStr = req.getParameter("pageSize");
        int pageCurrent = NumberUtils.toInt(pcStr, 1);
        int pageSize = NumberUtils.toInt(psStr, 10);
        Page<E> page = new Page<>(pageCurrent, pageSize);
        for (int i = 0; i < list.size(); i++) {
            if (i >= page.getStartRow() && i < page.getEndRow())
                page.add(list.get(i));
        }
        page.setTotal(list.size());
        page.setPages(list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1);
        return new PageInfo<>(page);
    }


    public static <E> PageList<E> logicalPage(List<E> list, Integer firstnum, Integer num) {
        if (firstnum == null && num == null) {
            firstnum = 0;
            num = 10;
        }
        int pageSize = num;
        int pageCurrent = firstnum / num + 1;
        Page<E> page = new Page<>(pageCurrent, pageSize);
        for (int i = 0; i < list.size(); i++) {
            if (i >= page.getStartRow() && i < page.getEndRow())
                page.add(list.get(i));
        }
        page.setTotal(list.size());
        page.setPages(list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1);
        return new PageList<>(page);
    }

    /**
     * @author: fengzhiyuan
     * @date: 2020/6/19 15:19
     * @description: 配合前端firstnum的转化分页
     * @param: firstnum
     * @param: num
     * @return: void
     */
    public static void startPageHelp(Integer firstnum, Integer num) {
        if (firstnum != null && num != null) {
            int pageSize = num;
            int pageCurrent = firstnum / num + 1;
            PageHelper.startPage(pageCurrent, pageSize);
        }
    }


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
            log.error("[copyObject] exception: {}", e);
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

}
