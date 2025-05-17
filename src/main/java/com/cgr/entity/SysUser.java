package com.cgr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUser implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer status=1; // 0=禁用，1=启用
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
}
