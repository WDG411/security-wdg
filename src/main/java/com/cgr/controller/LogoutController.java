package com.cgr.controller;

import com.cgr.entity.ResponseModel;
import com.cgr.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @Autowired
    private LogoutService  logoutService;

    @PostMapping("/logout")
    public ResponseModel logout(){
        logoutService.logout();
        return ResponseModel.success("退出成功");
    }
}
