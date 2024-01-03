package com.pj.service.impl;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pj.mapper.AuthorizationUserMapper;
import com.pj.model.entity.AuthUser;
import com.pj.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/6 15:57
 */
@Service
public class UserDetailService {
    @Autowired
    private AuthorizationUserMapper authorizationUserMapper;
    /**
     * 通过密码登录的，加载并且初始化用户
     * @param name /
     * @param pwd /
     * @return /
     */
    public Object loadAndInitUser(String name, String pwd) {
//        查出用户
        LambdaQueryWrapper<AuthUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUser::getAccount, name);
        AuthUser authUser = authorizationUserMapper.selectOne(wrapper);
//        如果为空，证明用户不存在
        if(authUser == null) {
            return SaResult.error("账号名或密码错误");
        }
        return loadUserByModel(authUser,pwd,true);
    }

    /**
     * 接入用户多元化登录的能力，只需要告诉系统是否需要验证密码即可
     * @param authUser
     * @return
     */

    public Object loadUserByModel(AuthUser authUser,String pwd,boolean isCheckPwd) {
        //        看账户是否被封禁或者未激活
        if(authUser.getActive() == 0) {
            return SaResult.error("账号已被封禁或未被激活");
        }
//        解密密码，看密码是否相对，如果密码不对，仍旧返回错误
        try {
            if (isCheckPwd && pwd==null){
                return SaResult.error("账号名或密码错误");
            }
            if (isCheckPwd && !pwd.equals(AesUtil.decryptAES(authUser.getPassword()))){
                return SaResult.error("账号名或密码错误");
            }
        } catch (Exception e) {
            return SaResult.error("密码解析错误，请联系管理员进行解决");
        }
//        封装token
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(authUser)).getInnerMap();
//        如果此时对应的授权用户已经有登录了的，把登录信息注销掉
        if (StpUtil.isLogin()){
            StpUtil.logout(StpUtil.getLoginId());
        }
//        再进行登录
        StpUtil.login(authUser.getId(), SaLoginConfig.setExtraData(map));
        return SaResult.get(200,"登录成功",StpUtil.getTokenInfo());
    }

    public AuthUser getAuthUser(String accessToken) {
        Object id = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        System.out.println(id);
        String s = id.toString();
        return authorizationUserMapper.selectById(Integer.valueOf(s));
    }
}
