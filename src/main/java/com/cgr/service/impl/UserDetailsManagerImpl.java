package com.cgr.service.impl;

import com.cgr.entity.ResponseModel;
import com.cgr.entity.SysUser;
import com.cgr.entity.VO.LoginVO;
import com.cgr.mapper.AuthorityMapper;
import com.cgr.mapper.RoleMapper;
import com.cgr.mapper.UserMapper;
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
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "不存在" );
        }
        Long userId = user.getId();
        List<String> roleList = roleMapper.selectRoleByUserId(userId);
        List<String> authorityList = authorityMapper.selectAuthorityByUserId(userId);

        return new MyUserDetails(user,roleList,authorityList);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseModel createUser(MyUserDetails user) {

        SysUser newUser = user.getUser();
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        try {
            userMapper.insert(newUser);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return ResponseModel.error("用户名已存在");
        }

        Long id = newUser.getId();
        List<String> roleList = user.getRoleList();

        if(roleList ==  null || roleList.isEmpty()){
            roleList = List.of(ROLE_USER.getRoleName());
        }
        List<Integer> roleIds = roleMapper.selectRoleIdsByName(roleList);

        authorityMapper.insertBatch(id,roleIds, LocalDateTime.now());

        user.setAuthorityList(authorityMapper.selectAuthorityByUserId(id));
        user.setRoleList(roleList);

        String token="";

        try {
            Authentication authentication =
                    UsernamePasswordAuthenticationToken.authenticated(
                            user,
                            null,
                            user.getAuthorities()
                    );
            if(authentication.isAuthenticated()){
                //存入安全上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //生成token
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",id);
                claims.put("username",newUser.getUsername());
                token = JwtUtil.generateToken(claims);

                //存入redis
                String jwtKey = "user:" + id;
                redisTemplate.opsForValue().set(jwtKey,user,60, TimeUnit.MINUTES);

            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            ResponseModel.error("注册失败");
        }



        return ResponseModel.success(new LoginVO(newUser.getUsername(), newUser.getEmail(),token));

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
