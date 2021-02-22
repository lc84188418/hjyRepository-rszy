package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReRolePerms {
    private String pk_rolePerms_id;
    private String fk_role_id;
    private String fk_perms_id;
    private Date expire_time;

}
