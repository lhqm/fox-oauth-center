package com.pj.service;

import cn.dev33.satoken.util.SaResult;
import com.pj.model.dto.CardMessageDto;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:17
 */
public interface MessageService {
    SaResult sendCardMessage(CardMessageDto cardMessageDto);
}
