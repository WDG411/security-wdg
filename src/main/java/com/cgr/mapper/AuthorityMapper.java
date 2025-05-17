package com.cgr.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    List<String> selectByUserId(Long userId);
}
