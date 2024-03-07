package com.pj.model.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/2/1 17:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenStore {
    private String token;
    private String agentId;
}
