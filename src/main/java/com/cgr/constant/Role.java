package com.cgr.constant;

public enum Role {
    ROLE_ADMIN(1,"ADMIN"),
    ROLE_USER(2,"USER"),
    ROLE_MANAGER(3,"MANAGER");

    Integer id;
    String roleName;

    Role(Integer id,String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    /** 返回该角色对应的数据库 ID */
    public Integer getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }


}
