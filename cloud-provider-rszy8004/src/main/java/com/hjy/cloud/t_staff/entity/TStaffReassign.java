package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffReassign)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@Data
public class TStaffReassign {
    //调动详情主键
    private String pkReassignId;
    //员工ID
    private String fkStaffId;
    //发起时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //调动时间
    private Date reassignTime;
    //调动类型
    private String reassignType;
    //原部门
    private String oldDeptId;
    //调动后部门
    private String reassignDeptId;
    //原职位
    private String oldPosition;
    //调动后职位
    private String reassignPosition;
    //原工作地
    private String oldAddress;
    //调动后工作地
    private String reassignAddress;
    //原合同公司
    private String oldCompany;
    //调动后合同公司
    private String reassignCompany;
    //原直属领导
    private String oldLeader;
    //调动后直属领导
    private String reassignLeader;
    //调动原因
    private String reassignReason;
    //审批状态,0审批中，1审批通过，2审批被拒绝
    private Integer apvStatus;
    //第一级审批id
    private String firstApvrecordId;
    //-----------
    private String staffName;
    private String oldDeptName;
    private String reassignDeptName;
    private String oldPositionName;
    private String reassignPositionName;
    private String oldWorkAddressName;
    private String reassignWorkAddressName;
    private String oldCompanyName;
    private String reassignCompanyName;
    private String oldLeaderName;
    private String reassignLeaderName;

}
