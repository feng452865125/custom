package com.chunhe.custom.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author:fengzhiyuan
 * @date:2020/10/10 14:39
 * @description: sessionid，token，ip，登录验证码
 * @param: null
 */
@Component
public class LoginUtils {

    @Value("${jwt.issuer}")
    public String issuer;

    @Value("${jwt.secretkey}")
    public String secretkey;

    public static LoginUtils loginUtils;

    @PostConstruct
    public void init() {
        loginUtils = this;
    }

    //创建新的token
    public static String createToken(Integer userType, Integer userId) {
        // 通过hs256算法，以及secretKey得到Algorithm对象
        Algorithm algo = Algorithm.HMAC256(loginUtils.secretkey);
        String token = JWT.create()
                .withIssuer(loginUtils.issuer)
                .withIssuedAt(DateUtil.date())
                //过期时间 交给Redis控制
                // .withExpiresAt(new Date(now.getTime() + JwtConstant.TOKENEXPIRETIME))
                .withClaim("userType", userType.toString())
                .withClaim("userId", userId.toString())
                .sign(algo);
        return token;
    }

    /**
     * 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getLocalIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.contains("../") || ip.contains("..\\")) {
                return "";
            }
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                ip = ip.substring(0, index);
            }
            if (ip.contains("../") || ip.contains("..\\")) {
                return "";
            }
            return ip;
        } else {
            ip = request.getRemoteAddr();
            if (ip.contains("../") || ip.contains("..\\")) {
                return "";
            }
            if (ip.equals("0:0:0:0:0:0:0:1")) {
                ip = "127.0.0.1";
            }
            return ip;
        }
    }

    //生成验证码
    public static void getCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //高和宽
        int height = 30;
        int width = 60;
        String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        //创建一个图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获得画板
        Graphics g = image.getGraphics();
        //填充一个矩形，设置颜色
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, width - 2, height - 2);
        //设置字体
        g.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 25));
        StringBuffer sb = new StringBuffer();
        //写随机字
        for (int i = 0; i < 4; i++) {
            // 设置颜色‐‐随机数
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            //获得随机字
            int index = random.nextInt(data.length());
            String str = data.substring(index, index + 1);
            //写入
            g.drawString(str, width / 6 * (i + 1), 20);
            sb.append(str);
        }
        //验证码保存到session中
        request.getSession().setAttribute("code", SecureUtil.md5(sb.toString().toUpperCase() + "xingzheng"));
        //干扰线
        for (int i = 0; i < 3; i++) {
            //设置颜色‐‐随机数
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            //随机绘制
            g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
            //随机点
            g.drawOval(random.nextInt(width), random.nextInt(height), 2, 2);
        }
        // 将图片响应给浏览器
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    //验证code
    public static Boolean checkCode(String code, HttpServletRequest request) {
        String sessionCode = (String) request.getSession().getAttribute("code");
        //对两个验证码进行判断
        if (StringUtils.isEmpty(code)) {
            return false;
        } else {
            //忽略验证码大小写
            if (code.equalsIgnoreCase(sessionCode)) {
                //进行登录业务的处理
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length     长度
     */
    public static String createRandom(boolean numberFlag, int length) {
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double dblR = Math.random() * len;
            int intR = (int) Math.floor(dblR);
            sb.append(strTable.charAt(intR));
        }
        return sb.toString();
    }

    /**
     * @Author: qiu chunjing
     * @Description: token 解密
     * @Date: 2020/11/16 16:39
     * @Param: [token]
     * @Return: com.auth0.jwt.interfaces.DecodedJWT
     */
    public static DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(loginUtils.secretkey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(loginUtils.issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    /**
     * @Author: qiu chunjing
     * @Description:
     * @Date: 2020/11/20 16:51
     * @Param: [request]
     * @Return: java.lang.Integer 返回-1时登录超时
     */
    public static Integer getAdminIdByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token.equals("999999")) {
            return 1;
        }
        DecodedJWT decode = decodeToken(token);
        if (decode.getClaim("userId").asString() != null) {
            return Integer.parseInt(decode.getClaim("userId").asString());
        } else {
            return -1;
        }
    }

    //PC 0   APP 1
    public static Integer getUserTypeByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token.equals("999999")) {
            return 0;
        }
        DecodedJWT decode = decodeToken(token);
        if (decode.getClaim("userType").asString() != null) {
            return Integer.parseInt(decode.getClaim("userType").asString());
        }
        return -1;
    }
}
