package com.cgr.entity.VO;

public class LoginVO {
    private String username;
    //private String password;
    //....
    private String email;

    private String token;

    public LoginVO(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public LoginVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
