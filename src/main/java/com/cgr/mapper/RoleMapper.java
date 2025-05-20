package com.cgr.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Integer> selectRoleIdsByName(List<String> roleList);

    List<String> selectRoleByUserId(Long userId);
}
