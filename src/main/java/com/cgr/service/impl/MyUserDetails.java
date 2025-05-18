package com.cgr.service.impl;

import com.cgr.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MyUserDetails implements UserDetails {


    private SysUser user;

    private List<String> roleList;

    private List<String> authorityList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = authorityList.stream().map(SimpleGrantedAuthority::new).toList();
        return list;
    }

    public MyUserDetails(SysUser user, List<String> roleList, List<String> authorityList) {
        this.user = user;
        this.roleList = roleList;
        this.authorityList = authorityList;
    }

    public MyUserDetails() {
    }

    public List<String> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<String> authorityList) {
        this.authorityList = authorityList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public List<String> getRoleList() {
        return roleList;
    }



    /**
     * 防止redis中存入两份username，以及密码不显示
     * @return
     */

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

}
