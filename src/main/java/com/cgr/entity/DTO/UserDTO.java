package com.cgr.entity.DTO;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<String> roleList;

    public UserDTO() {
    }


    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public UserDTO(Long id, String username, String password, String email, List<String> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
