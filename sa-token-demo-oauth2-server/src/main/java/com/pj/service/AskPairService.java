package com.pj.service;

import com.pj.model.cache.ClientCacheInfo;
import com.pj.model.entity.AskKeyPair;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/2/1 15:13
 */
public interface AskPairService {
    ClientCacheInfo getKeyPair(String clientId, String clientSecret, Integer channel);
}
