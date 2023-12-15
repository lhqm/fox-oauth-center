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
 * @date 2023/12/12 9:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("authorization_user")
public class AuthUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long localId;
    private Integer clientId;
    private String platform;
    private Long binding;
    private String bindingCode;
    private String account;
    private String password;
    private String avatar;
    private String nickName;
    private String email;
    private Integer gender;
    private Long phone;
    private Integer active;
    @TableLogic(value = "0",delval = "1")
    private Integer del;

}
