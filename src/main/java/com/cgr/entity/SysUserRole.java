package com.cgr.entity;

import java.time.LocalDateTime;


public class SysUserRole {
    private Long userId;
    private Long roleId;
    private LocalDateTime assignedAt;

    public SysUserRole(LocalDateTime assignedAt, Long roleId, Long userId) {
        this.assignedAt = assignedAt;
        this.roleId = roleId;
        this.userId = userId;
    }

    public SysUserRole() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
