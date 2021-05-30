package com.chunhe.custom.utils.okhttp3;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LW on 2018/2/26
 * Interceptor是全局的设置，针对所有请求和回应，但有时候需要对request, response单独添加一些处理。
 * 该类处理顺序和Interceptor一样，按照添加的顺序，request从前向后，response从后向前
 *
 * @see okhttp3.Interceptor
 */
public abstract class Okhttp3Dispose {

    /**
     * 在request发送前做一些处理
     *
     * @param request 原始request
     * @return 处理后request
     */
    Request disposeRequest(Request request) {
        return request;
    }

    /**
     * 在请求完成并成功回应后做一些处理
     *
     * @param response 原始response
     * @return 处理后response
     */
    Response disposeResponse(Response response) {
        return response;
    }
}
