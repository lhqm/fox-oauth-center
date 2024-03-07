package com.pj.oauth2.tenant;

public enum TenantChannelEnum {


    QYWX(1,"企业微信");
    private final Integer id;
    private final String channelName;

    TenantChannelEnum(int id, String channelName) {
        this.channelName=channelName;
        this.id=id;
    }

    // 获取channelName的方法
    public String getChannelName() {
        return channelName;
    }

    // 获取id的方法
    public int getId() {
        return id;
    }
}
