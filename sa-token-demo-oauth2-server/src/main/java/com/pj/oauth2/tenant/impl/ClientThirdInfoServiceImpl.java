package com.pj.oauth2.tenant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pj.mapper.AskPairMapper;
import com.pj.model.entity.AskKeyPair;
import com.pj.oauth2.tenant.ClientThirdInfoService;
import com.pj.oauth2.tenant.TenantChannelEnum;
import com.pj.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ClientThirdInfoServiceImpl implements ClientThirdInfoService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AskPairMapper askPairMapper;

    public static final String TENANT_THIRD_PASS_AUTH_INFO="tenant:third:info:";

    @Override
    public List<AskKeyPair> getClientInfoKeysByClient(String clientId){
        Object data = redisUtil.getCacheObject(TENANT_THIRD_PASS_AUTH_INFO+clientId);
//        不为空，直接返回值
        if (data!=null){
            return JSONArray.parseArray(data.toString(), AskKeyPair.class);
        }
//        为空，查库写入，然后返回数据
        List<AskKeyPair> keyPairs=askPairMapper.getClientDataByClientId(clientId);
//        这条数据在15分钟以后过期，为系统方便动态解析而设立使用
        redisUtil.setCacheObject(TENANT_THIRD_PASS_AUTH_INFO+clientId, JSON.toJSONString(keyPairs),15, TimeUnit.MINUTES);
        return keyPairs;
    }

    @Override
    public AskKeyPair getClientInfoKeysByClient(String clientId, TenantChannelEnum channelEnum){
//        获取到所有渠道的消息
        List<AskKeyPair> askKeyPairs = getClientInfoKeysByClient(clientId);
//        获取到传入的渠道信息
        int channelEnumId = channelEnum.getId();
        for (AskKeyPair keyPair : askKeyPairs) {
            if (keyPair.getChannel().equals(channelEnumId)){
                return keyPair;
            }
        }
//        没找到，报错
        throw new RuntimeException("不存在的三方认证渠道，请重试!");
    }
}
