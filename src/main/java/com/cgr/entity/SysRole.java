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
public class SysRole {
    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
