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
    //考勤组名称
    @ApiModelProperty("考勤组名称")
    private String groupName;
    //考勤组负责人
    @ApiModelProperty("考勤组负责人")
    private String groupStewards;
    //考勤类型，1固定班制2排班制3自由工时
    @ApiModelProperty("考勤类型")
    private Integer kqType;
    //法定节假日自动排休
    @ApiModelProperty("法定节假日自动排休")
    private Integer isPaixiu;
    //必须打卡日期
    @ApiModelProperty("必须打卡日期")
    private Date bxdkTime;
    //无需打开日期
    @ApiModelProperty("无需打开日期")
    private Date wxdkTime;
    //考勤方式，1地点打卡，2WiFi打开
    @ApiModelProperty("考勤方式")
    private Integer kqWay;
    //是否允许外勤打卡，1代表允许，0代表不允许
    @ApiModelProperty("是否允许外勤打卡")
    private Integer isWq;
    //是否启用,1启用，0禁用
    @ApiModelProperty("是否启用")
    private Integer turnOn;
    //-----------
    private String stewardsName;
}
