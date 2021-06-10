package com.hjy.cloud.t_kq.entity;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * (TKqGroup)表实体类
 *
 * @author makejava
 * @since 2021-03-23 15:22:25
 */
@Data
@ApiModel("考勤组")
public class TKqGroup {
    @ApiModelProperty("考勤组主键id")
    private String pkGroupId;
    @ApiModelProperty("考勤组名称")
    private String groupName;
    @ApiModelProperty("同部门后续同事自动加入该考勤组")
    private Integer autoJoin;
    @ApiModelProperty("考勤地址")
    private String kqAddress;
    @ApiModelProperty("考勤范围")
    private Integer kqRange;
    @ApiModelProperty("考勤组负责人")
    private String groupStewards;
    @ApiModelProperty("考勤类型：1固定班制\n2排班制:仅支持一个默认班次\n3自由工时")
    private Integer kqType;
    @ApiModelProperty("排班制：未排班时，员工可选择班次打卡")
    private Integer isKxbcdk;
    @ApiModelProperty("排班制：未排班时，员工可打卡")
    private Integer isKdk;
    @ApiModelProperty("自由工时类型设置")
    private String typeSet;
    @ApiModelProperty("法定节假日自动排休")
    private Integer isPaixiu;
    @ApiModelProperty("必须打卡日期")
    private Date bxdkTime;
    @ApiModelProperty("无需打开日期")
    private Date wxdkTime;
    @ApiModelProperty("考勤方式，1地点打卡，2WiFi打卡")
    private Integer kqWay;
    @ApiModelProperty("拍照打卡")
    private Integer isPzdk;
    @ApiModelProperty("是否允许外勤打卡，1代表允许，0代表不允许")
    private Integer isWq;
    @ApiModelProperty("外勤打卡需审批")
    private Integer wqApv;
    @ApiModelProperty("外勤打卡需填写备注")
    private Integer wqRemarks;
    @ApiModelProperty("外勤打卡需拍照")
    private Integer wqPz;
    @ApiModelProperty("允许员工隐藏详细地址")
    private Integer wqHideaddress;
    @ApiModelProperty("上班打卡后,几小时后才能打下班卡")
    private Integer dkJgsj;
    @ApiModelProperty("是否启用")
    private Integer turnOn;
    @ApiModelProperty("是否默认")
    private Integer isDefault;

}
