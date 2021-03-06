package com.chunhe.custom.utils.okhttp3;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class Okhttp3Utils {

    static Logger logger = LogManager.getLogger(Okhttp3Utils.class);

    private static OkHttpClient okHttpClient;

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(30, 5, TimeUnit.MINUTES))
                .build();
        ignoreCertificate(okHttpClient);
    }

    /**
     * 忽略SSL证书
     */
    private static void ignoreCertificate(OkHttpClient okHttpClient) {
        X509TrustManager trustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            Okhttp3Utils.okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步发送
     *
     * @param request         请求
     * @param tar             期待的返回结果类型
     * @param okhttp3Disposes 处理请求和回应
     * @return 返回结果
     */
    public static <T> T execute(Request request, Class<T> tar, Okhttp3Dispose... okhttp3Disposes) {
        for (Okhttp3Dispose okhttp3Dispose : okhttp3Disposes) {
            request = okhttp3Dispose.disposeRequest(request);
        }
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                for (int i = okhttp3Disposes.length; i > 0; i--) {
                    response = okhttp3Disposes[i - 1].disposeResponse(response);
                }
                String result = response.body().string();
                if (tar == String.class) {
                    return (T) result;
                } else {
                    return JSON.parseObject(result, tar);
                }
            } else {
                Okhttp3Callback.onErrorDefault(response);
            }
        } catch (IOException e) {
            Okhttp3Callback.onFailureDefault(call, e);
        }
        return null;
    }

    /**
     * 异步发送
     *
     * @param request         请求
     * @param httpCallback    回调
     * @param okhttp3Disposes 处理请求和回应
     */
    public static void enqueue(Request request, final Okhttp3Callback httpCallback, final Okhttp3Dispose... okhttp3Disposes) {
        for (Okhttp3Dispose okhttp3Dispose : okhttp3Disposes) {
            request = okhttp3Dispose.disposeRequest(request);
        }
        httpCallback.onBeforeRequest(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    for (int i = okhttp3Disposes.length; i > 0; i--) {
                        response = okhttp3Disposes[i - 1].disposeResponse(response);
                    }
                    if (httpCallback.mType == String.class) {
                        httpCallback.onSuccess(response, response.body().string());
                    } else {
                        httpCallback.onSuccess(response, JSON.parseObject(response.body().string(), httpCallback.mType));
                    }
                } else {
                    httpCallback.onError(response);
                }
            }
        });
    }

}
