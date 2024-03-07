package com.pj.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pj.mapper.AskPairMapper;
import com.pj.mapper.ClientInfoMapper;
import com.pj.model.cache.ClientCacheInfo;
import com.pj.model.entity.AskKeyPair;
import com.pj.model.entity.ClientInfo;
import com.pj.service.AskPairService;
import com.pj.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/2/1 15:14
 */
@Service
public class AskPairServiceImpl implements AskPairService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AskPairMapper askPairMapper;
    @Autowired
    private ClientInfoMapper clientInfoMapper;

    private static final String ASK_PAIR_KEY = "ask_pair:";

    /**
     * 通过客户端的校验码获取密钥对
     *
     * @param clientId              客户端ID
     * @param clientSecret          客户端密钥
     * @param channel
     * @return
     */
    @Override
    public ClientCacheInfo getKeyPair(String clientId, String clientSecret, Integer channel) {
//        先走缓存
        Object cacheObject = redisUtil.getCacheObject(ASK_PAIR_KEY + clientId + channel);
//        缓存不空，有数据，返回数据
        if (cacheObject != null) {
            ClientCacheInfo keyPair = JSON.parseObject(JSON.toJSONString(cacheObject),ClientCacheInfo.class);
//            缓存中的密码不为空且密码正确，返回
            if (keyPair.getClientSecret()!=null && keyPair.getClientSecret().equals(clientSecret)) {
                return keyPair;
            }
//            密码不对，不返回
            return null;
        }
//        缓存没有，查数据库
        ClientInfo clientInfo = clientInfoMapper.selectOne(new LambdaQueryWrapper<ClientInfo>().eq(ClientInfo::getClientId, clientId));
        //        拿着到的clientInfo，去查数据库。如果没有这个客户端，就不查了
        AskKeyPair askKeyPair = clientInfo==null?
                new AskKeyPair()
                :askPairMapper.selectOne(new LambdaQueryWrapper<AskKeyPair>().eq(AskKeyPair::getClientId, clientInfo.getId()));
//        数据库没有，先将空的对象数据存入缓存，防止redis击穿
        if (clientInfo == null) {
            clientInfo = new ClientInfo();
        }
        if (askKeyPair == null) {
            askKeyPair = new AskKeyPair();
        }
//        构造缓存
        ClientCacheInfo clientCacheInfo = new ClientCacheInfo(clientInfo.getClientSecret(), askKeyPair.getCorpId(), askKeyPair.getCorpSecret(), askKeyPair.getAppId());
        redisUtil.setCacheObject(ASK_PAIR_KEY + clientId + channel, clientCacheInfo,5, TimeUnit.MINUTES);
//        返回数据
        return clientCacheInfo.getClientSecret().equals(clientSecret) ? clientCacheInfo : null;
    }
}
