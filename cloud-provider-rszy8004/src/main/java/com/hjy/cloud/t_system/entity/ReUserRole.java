package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReUserRole {
    private String pk_userRole_id;
    private String fk_user_id;
    private String fk_role_id;
    private Date expire_time;

}
