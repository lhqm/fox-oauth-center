package com.pj.oauth2.tenant;

import com.pj.model.entity.AskKeyPair;

import java.util.List;

public interface ClientThirdInfoService {
    List<AskKeyPair> getClientInfoKeysByClient(String clientId);

    AskKeyPair getClientInfoKeysByClient(String clientId, TenantChannelEnum channelEnum);
}
