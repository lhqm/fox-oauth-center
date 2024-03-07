package com.pj.oauth2.qywx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pj.constants.ChannelType;
import com.pj.model.cache.ClientCacheInfo;
import com.pj.model.cache.TokenStore;
import com.pj.model.entity.AskKeyPair;
import com.pj.service.AskPairService;
import com.pj.util.RedisUtil;
import com.pj.util.request.HttpClient;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:56
 * 企微token获取
 */
@Service
public class QYWXTokenTaker {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AskPairService askPairService;
    private final static String qywx_token_key = "token:qywx:";
    private final static String err_flag = "err";
    public TokenStore getToken(String clientId, String clientSecret){
//        从缓存拿客户端信息
        ClientCacheInfo clientCacheInfo=askPairService.getKeyPair(clientId,clientSecret, ChannelType.QYWX_ASK_KEY_PAIR_CHANNEL);
//        信息为空，返回
        if(clientCacheInfo == null){
            return null;
        }
//        替换掉客户端传过来的账号密码
        clientId = clientCacheInfo.getCorpId();
        clientSecret = clientCacheInfo.getCorpSecret();
//        如果存在token，直接返回回去
        String cacheObject = redisUtil.getCacheObject(qywx_token_key + clientId + clientSecret);
        if(cacheObject != null){
//            增加对错误token的处理，防止redis击穿和微信接口请求过于频繁带来的封禁问题
            return cacheObject.equals(err_flag) ? null : new TokenStore(cacheObject,clientCacheInfo.getAppId());
        }
        else {
//            每五分钟重新去取一次token
            String token = refreshQwToken(clientId,clientSecret);
            redisUtil.setCacheObject(qywx_token_key+clientId+clientSecret,token,5, TimeUnit.MINUTES);
            return new TokenStore(token,clientCacheInfo.getAppId());
        }
    }
    @SneakyThrows
    private String refreshQwToken(String clientId,String clientSecret) {
        OkHttpClient client = HttpClient.getInstance();
        Request request = new Request.Builder()
                .url("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+clientId+"&corpsecret="+clientSecret)
                .method("GET", null)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "qyapi.weixin.qq.com")
                .addHeader("Connection", "keep-alive")
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
//            获取token
            String bodyString = response.body().string();
            JSONObject bodyJson = JSON.parseObject(bodyString);
            if(bodyJson.getInteger("errcode") != 0){
                return err_flag;
            }
            return bodyJson.getString("access_token");
        }
        return null;
    }
}
