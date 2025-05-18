package com.cgr.service.impl;

import com.cgr.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void logout() {
        String redisKey = "user:";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();

        //清空redis信息
        Long id = userDetails.getUser().getId();
        redisKey += id;

        redisTemplate.delete(redisKey);

        //清空安全上下文
        SecurityContextHolder.clearContext();
    }
}
