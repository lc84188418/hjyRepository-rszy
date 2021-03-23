package com.hjy.cloud.t_kq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * (TKqGroup)表实体类
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@Data
@ApiModel("考勤分组实体类")
public class TKqGroup {
    @ApiModelProperty("pkGroupId")
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
    @ApiModelProperty("考勤类型，1固定班制2排班制3自由工时")
    private Integer kqType;
    @ApiModelProperty("类型设置")
    private Integer typeSet;
    @ApiModelProperty("法定节假日自动排休")
    private Integer isPaixiu;
    @ApiModelProperty("必须打卡日期")
    private Date bxdkTime;
    @ApiModelProperty("无需打开日期")
    private Date wxdkTime;
    @ApiModelProperty("考勤方式，1地点打卡，2WiFi打开")
    private Integer kqWay;
    @ApiModelProperty("是否允许外勤打卡，1代表允许，0代表不允许")
    private Integer isWq;
    @ApiModelProperty("是否启用,1启用，0禁用")
    private Integer turnOn;
    //-----------
    private String stewardsName;
}
