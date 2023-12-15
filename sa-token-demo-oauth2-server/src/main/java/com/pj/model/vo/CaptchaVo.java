package com.pj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/15 9:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaVo {
    private String uuid;
    private String captcha;
}
