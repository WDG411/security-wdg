package com.cgr.service.impl;

import com.cgr.entity.SysUser;
import com.cgr.mapper.AuthorityMapper;
import com.cgr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsManagerImpl implements UserDetailsManager {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorityMapper authorityMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "不存在" );
        }
        Long userId = user.getId();
        List<String> authorityList = authorityMapper.selectByUserId(userId);

        return new MyUserDetails(user,authorityList);
    }


    @Override
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
