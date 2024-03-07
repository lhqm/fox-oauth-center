package com.pj.service.impl;

import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pj.model.cache.TokenStore;
import com.pj.model.dto.CardMessageDto;
import com.pj.oauth2.qywx.QYWXTokenTaker;
import com.pj.service.MessageService;
import com.pj.util.request.HttpClient;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:17
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private QYWXTokenTaker tokenTaker;
    @Override
    public SaResult sendCardMessage(CardMessageDto cardMessageDto) {
        JSONObject json = new JSONObject();
        json.put("touser", cardMessageDto.getToUser());
        json.put("msgtype", "textcard");
//        注意这里！！！agentid是企业微信应用id，是每个应用独立的，不要混淆了
//        json.put("agentid", cardMessageDto.getAgentId());
        JSONObject value = JSONObject.parseObject("{\"title\":\"" + cardMessageDto.getTextCard().getTitle() + "\"," +
//                "\"description\":\"" + cardMessageDto.getTextCard().getDescription() + "\"," +
                "\"url\":\"" + cardMessageDto.getTextCard().getUrl() + "\"," +
                "\"btntxt\":\"" + cardMessageDto.getTextCard().getBtnTxt() + "\"}");
        value.put("description", cardMessageDto.getTextCard().getDescription());
        json.put("textcard", value);
        json.put("enable_id_trans",0);
        json.put("enable_duplicate_check",1);
        json.put("duplicate_check_interval",1800);
//        获取JSON字符串
        String s = messageSender(json,cardMessageDto.getClientId(),cardMessageDto.getClientSecret());
        return SaResult.data(JSON.parseObject(s));
    }
    @SneakyThrows
    private String messageSender(JSONObject json, String clientId, String clientSecret){
//        获取token用于传递信息
        TokenStore tokenStore = tokenTaker.getToken(clientId, clientSecret);
//        token为空，报错返回
        if(tokenStore==null){
            return "不合法的客户端请求！请五分钟以后再试。";
        }
//        设置请求应用
        json.put("agentid", tokenStore.getAgentId());
        OkHttpClient client = HttpClient.getInstance();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, json.toJSONString());
        Request request = new Request.Builder()
                .url("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+ tokenStore.getToken())
                .method("POST", body)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .addHeader("Content-Type", "text/plain")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "qyapi.weixin.qq.com")
                .addHeader("Connection", "keep-alive")
                .build();
        Response response = client.newCall(request).execute();
        return response.body()==null?null:response.body().string();
    }
}
