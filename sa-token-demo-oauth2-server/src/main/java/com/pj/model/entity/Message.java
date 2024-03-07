package com.pj.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
//    转到用户
    private String toUser;
//    转到应用
    private String clientId;
    private String clientSecret;
    private String agentId;
}
