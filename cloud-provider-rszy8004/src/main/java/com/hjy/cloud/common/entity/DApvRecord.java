package com.hjy.cloud.common.entity;

import java.util.Date;

import lombok.Data;

/**
 * (DApvRecord)表实体类
 *
 * @author makejava
 * @since 2021-02-26 17:55:25
 */
@Data
public class DApvRecord {
    //审批记录主键
    private String pkRecordId;
    //审批类别
    private String approvalType;
    //申请人
    private String applyPeople;
    //发起人
    private String sponsor;
    //发起时间
    private Date startTime;
    //审批人
    private String apvApproval;
    //审批时间
    private Date apvDate;
    //审批结果
    private Integer apvResult;
    //审批理由
    private String apvReason;
    //下级审批
    private String nextApproval;
    //审批来源ID
    private String sourceId;
    //是否为一级审批
    private int isStart;
    //是否处于正在处理的审批流程中
    private int isIng;
    //------------
    private String apvtypeName;
    private String approvalPeopleName;
    private String positionName;
}
