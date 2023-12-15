package com.pj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pj.mapper.ClientInfoMapper;
import com.pj.model.entity.ClientInfo;
import com.pj.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/12 9:49
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientInfoMapper clientInfoMapper;
    @Override
    public ClientInfo getClientInfo(String clientId) {
        if (clientId==null){
            return null;
        }
        LambdaQueryWrapper<ClientInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClientInfo::getClientId,clientId);
        return clientInfoMapper.selectOne(wrapper);
    }
}
