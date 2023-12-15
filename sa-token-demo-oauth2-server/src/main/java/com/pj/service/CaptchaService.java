package com.pj.service;

import cn.dev33.satoken.util.SaResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/14 18:03
 */
public interface CaptchaService {
    SaResult generateCaptcha(HttpServletRequest httpServerRequest, HttpServletResponse httpServerResponse);

    SaResult verifyCaptcha(HttpServletRequest request, String code);
}
