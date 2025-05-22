package com.cgr.handler;

import com.cgr.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 从 Spring Security Context 里拿到 OAuth2User
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        // 根据第三方返回的属性名取用户名，Gitee 通常是 "name" 或 "login"
        String username = oAuth2User.getAttribute("name");
        if (username == null) {
            username = oAuth2User.getAttribute("login");
        }

        // 构造 JWT 的载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        // 生成 JWT（你自己的工具类）
        String token = JwtUtil.generateToken(claims);

        // 重定向到前端页面，并把 token 和用户名作为 URL 参数
        String redirectUrl = "http://localhost:5173/index?token=" 
            + URLEncoder.encode(token, StandardCharsets.UTF_8)
            + "&username=" 
            + URLEncoder.encode(username, StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }
}
