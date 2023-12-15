package com.pj.model.entity;

import cn.dev33.satoken.oauth2.model.SaClientModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oauth_clients_info")
public class ClientInfo{
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 应用id 
	 */
	private String clientId;
	/**
	 * 客户端名称
	 */
	private String clientName;
	/**
	 * 客户端图标
	 */
	private String clientIcon;

	/**
	 * 应用秘钥
	 */
	private String clientSecret;

	/**
	 * 应用签约的所有权限, 多个用逗号隔开
	 */
	private String contractScope;

	/**
	 * 应用允许授权的所有URL, 多个用逗号隔开
	 */
	private String allowUrl;

	/** 此 Client 是否打开模式：授权码（Authorization Code） */
	private Boolean isCode = false;

	/** 此 Client 是否打开模式：隐藏式（Implicit） */
	private Boolean isImplicit = false;

	/** 此 Client 是否打开模式：密码式（Password） */
	private Boolean isPassword = false;

	/** 此 Client 是否打开模式：凭证式（Client Credentials） */
	private Boolean isClient = false;

	/**
	 * 是否自动判断此 Client 开放的授权模式
	 * <br> 此值为true时：四种模式（isCode、isImplicit、isPassword、isClient）是否生效，依靠全局设置
	 * <br> 此值为false时：四种模式（isCode、isImplicit、isPassword、isClient）是否生效，依靠局部配置+全局配置
	 */
	private Boolean isAutoMode = true;

	/** 单独配置此Client：是否在每次 Refresh-Token 刷新 Access-Token 时，产生一个新的 Refresh-Token [默认取全局配置] */
	private Boolean isNewRefresh;

	/** 单独配置此Client：Access-Token 保存的时间(单位秒)  [默认取全局配置] */
	private long accessTokenTimeout;

	/** 单独配置此Client：Refresh-Token 保存的时间(单位秒) [默认取全局配置] */
	private long refreshTokenTimeout;

	/** 单独配置此Client：Client-Token 保存的时间(单位秒) [默认取全局配置] */
	private long clientTokenTimeout;

	/** 单独配置此Client：Past-Client-Token 保存的时间(单位：秒) [默认取全局配置] */
	private long pastClientTokenTimeout;

	@TableLogic(value = "0",delval = "1")
	private Integer del;
}