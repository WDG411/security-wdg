package com.cgr.entity;

import java.time.LocalDateTime;


public class SysRolePermission {
    private Long roleId;
    private Long permId;
    private LocalDateTime assignedAt;

    public SysRolePermission(Long roleId, Long permId, LocalDateTime assignedAt) {
        this.roleId = roleId;
        this.permId = permId;
        this.assignedAt = assignedAt;
    }

    public SysRolePermission() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermId() {
        return permId;
    }

    public void setPermId(Long permId) {
        this.permId = permId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
