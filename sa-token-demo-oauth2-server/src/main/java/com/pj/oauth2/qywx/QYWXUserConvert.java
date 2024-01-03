package com.pj.oauth2.qywx;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pj.mapper.AuthorizationUserMapper;
import com.pj.model.entity.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 将微信用户转化成Sa-Token的AuthUser对象，从而实现本地登录功能
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/18 14:03
 */
@Service
public class QYWXUserConvert {
    @Autowired
    private AuthorizationUserMapper userMapper;
    /**
     * 进行转换。关于企业微信用户实体类，可以参照如下地址文档：
     * <a href="https://www.justauth.cn/guide/oauth/wechat_enterprise_web/#_3-%E6%8E%88%E6%9D%83%E7%BB%93%E6%9E%9C">企微授权结果模型</a>
     * @param userString 用户响应体模型
     * @return AuthUser 系统本地登录用户
     */
    public AuthUser convertToLocalUserFromJSONString(String userString) {
        JSONObject userJson = JSONObject.parseObject(userString);

//        获取到里层数据，通过里层数据进行装配
        JSONObject rawData = userJson.getJSONObject("rawUserInfo");

//        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
//        System.out.println(rawData.toJSONString());

//        初始化用户账号，用户账号的来源其实可以参考用户的好友添加qr_code。
//        可以知道的是，一个用户的添加请求肯定是唯一的，微信官方生成这个qr_code的规则也是唯一生成
//        类似：https://open.work.weixin.qq.com/wwopen/userQRCode?vcode=vcd432f52d0ddbd39b
//        可以知道这个qr_code的规则是：vcode=xxx，xxx就是唯一用于判定一个的识别码，那么我们作为第三方认证系统，将它视为一个唯一标记微信号的账号即可
//        目前该方法已经废弃，因为扫码并不能获取这个qr_code。所以直接获取user_id即可。user_id是用户手机号
//        String account = rawData.getString("qr_code").split("=")[1];
        String account = rawData.getString("userid");

//        通过这个账号直接找数据，如果找到就召回，没有找到就走注册-登录流程
        LambdaQueryWrapper<AuthUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthUser::getAccount, account);
        AuthUser userOriginal = userMapper.selectOne(wrapper);
        if (userOriginal != null) {
//            存在数据，进入召回阶段
//            查询用户是否存在绑定账户并返回.如果存在绑定账户则召回绑定账户，否则召回原数据
            return userOriginal.getBinding()==null?userOriginal:userMapper.selectById(userOriginal.getBinding());
        }

//        不存在的账号，走授权登录流程
        AuthUser user = new AuthUser();
//        标记账号
        user.setAccount(account);
//        初始化登记，设置登录平台为企业微信
        user.setPlatform("企业微信");
//        设置账户为活跃状态
        user.setActive(1);
//        RAM本地授权登录
        user.setClientId(0);
//        把企业微信微信名字设为用户名
        user.setNickName(rawData.getString("name"));
//        设置用户移动手机号
        user.setPhone(rawData.getLong("mobile"));
//        其他信息
        user.setGender(rawData.containsKey("gender")?null:rawData.getIntValue("gender")==0?null:rawData.getIntValue("gender")==1?1:0);
        user.setAvatar(rawData.getString("avatar"));
        user.setEmail(rawData.getString("email"));
//        插入数据
        userMapper.insert(user);
//        返回用户
        return user;
    }
}
