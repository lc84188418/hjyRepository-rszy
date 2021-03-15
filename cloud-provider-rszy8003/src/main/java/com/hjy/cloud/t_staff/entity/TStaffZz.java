package com.hjy.cloud.t_staff.entity;

import lombok.Data;

import java.util.Date;

/**
 * (TStaffZz)表实体类
 *
 * @author makejava
 * @since 2021-03-10 15:10:51
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
    //实际转正日期
    private Date sjzzTime;
    //状态
    private Integer status;
    //转正审批状态,0代表正在审批中，1通过
    private Integer apvStatus;
    //第一级审批id
    private String firstApvrecordId;
    /**
     *
     */
    private String staffName;
    private String wordAddressName;
    private String deptName;
    private String positionName;
}
