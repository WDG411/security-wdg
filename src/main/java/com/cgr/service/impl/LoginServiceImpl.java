package com.cgr.service.impl;

import com.cgr.entity.DTO.LoginDTO;
import com.cgr.entity.VO.LoginVO;
import com.cgr.entity.ResponseModel;
import com.cgr.entity.SysUser;
import com.cgr.mapper.UserMapper;
import com.cgr.service.LoginService;
import com.cgr.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseModel checkLogin(LoginDTO loginDTO) {
        //得到用户名和密码参数
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        SysUser user = new SysUser();
        String token = "";

        //封装到待认证的 Authentication 对象中
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        try {
            //发起认证，得到已认证的 Authentication 对象   这一步容易出异常
            Authentication authentication = authenticationManager.authenticate(unauthenticated);
            if (authentication.isAuthenticated()) {
                //获取认证后的用户信息
                MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
                user = principal.getUser();

                //认证成功
                //存入安全上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //生成token
                Map<String, Object> claims = new HashMap<>();
                claims.put("id",user.getId());
                claims.put("username",user.getUsername());
                token =JwtUtil.generateToken(claims);

                //写入redis
                String jwtKey = "user:" + user.getId();
                redisTemplate.opsForValue().set(jwtKey,principal,1000*60*60, TimeUnit.MILLISECONDS);


            }
        } catch (AuthenticationException e) {
            e.printStackTrace(); // 打印到控制台
            return ResponseModel.error(e.getMessage());
        }
        //认证成功
        return ResponseModel.success(new LoginVO(user.getUsername(), user.getEmail(),token));
    }
}
