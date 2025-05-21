package com.cgr.controller;

import com.cgr.entity.DTO.UserDTO;
import com.cgr.entity.ResponseModel;
import com.cgr.entity.SysUser;
import com.cgr.service.impl.MyUserDetails;
import com.cgr.service.impl.UserDetailsManagerImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDetailsManagerImpl userDetailsManager;

    /**
     * 新增用户
     */
    @PostMapping("/save")
    public ResponseModel save(@RequestBody UserDTO userDTO){
        SysUser user = new SysUser();

        BeanUtils.copyProperties(userDTO,user);

        MyUserDetails userDetails = new MyUserDetails(user,userDTO.getRoleList(),null);

        return userDetailsManager.createUser(userDetails);
    }
}
