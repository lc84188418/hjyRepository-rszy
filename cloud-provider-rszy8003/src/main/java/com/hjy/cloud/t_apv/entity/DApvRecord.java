package com.hjy.cloud.t_apv.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * (DApvRecord)表实体类
 *
 * @author makejava
 * @since 2021-02-26 17:55:25
 */
@Data
@ApiModel(value = "审批记录")
public class DApvRecord<T> {
    @ApiModelProperty(value = "审批记录主键", example = "", required = false)
    private String pkRecordId;
    @ApiModelProperty(value = "审批类别", example = "", required = false)
    private String approvalType;
    @ApiModelProperty(value = "申请人id", example = "", required = false)
    private String applyPeopleId;
    @ApiModelProperty(value = "申请人", example = "", required = false)
    private String applyPeople;
    @ApiModelProperty(value = "发起人id", example = "", required = false)
    private String sponsorId;
    @ApiModelProperty(value = "发起人", example = "", required = false)
    private String sponsor;
    @ApiModelProperty(value = "发起时间", example = "", required = false)
    private Date startTime;
    @ApiModelProperty(value = "审批人", example = "", required = false)
    private String apvApproval;
    @ApiModelProperty(value = "审批人姓名", example = "", required = false)
    private String approvalPeopleName;
    @ApiModelProperty(value = "审批时间", example = "", required = false)
    private Date apvDate;
    @ApiModelProperty(value = "审批结果", example = "", required = false)
    private Integer apvResult;
    @ApiModelProperty(value = "审批理由", example = "", required = false)
    private String apvReason;
    @ApiModelProperty(value = "下级审批", example = "", required = false)
    private String nextApproval;
    @ApiModelProperty(value = "审批来源ID", example = "", required = false)
    private String sourceId;
    @ApiModelProperty(value = "是否为一级审批", example = "", required = false)
    private Integer isStart;
    @ApiModelProperty(value = "是否处于正在处理的审批流程中", example = "", required = false)
    private Integer isIng;
    @ApiModelProperty(value = "整个流程的审批状态,0审批中1通过2拒绝", example = "", required = false)
    private Integer apvStatus;
    //------------
    @ApiModelProperty(value = "审批类别名称", example = "", required = false)
    private String apvtypeName;
    @ApiModelProperty(value = "岗位名称", example = "", required = false)
    private String positionName;
    @ApiModelProperty(value = "标识", example = "", required = false)
    private Integer prompt;
    @ApiModelProperty(value = "审批类别", example = "", required = false)
    private DApvRecord nextRecord;
    @ApiModelProperty(value = "审批来源详情", example = "", required = false)
    private T sourceDetail;

}
