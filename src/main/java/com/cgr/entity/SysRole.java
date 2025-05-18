package com.cgr.entity;

import java.time.LocalDateTime;


public class SysRole {
    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SysRole() {
    }

    public SysRole(Long id, String roleName, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
