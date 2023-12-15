package com.pj.service;

import com.pj.model.entity.ClientInfo;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/12 9:48
 */

public interface ClientService {
    ClientInfo getClientInfo(String clientId);
}
