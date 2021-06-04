package com.hjy.cloud.t_staff.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * (TStaffEntry)表实体类
 *
 * @author makejava
 * @since 2021-06-03 15:17:55
 */
@Data
@ApiModel("入职信息-实体类")
public class TStaffEntry {
    @ApiModelProperty(value = "入职信息主键", example = "", required = false)
    private String pkEntryId;
    @ApiModelProperty(value = "姓名", example = "", required = false)
    private String staffName;
    @ApiModelProperty(value = "性别", example = "", required = false)
    private Integer staffSex;
    @ApiModelProperty(value = "年龄", example = "", required = false)
    private Integer staffAge;
    @ApiModelProperty(value = "部门", example = "", required = false)
    private String staffDept;
    @ApiModelProperty(value = "职位", example = "", required = false)
    private String staffPosition;
    @ApiModelProperty(value = "电话", example = "", required = false)
    private String staffTel;
    @ApiModelProperty(value = "合同类型", example = "", required = true)
    private String fkHtlxId;
    @ApiModelProperty(value = "工作地点", example = "", required = false)
    private String workAddress;
    @ApiModelProperty(value = "招聘方式", example = "", required = false)
    private String recruitWay;
    @ApiModelProperty(value = "证件类型", example = "", required = false)
    private String idType;
    @ApiModelProperty(value = "证件号", example = "", required = false)
    private String idCard;
    @ApiModelProperty(value = "入职日期", example = "", required = false)
    private Date entryTime;
    @ApiModelProperty(value = "个人邮箱", example = "", required = false)
    private String email;
    @ApiModelProperty(value = "操作人", example = "", required = false)
    private String operatedPeople;
    @ApiModelProperty(value = "状态,0代表刚添加完成入职信息，2代表已发起入职审批，正在审批中，1代表审批完成", example = "", required = false)
    private Integer status;
    @ApiModelProperty(value = "备注", example = "", required = false)
    private String remarks;
    @ApiModelProperty(value = "是否弃职", example = "", required = false)
    private Integer isAbandon;
    @ApiModelProperty(value = "弃职时间", example = "", required = false)
    private Date abandonTime;
    @ApiModelProperty(value = "弃职原因", example = "", required = false)
    private String abandonReason;
    @ApiModelProperty(value = "入职说明", example = "", required = false)
    private String entryDesc;
    @ApiModelProperty(value = "第一级审批ID", example = "", required = false)
    private String apvId;

}
