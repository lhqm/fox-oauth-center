package com.pj.model.dto;

import com.pj.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/1/31 16:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardMessageDto extends Message {
    private TextCard textCard;
}

