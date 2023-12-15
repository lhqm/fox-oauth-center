package com.pj.oauth2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.pj.model.entity.AuthUser;
import com.pj.model.entity.ClientInfo;
import com.pj.service.ClientService;
import com.pj.service.impl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

/**
 * Sa-OAuth2 Server端 控制器
 * @author click33
 * 
 */
@RestController
public class SaOAuth2ServerController {
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private ClientService clientService;

	// 处理所有OAuth相关请求 
	@RequestMapping("/oauth2/*")
	public Object request() {
		System.out.println("------- 进入授权请求: " + SaHolder.getRequest().getUrl());
		return SaOAuth2Handle.serverRequest();
	}
	
	// Sa-OAuth2 定制化配置 
	@Autowired
	public void setSaOAuth2Config(SaOAuth2Config cfg) {
		cfg.
			// 未登录的视图 
			setNotLoginView(()->{
				return new ModelAndView("login.html");	
			}).
			// 登录处理函数 
			setDoLoginHandle((name, pwd) -> userDetailService.loadAndInitUser(name,pwd)).
			// 授权确认视图 
			setConfirmView((clientId, scope)->{
				Map<String, Object> map = new HashMap<>();
				ClientInfo clientInfo=clientService.getClientInfo(clientId);
				//封装客户端信息
				map.put("clientId", clientId);
				map.put("scopes", scope);
				map.put("clientName", clientInfo.getClientName());
				map.put("clientIcon", clientInfo.getClientIcon());
				//封装个人信息
//				AuthUser authUser = userDetailService.getAuthUser();
				map.put("account", StpUtil.getExtra("account"));
				map.put("avatar", StpUtil.getExtra("avatar"));
				map.put("nickName",StpUtil.getExtra("nickName"));

				return new ModelAndView("confirm.html", map); 
			});
	}


	// 全局异常拦截  
	@ExceptionHandler
	public SaResult handlerException(Exception e) {
		e.printStackTrace(); 
		return SaResult.error(e.getMessage());
	}

	//获取用户是否登录
	@RequestMapping("/oauth2/isLogin")
	public SaResult isLogin() {
		return SaResult.data(StpUtil.isLogin());
	}
	
	
	// ---------- 开放相关资源接口： Client端根据 Access-Token ，置换相关资源 ------------ 
	
	// 获取Userinfo信息：昵称、头像、性别等等
	@RequestMapping("/oauth2/userinfo")
	public SaResult userinfo() {
		// 获取 Access-Token 对应的账号id 
		String accessToken = SaHolder.getRequest().getParamNotNull("access_token");
		Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
		System.out.println("-------- 此Access-Token对应的账号id: " + loginId);
		
		// 校验 Access-Token 是否具有权限: userinfo
//		SaOAuth2Util.checkScope(accessToken, "userinfo");
		
		// 查询数据库获取信息
		return SaResult.data(userDetailService.getAuthUser(accessToken));
	}
	
}
