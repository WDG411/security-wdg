package com.cgr.service.impl;

import com.cgr.entity.SysUser;
import com.cgr.mapper.AuthorityMapper;
import com.cgr.mapper.RoleMapper;
import com.cgr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.cgr.constant.Role.ROLE_USER;

@Component
public class UserDetailsManagerImpl implements UserDetailsManager {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private RoleMapper roleMapper;

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
    public void createUser(UserDetails user) {
        MyUserDetails userDetails = (MyUserDetails) user;
        SysUser newUser = userDetails.getUser();
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        userMapper.insert(newUser);

        Long id = newUser.getId();
        List<String> roleList = userDetails.getRoleList();

        if(roleList ==  null || roleList.isEmpty()){
            roleList = List.of(ROLE_USER.getRoleName());
        }
        List<Integer> roleIds = roleMapper.selectRoleIdsByName(roleList);

        authorityMapper.insertBatch(id,roleIds, LocalDateTime.now());

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
