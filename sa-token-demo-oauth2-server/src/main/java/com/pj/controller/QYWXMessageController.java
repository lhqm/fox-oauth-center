package com.pj.controller;

import cn.dev33.satoken.util.SaResult;
import com.pj.model.dto.CardMessageDto;
import com.pj.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 15:48
 */
@RestController
@RequestMapping("/qwms")
public class QYWXMessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("/sendCard")
    public SaResult sendCard(@RequestBody CardMessageDto cardMessageDto) {
        return messageService.sendCardMessage(cardMessageDto);
    }
}
