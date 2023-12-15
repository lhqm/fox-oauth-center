package com.pj.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pj.mapper.ClientInfoMapper;
import com.pj.model.entity.ClientInfo;
import com.pj.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;

/**
 * Sa-Token OAuth2.0 整合实现 
 * @author click33
 */
@Component
public class SaOAuth2TemplateImpl extends SaOAuth2Template {
	@Autowired
	private ClientInfoMapper clientInfoMapper;
	
	// 根据 id 获取 Client 信息 
	@Override
	public SaClientModel getClientModel(String clientId) {
		// 查询客户端数据
		LambdaQueryWrapper<ClientInfo> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ClientInfo::getClientId, clientId);
		ClientInfo clientInfo = clientInfoMapper.selectOne(wrapper);
//		存在客户端就返回给satoken执行器进行执行
		if(clientInfo!= null) {
			return BeanCopyUtils.copyBean(clientInfo, SaClientModel.class);
		}
		return null;
	}
	
	// 根据ClientId 和 LoginId 获取openid 
	@Override
	public String getOpenid(String clientId, Object loginId) {
		// 此为模拟数据，真实环境需要从数据库查询 
		return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
	}
	
	// -------------- 其它需要重写的函数 
	
}
