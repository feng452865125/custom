package com.chunhe.custom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    static Logger logger = LogManager.getLogger(HttpUtils.class);

    /**
     * @param url
     * @return JSONObject
     * @throws Exception
     * @desc ：1.发起GET请求
     */
    public static JSONObject doGet(String url) throws Exception {

        //1.生成一个请求
        HttpGet httpGet = new HttpGet(url);
        //2.配置请求的属性
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//2000
        httpGet.setConfig(requestConfig);

        //3.发起请求，获取响应信息
        //3.1 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            //3.2 发起请求，获取响应信息
            response = httpClient.execute(httpGet, new BasicHttpContext());

            //如果返回结果的code不等于200，说明出错了
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("[doGet] url: {}, statusCode: {}", url, response.getStatusLine().getStatusCode());
                return null;
            }
            //4.解析请求结果
            HttpEntity entity = response.getEntity();      //reponse返回的数据在entity中
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");  //将数据转化为string格式
                logger.info("[doGet] return：{}", resultStr);
                JSONObject result = JSON.parseObject(resultStr);    //将String转换为 JSONObject

                if (result.getInteger("errcode") == null) {
                    return result;
                } else if (0 == result.getInteger("errcode")) {
                    return result;
                } else {
                    logger.info("[doGet] url: {}, return: {}", url, resultStr);
                    return result;
                }
            }
        } catch (IOException e) {
            logger.error("[doGet] url: {}, return exception msg: {}, ", url, e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    //释放资源
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 2.发起POST请求
     *
     * @param url  请求url
     * @param data 请求参数（json）
     * @return
     * @throws Exception JSONObject
     * @desc ：
     */
    public static JSONObject doPost(String url, Object data) throws Exception {
        //1.生成一个请求
        HttpPost httpPost = new HttpPost(url);

        //2.配置请求属性
        //2.1 设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
        httpPost.setConfig(requestConfig);
        //2.2 设置数据传输格式-json
        httpPost.addHeader("Content-Type", "application/json");
        //2.3 设置请求实体，封装了请求参数
        StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
        httpPost.setEntity(requestEntity);
        //3.发起请求，获取响应信息
        //3.1 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            //3.3 发起请求，获取响应
            response = httpClient.execute(httpPost, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("[doPost] url: {}, statusCode: {}", url, response.getStatusLine().getStatusCode());
                return null;
            }
            //获取响应内容
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                logger.info("[doPost] return：{}", resultStr);
                //解析响应内容
                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == null) {
                    return result;
                } else if (0 == result.getInteger("errcode")) {
                    return result;
                } else {
                    logger.info("[doPost] url: {}, return: {}", url, resultStr);
                    return result;
                }
            }
        } catch (IOException e) {
            logger.error("[doPost] url: {}, return exception msg: {}, ", url, e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    //释放资源
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info("[sendGet] {} ---> {}", key, map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("[sendGet] url: {}, return exception msg: {}, ", url, e.getMessage());
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        System.out.println(param);
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("[sendPost] url: {}, return exception msg: {}, ", url, e.getMessage());
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
