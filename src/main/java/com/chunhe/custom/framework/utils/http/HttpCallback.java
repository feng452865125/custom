package com.chunhe.custom.framework.utils.http;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 异步请求时的回调处理
 */
public abstract class HttpCallback<T> {
    public Type mType;

    /**
     * 得到泛型的类型
     */
    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            return parameterizedType.getActualTypeArguments()[0];
        } else {
            return String.class;
        }

    }

    public HttpCallback() {
        mType = getSuperclassTypeParameter(this.getClass());
    }

    /**
     * 在请求发送之前调用，可以请求过程中做一些事
     */
    protected void onBeforeRequest(Request request) {

    }

    protected void onFailure(Call call, Exception e) {
        onFailureDefault(call, e);
    }

    /**
     * 状态码大于200，小于300 时调用此方法
     */
    protected abstract void onSuccess(Response response, T result) throws IOException;

    /**
     * 状态码400，404，403，500等时调用此方法
     */
    protected void onError(Response response) {
        onErrorDefault(response);
    }

    public static void onFailureDefault(Call call, Exception e) {
        HttpUtil.log.error("[Http请求错误] url:{}", call.request().url(), e);
    }

    public static void onErrorDefault(Response response) {
        HttpUtil.log.error("[服务器错误] url:{}, code:{}, message:{}", new Object[]{response.request().url(), response.code(), response.message()});
    }
}
