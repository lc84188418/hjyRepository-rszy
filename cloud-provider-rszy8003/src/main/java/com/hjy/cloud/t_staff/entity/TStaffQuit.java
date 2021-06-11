package com.hjy.cloud.t_staff.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * (TStaffQuit)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
@Data
@ApiModel("离职")
public class TStaffQuit {
    @ApiModelProperty(value = "离职信息主键", example = "", required = false)
    private String pkQuitId;
    @ApiModelProperty(value = "离职人ID", example = "", required = false)
    private String fkStaffId;
    @ApiModelProperty(value = "离职人姓名", example = "", required = false)
    private String staffName;
    @ApiModelProperty(value = "部门ID", example = "", required = false)
    private String fkDeptId;
    private String deptName;
    @ApiModelProperty(value = "职位ID", example = "", required = false)
    private String position;
    private String positionName;

    @ApiModelProperty(value = "申请日期", example = "", required = false)
    private Date applyTime;
    @ApiModelProperty(value = "离职类型", example = "", required = false)
    private String quitType;
    @ApiModelProperty(value = "离职原因", example = "", required = false)
    private String quitReason;
    @ApiModelProperty(value = "离职日期", example = "", required = false)
    private Date quitTime;
    @ApiModelProperty(value = "状态,0审批中1通过2拒绝", example = "", required = false)
    private Integer quitStatus;
    @ApiModelProperty(value = "备注", example = "", required = false)
    private String remarks;
    @ApiModelProperty(value = "审批时间", example = "", required = false)
    private Date apvTime;
    @ApiModelProperty(value = "离职填写人", example = "", required = false)
    private String operatedPeople;
    @ApiModelProperty(value = "第一级审批id", example = "", required = false)
    private String apvId;

}
