//package com.pj.oauth2.qywx;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.pj.util.RedisUtil;
//import lombok.SneakyThrows;
//import okhttp3.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
///**
// * @author 离狐千慕
// * @version 1.0
// * @date 2023/12/28 11:08
// */
//@Service
//@Deprecated
//public class QrCodeCallback {
//    @Autowired
//    private RedisUtil redisUtil;
//
//    private final static String TOKEN_QYWX = "3rd:token:qywx";
//    @Value("${qywx.corp.app-secret}")
//    private String appSecret;
//    @Value("${qywx.corp.corp-id}")
//    private String appId;
//
//    @SneakyThrows
//    private String getToken(){
//        String token = redisUtil.getCacheObject(TOKEN_QYWX);
//        if(token !=null){
//            return token;
//        }else{
////            获取token
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("text/plain");
//            RequestBody body = RequestBody.create(mediaType, "");
//            Request request = new Request.Builder()
//                    .url("https://qyapi.weixin.qq.com/cgi-bin/gettoken?" +
//                            "corpid="+appId+"&corpsecret="+appSecret)
//                    .method("GET", body)
//                    .addHeader("Connection", "keep-alive")
//                    .build();
//            Response response = client.newCall(request).execute();
//            String data = response.body().string();
//            JSONObject object = JSON.parseObject(data);
//            String accessToken = object.getString("access_token");
//            return accessToken;
//        }
//    }
//}
