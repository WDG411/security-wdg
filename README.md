# security-wdg

一个基于 Spring Boot + Spring Security + JWT + Redis 的权限认证系统，实现了 RBAC（基于角色的访问控制）模型，支持用户登录、鉴权、权限管理等功能，适合用于权限管理系统的学习或二次开发。

---

## 🚀 技术栈

- Spring Boot
- Spring Security
- JWT（JSON Web Token）
- Redis（用于缓存用户信息）
- MyBatis + MySQL
- Maven

---

## 🔐 功能简介

- 用户名 + 密码 登录认证，JWT 令牌生成与验证
- 登录信息缓存至 Redis，实现无状态会话
- RBAC 权限控制模型（用户-角色-权限）
- 自定义认证失败、授权失败处理器
- 用户管理接口（注册、保存）
- 使用 `UserDetailsManager` 接口自定义用户管理逻辑
- 基于 Spring Security 过滤器链实现请求拦截与权限控制

---

## 🧩 主要模块说明

### 1. 用户登录流程

- 用户调用 `/login` 接口，提交用户名与密码。
- 后端由 `LoginServiceImpl` 构造 `UsernamePasswordAuthenticationToken`，通过 `AuthenticationManager` 认证。
- 认证成功后，生成 JWT，提取用户 ID，构建键名如 `user:{userId}` 存入 Redis，有效期 5 分钟。
- 最终返回 `LoginVO` 对象，包括 token、用户名、邮箱等。

### 2. JWT 认证过滤器

- 自定义过滤器 `JwtAuthenticationFilter` 实现 `OncePerRequestFilter`，在每次请求时拦截并解析请求头中的 JWT。
- 若 JWT 有效，则从 Redis 取出对应用户信息，封装为 `UsernamePasswordAuthenticationToken`，设置到 Spring Security 上下文中。

### 3. 权限控制（RBAC）

- 用户与角色为多对多关系，维护于 `sys_user_role` 表中。
- 角色与权限为多对多关系，维护于 `sys_role_permission` 表中。
- 登录后通过 `AuthorityMapper.selectByUserId(userId)` 查询权限字符串集合，存入 `UserDetails` 中。
- 请求访问接口时，通过 Spring Security 判断用户是否具有相应权限。

---

## 🛠️ 关键类解析

| 类名 | 说明 |
|------|------|
| `SecurityConfiguration` | Spring Security 配置类，配置认证管理器、过滤器链、异常处理器等 |
| `LoginServiceImpl` | 登录逻辑，认证、生成 JWT、缓存用户 |
| `JwtAuthenticationFilter` | JWT 认证过滤器，解析 Token 并加载用户信息 |
| `MyUserDetails` | 实现 `UserDetails` 接口，封装用户信息与权限 |
| `UserDetailsManagerImpl` | 自定义用户管理实现类，实现用户的注册、查询、更新等 |
| `UserController` | 提供用户接口，如 `/user/save` 注册、`/logout` 退出登录 |
| `MyAuthenticationHandler` | 未登录或身份失效处理器（实现 `AuthenticationEntryPoint`） |
| `MyAccessDeniedHandler` | 无权限访问处理器（实现 `AccessDeniedHandler`） |
| `JwtUtil` | JWT 工具类，负责生成与解析 token |

---

## 🗃️ 数据库结构简述

| 表名 | 描述 |
|------|------|
| `sys_user` | 用户表，字段有 id、username、password、email、status 等 |
| `sys_role` | 角色表，字段有 id、role_name、description 等 |
| `sys_permission` | 权限表，字段有 id、perm_name、description 等 |
| `sys_user_role` | 用户-角色关联表（多对多） |
| `sys_role_permission` | 角色-权限关联表（多对多） |

> 本项目使用 MyBatis + XML 映射，Mapper 层负责数据库操作。

---

## 📌 项目特点

- 完整的 RBAC 权限模型实现
- 登录后生成 JWT 并缓存用户详情到 Redis
- Spring Security 自定义扩展组件（认证管理器、处理器、过滤器）
- 代码结构清晰，适合学习权限控制与安全框架集成

---

## 📂 项目结构概览

security-wdg/
├── config/ # Security 配置类
├── controller/ # 控制器层
├── domain/ # 实体类、DTO、VO
├── filter/ # 自定义 JWT 过滤器
├── handler/ # 自定义认证/授权异常处理器
├── mapper/ # MyBatis Mapper 接口
├── service/ # 业务逻辑接口与实现
├── util/ # 工具类（如 JwtUtil）
├── resources/
│ ├── mapper/ # MyBatis XML 映射文件
│ └── application.yml # 配置文件
