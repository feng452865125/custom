package com.chunhe.custom.framework.utils.http;

import okhttp3.Request;

public class CommonDispose extends Dispose{
    @Override
    public Request disposeRequest(Request request) {
        return request.newBuilder()
                .header("Content-Type", "application/json")
                .build();
    }
}
