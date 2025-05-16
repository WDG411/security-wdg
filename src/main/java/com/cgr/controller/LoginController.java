package com.cgr.controller;

import com.cgr.entity.LoginDTO;
import com.cgr.entity.ResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseModel<String> login(@RequestBody LoginDTO loginDTO) {
        return ResponseModel.success("登录成功");
    }
}
