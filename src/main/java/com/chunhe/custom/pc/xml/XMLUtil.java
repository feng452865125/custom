package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLUtil {

    /**
     * 实体类转成xml
     *
     * @param xmlRequest
     * @throws IOException
     */
    public static String beanToXML(XMLRequest xmlRequest) {
        String headXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        //xml自身下划线为关键字，要转换
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLRequest.class);
        xStream.processAnnotations(ROW.class);
        String xml = xStream.toXML(xmlRequest);
        xml = headXML + xml;
        return xml;
    }

    /**
     * 实体类转成xml
     *
     * @param xmlRequest
     * @throws IOException
     */
    public static String beanToXML2(XMLRequest xmlRequest) {
        String headXML = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n";
        //xml自身下划线为关键字，要转换
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLRequest.class);
        xStream.processAnnotations(ROW.class);
        String xml = xStream.toXML(xmlRequest);
        xml = headXML + xml;
        return xml;
    }

    /**
     * xml转成实体类
     *
     * @param xml
     * @throws IOException
     */
    public static XMLResponse XMLToBean(String xml) {
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLResponse.class);
        xStream.processAnnotations(XMLDataSet.class);
        xStream.processAnnotations(FIELD.class);
        xStream.processAnnotations(FIELDS.class);
        xStream.addImplicitArray(FIELDS.class, "FIELD");
        xStream.addImplicitArray(ROWDATA.class, "ROW");
        Object object = xStream.fromXML(xml);
        XMLResponse response = (XMLResponse) object;
        return response;
    }

    /**
     * xml转成实体类
     *
     * @param xml
     * @throws IOException
     */
    public static XMLResponse XMLToBean2(String xml) {
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLResponse.class);
        xStream.processAnnotations(XMLDataSet.class);
        Object object = xStream.fromXML(xml);
        XMLResponse response = (XMLResponse) object;
        return response;
    }


    /**
     * 调用接口 POST
     *
     * @param xml
     * @throws IOException
     */
    public static String toPost(String xml, String posUrl) throws IOException {
        //转码，对方需要的编码
        String data = contentChange(xml, "UTF-8", "ISO-8859-1");
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(posUrl);
        StringEntity postingString = new StringEntity(data);// json传递
        post.setEntity(postingString);
        HttpResponse response = httpClient.execute(post);
        String content = EntityUtils.toString(response.getEntity());
        String output = contentChange(content, "ISO-8859-1", "UTF-8");
        return output;
    }

    /**
     * 调用接口 POST
     *
     * @param xml
     * @throws IOException
     */
    public static String toPost2(String xml, String posUrl) throws IOException {
        OutputStreamWriter out;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(posUrl);
        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(15000);
        conn.setRequestMethod("POST");
        conn.addRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");// utf-8编码
        // 发送请求参数
        out.write(xml);

        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        int code = conn.getResponseCode();
        if (code == 200) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        return result;
    }


    /**
     * 字符编码转换（可互换） (ISO-8859-1 and UTF-8)
     *
     * @param content
     * @param fromCharsetName
     * @param toCharsetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String contentChange(String content, String fromCharsetName, String toCharsetName) throws UnsupportedEncodingException {
        String output = "";
        if (StringUtils.isEmpty(content)) {
            return output;
        }
        byte[] bContent = content.getBytes(fromCharsetName);
        output = new String(bContent, toCharsetName);
        return output;
    }
}
