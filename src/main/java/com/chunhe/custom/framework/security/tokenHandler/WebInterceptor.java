package com.chunhe.custom.framework.security.tokenHandler;

import com.chunhe.custom.pc.service.SysUserTokenService;
//import com.wosaitech.estate.framework.exception.RFException;
//import com.wosaitech.estate.framework.utils.RSA.Base64Utils;
//import com.wosaitech.estate.framework.utils.RSA.MD5Util;
//import com.wosaitech.estate.pc.utils.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zcw
 * @Description:
 * @date 2019/7/29 13:56
 */
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserTokenService sysUserTokenService;

    private static final String METHOD_TYPE = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_CONDITION = "/app/";
//    @Autowired
//    private UserTokenService userTokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        boolean fl = this.tokenHandle(httpServletRequest);
        if (!fl) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, PATCH, OPTIONS");
// response.setHeader("Access-Control-Max-Age", "3600");//缓存时间
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,token,xtoken,time,sign,client_version,server_version,platform");
            httpServletResponse.setStatus(200);
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getOutputStream().write("{\"code\":405,\"message\":\"账号登陆过期,请重新登录!\"}".getBytes());
//            throw new RFException(405, "账号登陆过期,请重新登录!");

        }
        return fl;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {


    }

    /**
     * @Description: 对于app token的处理
     * @author: qsy
     * @param: HttpServletRequest
     * @return: boolean
     * @exception:
     * @date: 2019/1/25 16:30
     */
    public Boolean tokenHandle(HttpServletRequest httpServletRequest) {
        //H5和APP调用接口时，前缀有</app/>，后端自己调用时没有
        if (httpServletRequest.getRequestURI().contains(METHOD_CONDITION)) {
            // h5的判断
            if ((httpServletRequest.getHeader("h5") != null && httpServletRequest.getHeader("h5").equals("h5"))
                    || (httpServletRequest.getHeader("access-control-request-headers") != null && httpServletRequest.getHeader("access-control-request-headers").equals("h5"))) {
                //如果H5，true，没有登录功能，无token
                return true;
            }
            // app的判断
            if (httpServletRequest.getRequestURI().contains("/login")) {
                //登入登出时候不判断token
                return true;
            } else if (httpServletRequest.getRequestURI().contains("/version")) {
                //查版本号时不判断token
                return true;
            } else if (httpServletRequest.getRequestURI().contains("/dataSynch/sku")) {
                //sap系统同步时时不判断token
                return true;
            } else if (httpServletRequest.getRequestURI().contains("/dataSynch/posCard")) {
                //pos系统对接卡的状态
                return true;
            }
            //超时退出，开关
            Boolean b = sysUserTokenService.isTokenLogin();
            if (!b) {
                //关闭功能，返回true
                return true;
            }
            String tokenToHash = httpServletRequest.getHeader("token");//获取Token值
            if (tokenToHash == null || "".equals(tokenToHash)) {
                return false;
            }
            return sysUserTokenService.isToken(tokenToHash);
        } else {
            return true;
        }
    }


}
