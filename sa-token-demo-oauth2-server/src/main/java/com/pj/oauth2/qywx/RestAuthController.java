package com.pj.oauth2.qywx;

import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pj.model.entity.AuthUser;
import com.pj.service.impl.UserDetailService;
import me.zhyd.oauth.cache.AuthDefaultStateCache;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthWeChatEnterpriseQrcodeRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatEnterpriseThirdQrcodeRequest;
import me.zhyd.oauth.request.AuthWeChatEnterpriseWebRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/qywx")
public class RestAuthController {
    @Autowired
    private QYWXUserConvert convert;
    @Autowired
    private UserDetailService userDetailService;
    @Value("${qywx.corp.app-secret}")
    private String appSecret;
    @Value("${qywx.corp.corp-id}")
    private String appId;
    @Value("${qywx.corp.agent-id}")
    private String agentId;
    @Value("${qywx.corp.redirect-uri}")
    private String redirectUri;
    @Value("${qywx.baseUrl}")
    private String baseUrl;
    @GetMapping("/baseUrl")
    public SaResult getBaseUrl() {
        return SaResult.data(baseUrl+"/render");
    }
    @GetMapping("/nativeBaseUrl")
    public SaResult getNativeBaseUrl() {
        return SaResult.data(baseUrl+"/nativeRender");
    }

    /**
     * 扫码构造器
     * @param response 响应
     * @throws IOException
     */
    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }
//    @RequestMapping("/thirdRender")
//    public void thirdRenderAuth(HttpServletResponse response) throws IOException {
//        AuthRequest authRequest = getThirdRequest();
//        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
//    }
    @GetMapping("/codeRender")
    public SaResult codeRender() {
        JSONObject res=new JSONObject();
        res.put("appId", appId);
        res.put("agentId", agentId);
        res.put("redirectUri", redirectUri);
//        生成随机state
        String state = AuthStateUtils.createState();
//        state存入上下文
        getAuthRequest().authorize(state);
        System.out.println("state:"+state);
        res.put("state", state);
        res.put("scope","snsapi_base");
        return SaResult.data(res);
    }

    /**
     * 扫码用该接口
     * @param callback
     * @return 返回扫码回调
     */
    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        System.out.println("进入扫码授权回调，输出信息："+JSON.toJSONString(callback));
        AuthRequest authRequest = getAuthRequest();
        System.out.println(JSON.toJSONString(callback));
        // 登录成功后的回调信息
        AuthResponse<?> login = authRequest.login(callback);
        //获取到回调域返回的用户信息数据
        String userData = JSON.toJSONString(login.getData());
        System.out.println(userData);
        //回调信息送入加工成用户信息，并且接入查询是否有用户，返回用户编号进行登录
        AuthUser authUser = convert.convertToLocalUserFromJSONString(userData);
        return userDetailService.loadUserByModel(authUser,null,false);
//        return login;
    }

    /**
     * request对象构造器
     * 如果是企业微信内部应用，需要使用企业微信的请求对象构造器，构造的URL如下：
     * <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww6a7bc28e96f07af5&redirect_uri=http://10.10.0.29:8001/qywx/callback&response_type=code&scope=snsapi_privateinfo&state=STATE&agentid=1000031#wechat_redirect">...</a>
     * @return AuthRequest 授权请求对象
     */
    private AuthRequest getAuthRequest() {
        return new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder()
                .clientId(appId)
                .clientSecret(appSecret)
                .redirectUri(redirectUri)
                .agentId(agentId)
                .scopes(List.of("snsapi_privateinfo"))
                .build());
    }

    /**
     * 内联授权构造器
     * @param response 响应
     * @throws IOException
     */
    @RequestMapping("/nativeRender")
    public void nativeRenderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getNativeRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    /**
     * 内联授权回调
     * @param callback
     * @return 返回内联浏览器响应回调
     */
    @RequestMapping("/nativeCallback")
    public Object nativeLogin(AuthCallback callback) {
        AuthRequest authRequest = getNativeRequest();
        // 登录成功后的回调信息
        AuthResponse<?> login = authRequest.login(callback);
        //获取到回调域返回的用户信息数据
        String userData = JSON.toJSONString(login.getData());
        //回调信息送入加工成用户信息，并且接入查询是否有用户，返回用户编号进行登录
        AuthUser authUser = convert.convertToLocalUserFromJSONString(userData);
        return userDetailService.loadUserByModel(authUser,null,false);
//        return login;
    }



    /**
     * request对象构造器
     * 该构造器用于企业微信内嵌浏览器，通过向浏览器发起名为#wechat_redirect的请求，来快速获取授权码，避免授权服务器登录校验
     * @return
     */

    private AuthRequest getNativeRequest(){
        return new AuthWeChatEnterpriseWebRequest(AuthConfig.builder()
                .clientId(appId)
                .clientSecret(appSecret)
                .redirectUri(redirectUri+"na")
//                .agentId(agentId)
//                .scopes(List.of("snsapi_privateinfo"))
                .build());
    }

    @RequestMapping("/thirdCallback")
    public Object thirdLogin(AuthCallback callback) {
        System.out.println("进入三方授权回调，输出信息："+JSON.toJSONString(callback));
        AuthRequest authRequest = getThirdRequest();
        // 登录成功后的回调信息
        AuthResponse<?> login = authRequest.login(callback);
        //获取到回调域返回的用户信息数据
        String userData = JSON.toJSONString(login.getData());
        //回调信息送入加工成用户信息，并且接入查询是否有用户，返回用户编号进行登录
        AuthUser authUser = convert.convertToLocalUserFromJSONString(userData);
        return userDetailService.loadUserByModel(authUser,null,false);
    }

    private AuthRequest getThirdRequest(){
        return new AuthWeChatEnterpriseThirdQrcodeRequest(AuthConfig.builder()
               .clientId(appId)
               .clientSecret(appSecret)
               .redirectUri(redirectUri+"th")
               .agentId(agentId)
               .scopes(List.of("snsapi_privateinfo"))
                .build()
               );
    }
}
