package com.pj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/2/1 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oauth_ask_pair")
public class AskKeyPair {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer clientId;
    private String corpId;
    private String corpSecret;
    private Integer channel;
    private String appId;
    private String redirectUrl;
    @TableLogic(value = "0",delval = "1")
    private Integer del;
}
