package com.pj.oauth2.qywx;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthWeChatEnterpriseQrcodeRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/qywx")
public class RestAuthController {
    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }
    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }
    private AuthRequest getAuthRequest() {
        return new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder()
                .clientId("ww6d375b4fddaca967")
                .clientSecret("WcJbzoZe8rbCU5Ha02YeaOtale01urf8A5xJi0KBPSY")
                .redirectUri("http://ram.mynetworks.net")
                .agentId("1000002")
                .build());
    }
}
