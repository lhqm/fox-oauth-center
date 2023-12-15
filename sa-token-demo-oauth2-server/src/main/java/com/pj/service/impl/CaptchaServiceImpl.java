package com.pj.service.impl;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.RandomUtil;
import com.pj.model.vo.CaptchaVo;
import com.pj.service.CaptchaService;
import com.pj.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.UUID;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/14 18:04
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public SaResult generateCaptcha(HttpServletRequest httpServerRequest, HttpServletResponse httpServerResponse) {
        // 生成验证码四则运算
        GifCaptcha lineCaptcha = CaptchaUtil.createGifCaptcha(200, 100);
        lineCaptcha.setGenerator(new MathGenerator(1));//参数表示几位运算符
//        System.out.println(lineCaptcha.getCode());
        lineCaptcha.setBackground(getRandomColor(0,255));
        String uuid = UUID.randomUUID().toString();
        httpServerRequest.getSession().setAttribute("captcha", lineCaptcha);
//        redisUtil.setCacheObject("CAPTCHA:"+uuid, lineCaptcha);
        return SaResult.data(new CaptchaVo(uuid, lineCaptcha.getImageBase64Data()));
    }

    @Override
    public SaResult verifyCaptcha(HttpServletRequest request, String code) {
        if (request.getSession().getAttribute("captcha")!= null) {
            GifCaptcha lineCaptcha = (GifCaptcha) request.getSession().getAttribute("captcha");
            if (lineCaptcha.verify(code)) {
                return SaResult.ok("验证成功");
            }
        }
        return SaResult.error("验证码错误");
    }

    private Color getRandomColor(int min, int max) {
        if (min > 255) {min = 255;}
        if (max > 255) {max = 255;}
        if (min < 0) {min = 0;}
        if (max < 0) {max = 0;}
        if (min > max) {min = 0;max = 255;}
        return new Color(RandomUtil.randomInt(min, max), RandomUtil.randomInt(min, max), RandomUtil.randomInt(min, max));
    }
}
