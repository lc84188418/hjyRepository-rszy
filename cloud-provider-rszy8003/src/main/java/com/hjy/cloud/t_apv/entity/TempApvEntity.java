package com.hjy.cloud.t_apv.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.entity
 * @date 2021/3/3 17:52
 * description:
 */
@Data
@ApiModel("审批流程详情")
public class TempApvEntity {
    @ApiModelProperty(value = "审批主键", example = "", required = false)
    private String pkApprovalId;
    @ApiModelProperty(value = "审批类型id", example = "", required = false)
    private String approvalType;
    @ApiModelProperty(value = "审批类型名称", example = "", required = false)
    private String apvtypeName;
    @ApiModelProperty(value = "审批类型", example = "", required = false)
    private String applyPeople;
    @ApiModelProperty(value = "发起人", example = "", required = false)
    private String sponsor;
    @ApiModelProperty(value = "开始时间", example = "", required = false)
    private Date startTime;
    @ApiModelProperty(value = "第一级审批人", example = "", required = false)
    private String approvalPeople1;
    @ApiModelProperty(value = "第一级审批时间", example = "", required = false)
    private Date apvDate1;
    @ApiModelProperty(value = "第一级审批结果", example = "", required = false)
    private Integer apvResult1;
    @ApiModelProperty(value = "第一级审批原因", example = "", required = false)
    private String apvReason1;
    private String approvalPeople2;
    private Date apvDate2;
    private Integer apvResult2;
    private String apvReason2;
    private String approvalPeople3;
    private Date apvDate3;
    private Integer apvResult3;
    private String apvReason3;
    private String approvalPeople4;
    private Date apvDate4;
    private Integer apvResult4;
    private String apvReason4;
    private String approvalPeople5;
    private Date apvDate5;
    private Integer apvResult5;
    private String apvReason5;
    @ApiModelProperty(value = "抄送人", example = "", required = false)
    private String csr1;
    private String csr2;
    private String csr3;
    @ApiModelProperty(value = "来源id", example = "", required = false)
    private String sourceId;
    @ApiModelProperty(value = "标识", example = "", required = false)
    private Integer prompt;
    @ApiModelProperty(value = "审批状态", example = "", required = false)
    private Integer apvStatus;
}
