package com.cgr.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AuthorityMapper {
    List<String> selectAuthorityByUserId(Long userId);

    String selectByroleName(String roleName);

    /**
     * 给用户分配角色
     * @param id
     * @param roleIds
     * @param now
     */
    void insertBatch(Long id, List<Integer> roleIds, LocalDateTime now);

}
