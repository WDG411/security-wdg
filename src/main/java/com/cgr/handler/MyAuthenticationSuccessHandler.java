package com.cgr.handler;

import com.alibaba.fastjson2.JSON;
import com.cgr.entity.ResponseModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //响应头设置，JSON
        response.setContentType("application/json;charset=UTF-8");
        //创建输出流对象
        PrintWriter writer = response.getWriter();
        //构建输出数据
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResponseModel<String> responseModel = ResponseModel.success(userDetails.getUsername());
        //向前端响应数据
        String json = JSON.toJSONString(responseModel);
        writer.println(json);
    }
}
