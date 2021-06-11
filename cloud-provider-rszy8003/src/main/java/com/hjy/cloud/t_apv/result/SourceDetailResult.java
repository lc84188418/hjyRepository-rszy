package com.hjy.cloud.t_apv.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.result
 * @date 2021/6/9 18:08
 * description:
 */
@Data
@ApiModel("审批来源详情")
public class SourceDetailResult {
    private String sourceId;
    @ApiModelProperty(value = "员工ID", example = "", required = false)
    private String fkStaffId;
    @ApiModelProperty(value = "姓名", example = "", required = false)
    private String staffName;
    @ApiModelProperty(value = "部门", example = "", required = false)
    private String staffDept;
    @ApiModelProperty(value = "职位", example = "", required = false)
    private String staffPosition;
    /**
     * 以下信息为调动的，，若没有，说明该审批流程的审批来源不是为调动审批
     */
    @ApiModelProperty(value = "调动时间", example = "", required = false)
    private Date reassignTime;
    @ApiModelProperty(value = "调动类型", example = "", required = false)
    private String reassignType;
    @ApiModelProperty(value = "调动原因", example = "", required = false)
    private String reassignReason;
    @ApiModelProperty(value = "原部门", example = "", required = false)
    private String oldDeptName;
    @ApiModelProperty(value = "调动后部门", example = "", required = false)
    private String reassignDeptName;
    @ApiModelProperty(value = "原职位", example = "", required = false)
    private String oldPositionName;
    @ApiModelProperty(value = "调动后职位", example = "", required = false)
    private String reassignPositionName;
    @ApiModelProperty(value = "原工作地", example = "", required = false)
    private String oldWorkAddressName;
    @ApiModelProperty(value = "调动后工作地", example = "", required = false)
    private String reassignWorkAddressName;
    /**
     * 以下信息为离职，若没有，说明该审批流程的审批来源不是为离职审批
     */
    @ApiModelProperty(value = "离职类型", example = "", required = false)
    private String quitType;
    @ApiModelProperty(value = "离职原因", example = "", required = false)
    private String quitReason;
    @ApiModelProperty(value = "离职日期", example = "", required = false)
    private Date quitTime;
    /**
     * 以下信息为入职，若没有，说明该审批流程的审批来源不是为入职审批
     */
    @ApiModelProperty(value = "入职日期", example = "", required = false)
    private Date entryTime;
    /**
     * 以下信息为转正，若没有，说明该审批流程的审批来源不是为转正审批
     */
    @ApiModelProperty(value = "转正日期", example = "", required = false)
    private Date zzTime;
    //
    @ApiModelProperty(value = "第一级审批id", example = "", hidden = true)
    private String apvId;
}
