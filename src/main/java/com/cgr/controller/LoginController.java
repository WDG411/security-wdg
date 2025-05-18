package com.cgr.controller;

import com.cgr.entity.DTO.LoginDTO;
import com.cgr.entity.ResponseModel;
import com.cgr.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseModel login(@RequestBody LoginDTO loginDTO) {
        return loginService.checkLogin(loginDTO);
    }

}
