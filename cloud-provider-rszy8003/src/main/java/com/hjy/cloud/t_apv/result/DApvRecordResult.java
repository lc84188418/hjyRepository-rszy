package com.hjy.cloud.t_apv.result;

import lombok.Data;

import java.util.Date;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.result
 * @date 2021/6/4 10:38
 * description: 返回的审批流程结果最好写成这个，应前端方便全写一堆，放在TempApvEntity里
 */
@Data
public class DApvRecordResult {
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
    private DApvRecordResult nextApv;
}
