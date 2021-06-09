package com.hjy.cloud.t_staff.entity;

import lombok.Data;

import java.util.Date;

/**
 * (TStaffQuit)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
@Data
public class TStaffQuit {
    //离职信息主键
    private String pkQuitId;
    //离职人ID
    private String fkStaffId;
    private String staffName;
    //部门ID
    private String fkDeptId;
    private String deptName;
    //职位ID
    private String position;
    private String positionName;
    //申请日期
    private Date applyTime;
    //离职类型
    private String quitType;
    //离职原因
    private String quitReason;
    //离职日期
    private Date quitTime;
    //审批人
    private String apvPeople;
    //状态,0审批中1通过2拒绝
    private Integer quitStatus;
    //备注
    private String remarks;
    //审批时间
    private Date apvTime;
    //离职填写人
    private String operatedPeople;
    //第一级审批id
    private String apvId;
}
