package com.hjy.cloud.t_apv.entity;

import lombok.Data;

/**
 * (TApvApproval)表实体类
 *
 * @author makejava
 * @since 2021-02-26 14:50:46
 */
@Data
public class TApvApproval implements Comparable<TApvApproval>{

    private String pkApprovalId;
    //审批类别
    private String approvalType;
    //审批人ID
    private String approvalPeople;
    //下级审批
    private String nextApproval;
    //数据保存类型，1为通用，0为临时
    private Integer dataType;
    //审批人岗位
    private String apvStation;
    //是否为结尾审批
    private int isEnding;
    //是否为第一级审批
    private int isStart;
    //---------
    //审批人名字
    private String approvalPeopleName;
    //岗位名字
    private String stationName;
    @Override
    public int compareTo(TApvApproval tApvApproval) {
        return String.valueOf(this.getIsStart()).compareTo(String.valueOf(tApvApproval.getIsStart()));
    }
}
