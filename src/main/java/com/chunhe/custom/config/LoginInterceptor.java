//package com.chunhe.custom.config;
//
//import com.chunhe.custom.annotations.NoLogin;
//import com.chunhe.custom.service.SysTokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by snow on 2020/6/19 13:57
// */
//
////@PropertySource(value = {"classpath:config/config.properties"})
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private SysTokenService sysTokenService;
//
//    @Value("${token.over.time}")
//    private String tokenOvenTime;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("preHandle run!");
//        if (2 > 1) {
//            return true;
//        }
//        if (handler instanceof HandlerMethod) {
//            //获得经过拦截器的方法
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            //通过反射的getAnnotation方法获得其方法上的指定的NoLogin类型的注解。
//            NoLogin myanno = handlerMethod.getMethod().getAnnotation(NoLogin.class);
//            if (myanno != null) {  //如果获得的注解不为空的话，说明此方法不需要权限就可执行。
//                return true;
//            }
//            String url = request.getRequestURI();
//            if (url.contains("/app/")) {
//                //小程序前端登录不判断token时效（判断授权）
//                return true;
//            }
//            String token = request.getHeader("token");
//            if (!StringUtils.isEmpty(token) && token.equals("999999")) {
//                //调试方便，特殊处理
//                return true;
//            }
////            if (sysTokenService.checkToken(token)) {
////                return true;
////            }
////            JSONObject object = JsonExample.returnJsons(2001, JsonExample.MESSAGE_LOGIN_TIMEOUT, null);
////            response.setCharacterEncoding("UTF-8");
////            response.setContentType("application/json;charset=utf-8");
////            try (PrintWriter writer = response.getWriter()) {
////                writer.write(object.toJSONString());
////                return false;
////            }
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
////        System.out.println("postHandle run!");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
////        System.out.println("afterCompletion run!");
//    }
//}
