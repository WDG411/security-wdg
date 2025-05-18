package com.cgr.filter;

import com.cgr.utils.JwtUtil;
import com.cgr.service.impl.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //放行 登录相关的请求
        if (request.getRequestURI().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        //获取请求头中的token
        String token = parseJwt(request);

        if(token == null){
            throw new BadCredentialsException("token缺失");
        }

        String jwtKey = "user:";
        //取得token中的用户标识（注意解析的异常处理）
        try{
            Claims  claims = JwtUtil.parseToken(token);
            Integer id = claims.get("id", Integer.class);
            jwtKey = jwtKey + id;
        }catch (Exception e){
            throw new BadCredentialsException("token无效");
        }

        //从 redis 中获取用户信息
        //MyUserDetails userDetails =(MyUserDetails)redisTemplate.opsForValue().get(jwtKey);

        MyUserDetails userDetails = (MyUserDetails)redisTemplate.opsForValue().get(jwtKey);
        //MyUserDetails userDetails = new ObjectMapper().convertValue(o, MyUserDetails.class);

        if(userDetails == null){
            throw new BadCredentialsException("redis错误");
        }

        //将用户信息存入安全上下文
        UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //放行
        filterChain.doFilter(request, response);

    }


    /**
     * 从 HTTP 请求的 "Authorization" 头中提取并解析出 JWT（JSON Web Token）。
     * <p>
     * 如果该请求头存在，并且以 "Bearer " 为前缀，则截取令牌部分并返回。
     * 否则返回 null，表示请求中没有有效的 JWT。
     *
     * @param request 用于提取 HTTP 头信息的 HttpServletRequest 对象
     * @return 提取出的 JWT 字符串，或在未找到时返回 null
     */
    private String parseJwt(HttpServletRequest request) {
        // 从请求头中获取 "Authorization" 字段的值
        String headerAuth = request.getHeader("Authorization");

        // 校验 headerAuth 是否有文本内容，且以 "Bearer " 前缀开头
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // 去掉 "Bearer " 前缀，只保留实际的 JWT 部分
            return headerAuth.substring(7);
        }

        // 未能从请求头中获取到有效的 Bearer Token，则返回 null
        return null;
    }
}
