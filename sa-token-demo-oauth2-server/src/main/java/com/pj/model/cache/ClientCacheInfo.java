package com.pj.model.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/2/1 15:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientCacheInfo {
    private String clientSecret;
    private String corpId;
    private String corpSecret;
    private String appId;
}
