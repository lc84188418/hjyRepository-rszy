package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffZz)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:18
 */
@Data
public class TStaffZz {
    //转正信息主键
    private String pkZzId;
    //员工ID
    private String fkStaffId;
    //工作地点
    private String fkWordaddressId;
    //入职日期
    private Date entryTime;
    //试用期到期日
    private Date syqdqTime;
    //转正日期
    private Date zzTime;
    //转正审批状态
    private Integer apvStatus;
    //状态
    private Integer status;

}
