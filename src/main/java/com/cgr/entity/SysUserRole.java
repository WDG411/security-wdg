package com.cgr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserRole {
    private Long userId;
    private Long roleId;
    private LocalDateTime assignedAt;
}
