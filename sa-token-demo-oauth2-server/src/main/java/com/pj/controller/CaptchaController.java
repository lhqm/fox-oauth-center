package com.pj.controller;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.pj.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/14 18:02
 */
@RestController
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;
    /**
     * 生成验证码
     *
     * @param httpServerRequest
     * @param httpServerResponse
     */
    @GetMapping("/generateCaptcha")
    public SaResult generateCaptcha(HttpServletRequest httpServerRequest, HttpServletResponse httpServerResponse) {
        return captchaService.generateCaptcha(httpServerRequest, httpServerResponse);
    }
    @GetMapping("/verifyCaptcha")
    public SaResult verifyCaptcha(HttpServletRequest request, String code) {
        return captchaService.verifyCaptcha(request, code);
    }

}
