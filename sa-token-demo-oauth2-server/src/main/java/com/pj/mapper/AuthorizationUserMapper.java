package com.pj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pj.model.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/12/12 9:28
 */
@Mapper
public interface AuthorizationUserMapper extends BaseMapper<AuthUser> {
}
