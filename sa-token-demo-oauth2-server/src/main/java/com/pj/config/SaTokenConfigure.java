package com.pj.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfigure {
    // Sa-Token 整合 jwt (Simple 简单模式),用于实现jwt模式的token校验以及用户相关数据扩展
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    /**
     * 重写 Sa-Token 框架内部算法策略
     */
//    @Autowired
//    public void rewriteSaStrategy() {
//        // 重写 Token 生成策略
//        SaStrategy.instance.createToken = (loginId, loginType) -> {
////            通过StpUtil.getExtra()获取账号，通过账号来进行客户端分离校验
////            不使用loginId而使用account进行分离校验的原因在于：
////            1.不用强制各个客户端使用雪花算法或其他算法进行账号唯一性保障
////            2.增强授权服务器临时接入能力
////            3.可根据业务需求自定义账号唯一性校验方式
////            4.减小授权服务器流量压力
//            Object account = StpUtil.getExtra("account");
//            String accountStr = null;
//            if (account!=null){
//                accountStr=account.toString();
//            }
//            return JwtUtil.createJWT(accountStr);    // 生成JWT
//        };
//    }
}
