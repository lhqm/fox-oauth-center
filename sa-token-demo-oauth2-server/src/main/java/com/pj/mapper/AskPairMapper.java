package com.pj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pj.model.entity.AskKeyPair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2024/2/1 15:12
 */
@Mapper
public interface AskPairMapper extends BaseMapper<AskKeyPair> {
    List<AskKeyPair> getClientDataByClientId(@Param("id") String clientId);
}
