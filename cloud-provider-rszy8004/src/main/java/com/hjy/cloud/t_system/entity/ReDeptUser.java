package com.hjy.cloud.t_system.entity;

import lombok.Data;

@Data
public class ReDeptUser {
    private String pk_deptUser_id;
    private String fk_dept_id;
    private String fk_user_id;
    private String fk_company_id;
}
