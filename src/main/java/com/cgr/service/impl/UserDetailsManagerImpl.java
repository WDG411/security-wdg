package com.cgr.service.impl;

import com.cgr.entity.ResponseModel;
import com.cgr.entity.SysUser;
import com.cgr.entity.VO.LoginVO;
import com.cgr.mapper.*;
import com.cgr.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.cgr.constant.Role.ROLE_USER;

@Component
public class UserDetailsManagerImpl implements UserDetailsManager {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }
        Long userId = user.getId();
        List<String> roleList = roleMapper.selectRoleByUserId(userId);
        List<String> authorityList = authorityMapper.selectAuthorityByUserId(userId);

        return new MyUserDetails(user, roleList, authorityList);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseModel createUser(SysUser user) throws DuplicateKeyException, AuthenticationException {

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userMapper.insert(user);


        Long id = user.getId();
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(ROLE_USER.getId());

        userRoleMapper.insertBatch(id, roleIds, LocalDateTime.now());

        List<String> roleList = new ArrayList<>();
        roleList.add(ROLE_USER.getRoleName());

        List<String> authorityList = authorityMapper.selectAuthorityByUserId(id);

        MyUserDetails userDetails = new MyUserDetails(user, roleList, authorityList);


        Authentication authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        //存入安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", userDetails.getUsername());
        String token = JwtUtil.generateToken(claims);

        //存入redis
        String jwtKey = "user:" + id;
        redisTemplate.opsForValue().set(jwtKey, user, 60, TimeUnit.MINUTES);


        return ResponseModel.success(new LoginVO(userDetails.getUsername(), userDetails.getUser().getEmail(), token));

    }

    public void createUser(UserDetails user) {
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }


}
