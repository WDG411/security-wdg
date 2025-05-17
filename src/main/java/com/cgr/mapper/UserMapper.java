package com.cgr.mapper;

import com.cgr.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    SysUser selectByUsername(String username);
}
