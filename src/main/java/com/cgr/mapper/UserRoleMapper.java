package com.cgr.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserRoleMapper {
    /**
     * 批量插入用户角色关系
     * @param userId
     * @param roleIds
     * @param now
     */
    void insertBatch(Long userId, List<Integer> roleIds, LocalDateTime now);
}
