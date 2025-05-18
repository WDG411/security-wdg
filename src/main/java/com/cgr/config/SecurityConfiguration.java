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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception ->{
                    exception.authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler);
                })
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.disable())
                .logout(logout -> logout.disable())
                .formLogin(form -> form.disable())
                .addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class);

        return http.build();
    }
}
