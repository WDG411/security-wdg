package com.cgr.config;

import com.cgr.filter.JwtAuthenticationFilter;
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
                        .anyRequest().authenticated())
                // 2. 异常处理
                .exceptionHandling(exception ->{
                    exception.authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler);
                })
                // 3. 关闭 CSRF（如果前后端分离且用 Token，可关闭）
                .csrf(csrf -> csrf.disable())
                // 4. 无状态会话（如果用 JWT，可禁用 session）
                //.sessionManagement(session -> session.disable())
                // 5. 启用 OAuth2 登录，使用默认登录页（会自动生成一个“选择提供商”的页面）
                //.oauth2Login(Customizer.withDefaults())
                // 6. 禁用不需要的过滤器
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class);

        return http.build();
    }

    //    —— 如果你有自定义的 /login 页面，用下面这一段替换上面那行：
    // .oauth2Login(oauth -> oauth
    //     .loginPage("/login")                  // 自定义登录入口
    //     .defaultSuccessUrl("/", true)         // 成功后跳转
    //     .failureUrl("/login?error")           // 失败跳转
    //     .userInfoEndpoint(userInfo -> userInfo
    //         .userService(customOAuth2UserService())
    //     )
    // )

}
