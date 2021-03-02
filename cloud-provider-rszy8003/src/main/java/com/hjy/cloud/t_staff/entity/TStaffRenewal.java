package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffRenewal)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
@Data
public class TStaffRenewal {
    //续签合同主键
    private String pkRenewalId;
    //员工ID
    private String fkStaffId;
    //续签合同类型ID,在字典中
    private String fkContractType;
    //续签合同开始时间
    private Date renewalStartTime;
    //续签合同结束时间
    private Date renewalEndTime;
    //续签时间
    private Date renewalTime;
    //发起时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //审批状态
    private Integer approvalStatus;
    //附件
    private String annex;

}
