package com.cgr.config;

import com.cgr.filter.JwtAuthenticationFilter;
import com.cgr.handler.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private AuthenticationEntryPoint  authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter  jwtAuthenticationFilter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private OAuth2LoginSuccessHandler AuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        //匹配合适的AuthenticationProvider(默认有DaoAuthenticationProvider)
        DaoAuthenticationProvider  provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        //配置基于数据库认证的UserDetailsService对象
        provider.setUserDetailsService(userDetailsManager);
        //创建并返回认证管理器对象
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 授权规则：放行登录页和 OAuth2 回调端点，其他都要认证
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        //访问不存在的资源放行，让MVC抛出 404
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                // 2. 异常处理
                .exceptionHandling(exception ->{
                    exception.authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler);
                })
                //开启OAuth2 登录
                // 3. 启用 OAuth2 登录（使用默认设置即可）
                .oauth2Login(auth -> auth.successHandler(AuthenticationSuccessHandler))
                // 3. 关闭 CSRF（如果前后端分离且用 Token，可关闭）
                .csrf(csrf -> csrf.disable())
                // 4. 无状态会话（如果用 JWT，可禁用 ,session,OAuth登录会用到session）
                //.sessionManagement(session -> session.disable())
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class);

        return http.build();
    }

}
