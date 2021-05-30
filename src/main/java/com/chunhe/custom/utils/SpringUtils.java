package com.chunhe.custom.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther: qiu chunjing
 * @Date:
 */

@Component
public class SpringUtils implements ApplicationContextAware {

    private static Logger logger = LogManager.getLogger(SpringUtils.class);

    private static ApplicationContext applicationContext;


    /**
     * Spring初始化时，会通过该方法将ApplicationContext对象注入。
     * ContextUtil类要在applicationContext.xml中配置
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static String getRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = applicationContext.getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取系统根目录失败");
        }
        return rootRealPath;
    }

    public static String getResourceRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取资源根目录失败");
        }
        return rootRealPath;
    }
    // =================request=================== //

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取request中的属性值
     *
     * @param attName 属性名
     */
    public static Object getAttributeFromRequest(String attName) {
        return getRequest().getAttribute(attName);
    }

    /**
     * 获取request中的参数值
     *
     * @param key 参数Key
     */
    public static String getParameterFromRequest(String key) {
        return getRequest().getParameter(key);
    }

    // =================session=================== //

    /**
     * 获取session
     */
    private static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 往Session中放值
     */
    public static void setValueToSession(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从Session中取值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueFromSession(String key, Class<T> clazz) {
        return (T) getValueFromSession(key);
    }

    /**
     * 从Session中取值
     */
    public static Object getValueFromSession(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 从Session中移除对象
     */
    public static void removeFromSession(String key) {
        getSession().removeAttribute(key);
    }


    // =================cookie=================== //

    /**
     * 取出所有cookie
     */
    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    /**
     * 从cookie中取值
     */
    public static String getCookie(String name) {
        Cookie[] cs = getCookies();
        for (Cookie c : cs) {
            if (c.getName().equals(name)) {
                return c.getValue();
            }
        }
        return "";
    }

    /**
     * 向cookie中放值
     */
    public static void setCookie(String name, String value) {
        Cookie c = new Cookie(name, value);
        getResponse().addCookie(c);
    }

    /**
     * 向cookie中放值
     */
    public static void setCookie(Cookie c) {
        getResponse().addCookie(c);
    }

}
