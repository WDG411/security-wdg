package com.cgr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityWdgApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("1234"));
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass  = encoder.encode("1234");
        System.out.println(pass);
        System.out.println(encoder.matches("1234", pass));
    }




}
