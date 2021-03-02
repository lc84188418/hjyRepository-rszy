package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffExperienceGz)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
@Data
public class TStaffExperienceGz {
    //工作经历信息主键
    private String pkExperienceId;
    //公司名称
    private String companyName;
    //担任职务
    private String position;
    //入职时间
    private Date entryTime;
    //离职时间
    private Date quitTime;
    //职位描述
    private String positionDesc;
    //关联员工ID
    private String fkStaffId;

}
