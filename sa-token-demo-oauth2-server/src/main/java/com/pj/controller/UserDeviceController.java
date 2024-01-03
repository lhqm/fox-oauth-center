package com.pj.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/20 15:51
 */
@RestController
@RequestMapping("/user")
public class UserDeviceController {
    @GetMapping("/device")
    public SaResult device() {
        return SaResult.data(StpUtil.getLoginDevice());
    }
}
