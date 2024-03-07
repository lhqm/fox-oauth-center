package com.pj.util.request;

import okhttp3.OkHttpClient;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:21
 */
public class HttpClient {
    private static OkHttpClient client;

    private HttpClient() {
        // 私有构造方法
    }

    public static synchronized OkHttpClient getInstance() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    // 自定义配置
                    .build();
        }
        return client;
    }
}