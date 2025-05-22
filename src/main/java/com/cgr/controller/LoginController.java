package com.cgr.controller;

import com.cgr.entity.DTO.LoginDTO;
import com.cgr.entity.DTO.UserDTO;
import com.cgr.entity.ResponseModel;
import com.cgr.entity.SysUser;
import com.cgr.service.LoginService;
import com.cgr.service.impl.UserDetailsManagerImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserDetailsManagerImpl userDetailsManager;

    @PostMapping("/login")
    public ResponseModel login(@RequestBody LoginDTO loginDTO) {
        return loginService.checkLogin(loginDTO);
    }


    @PostMapping("/register")
    public ResponseModel register(@RequestBody UserDTO userDTO) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);

        return userDetailsManager.createUser(user);
    }

}
