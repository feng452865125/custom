package com.chunhe.custom.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;

public class MyTool {
    private static Logger log = LoggerFactory.getLogger(MyTool.class);

    /**
     * 获得本地ip地址
     */
    public static String getLocalIp(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 通过ip获得所在省、市、区
     */
    public static String getAddressByIP(String strIP) {
        String address = null;
        address = "中国";
//        try {
////	    strIP = "112.17.242.116";
//            URL url = new URL("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + strIP);
//            URLConnection conn = url.openConnection();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
//            String line = null;
//            StringBuffer result = new StringBuffer();
//            while ((line = reader.readLine()) != null) {
//                result.append(line);
//            }
//            reader.close();
//            System.out.println(result);
//            JSONObject obj = JSONObject.parseObject(result.toString());
//            String country = obj.getString("country");
//            String province = obj.getString("province");
//            String city = obj.getString("city");
//            address = country + "-" + province + "-" + city;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "读取失败";
//        }
        return address;
    }


    /**
     * 适用于bjui框架传回的排序参数
     *
     * @param orders 原参数
     * @param alias  mapper中的表别名
     * @return 转化后的排序参数
     */
    public static String getOrder(String orders, String alias) {
        String[] orderArr = orders.replaceAll("\\s*", "").split(",");
        StringBuilder sb = new StringBuilder();
        int length = orderArr.length;
        for (int i = 0; i < length; i++) {
            String order = orderArr[i];
            switch (order) {
                case "createDateasc":
                    if (!CheckUtil.checkNull(alias))
                        sb.append(alias).append(".");
                    sb.append("create_date asc");
                    break;
                case "createDatedesc":
                    if (!CheckUtil.checkNull(alias))
                        sb.append(alias).append(".");
                    sb.append("create_date desc");
                    break;
                case "updateDateasc":
                    if (!CheckUtil.checkNull(alias))
                        sb.append(alias).append(".");
                    sb.append("update_date asc");
                    break;
                case "updateDatedesc":
                    if (!CheckUtil.checkNull(alias))
                        sb.append(alias).append(".");
                    sb.append("update_date desc");
                    break;
                case "lastDateasc":
                    if (!CheckUtil.checkNull(alias))
                        sb.append(alias).append(".");
                    sb.append("last_date asc");
                    break;
                case "lastDatedesc":
                    if (!CheckUtil.checkNull(alias))
                        sb.append(alias).append(".");
                    sb.append("use_date asc");
                    break;
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length     长度
     */

    public static String createRandom(boolean numberFlag, int length) {
        String strTable = numberFlag ? "1234567890" : "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
     * 计算2个地点之间的距离。
     * 用haversine公式
     *
     * @param lat1 纬度1
     * @param lon1 经度1
     * @param lat2 纬度2
     * @param lon2 经度2
     * @return 距离（公里、千米）
     */
    public static double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double EARTH_RADIUS = 6371.0;//km 地球半径 平均值，千米
        //经纬度转换成弧度
        lat1 = ConvertDegreesToRadians(lat1);
        lon1 = ConvertDegreesToRadians(lon1);
        lat2 = ConvertDegreesToRadians(lat2);
        lon2 = ConvertDegreesToRadians(lon2);
        //差值
        double vLon = Math.abs(lon1 - lon2);
        double vLat = Math.abs(lat1 - lat2);
        double h = HaverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * HaverSin(vLon);
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
        return distance;
    }

    public static double HaverSin(double theta) {
        double v = Math.sin(theta / 2);
        return v * v;
    }

    /**
     * 将角度换算为弧度。
     *
     * @param degrees 角度
     * @return 弧度
     */
    private static double ConvertDegreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    /**
     * 生成token
     *
     * @param userCode
     * @return
     */
    public static String makeToken(Integer userCode) {
        String token = new Date().getTime() + new Random().nextInt(999999) + "" + userCode;
        String str = MD5Tool.ToMD5(token);
        return str;
    }
}
