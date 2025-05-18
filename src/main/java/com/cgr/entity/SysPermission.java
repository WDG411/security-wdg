package com.cgr.entity;

import java.time.LocalDateTime;


public class SysPermission {
    private Long id;
    private String permName; // 如 user:create
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SysPermission(Long id, String permName, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.permName = permName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public SysPermission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
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
