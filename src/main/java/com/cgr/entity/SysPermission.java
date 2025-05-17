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
public class SysPermission {
    private Long id;
    private String permName; // 如 user:create
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
