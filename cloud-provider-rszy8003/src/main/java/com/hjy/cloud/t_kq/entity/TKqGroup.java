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
@ApiModel("-实体类")
public class TKqGroup {
    private String pkGroupId;
    //考勤组名称
    @ApiModelProperty("考勤组名称")
    private String groupName;
    //同部门后续同事自动加入该考勤组
    @ApiModelProperty("同部门后续同事自动加入该考勤组")
    private Integer autoJoin;
    //考勤地址
    @ApiModelProperty("考勤地址")
    private String kqAddress;
    //考勤范围
    @ApiModelProperty("考勤范围")
    private Integer kqRange;
    //考勤组负责人
    @ApiModelProperty("考勤组负责人")
    private String groupStewards;
    //考勤类型：1固定班制2排班制3自由工时
    @ApiModelProperty("考勤类型：1固定班制2排班制3自由工时")
    private Integer kqType;
    //未排班时，员工可选择班次打卡
    @ApiModelProperty("排班制：未排班时，员工可选择班次打卡")
    private Integer isKxbcdk;
    //未排班时，员工可打卡
    @ApiModelProperty("排班制：未排班时，员工可打卡")
    private Integer isKdk;
    //类型设置
    @ApiModelProperty("自由工时类型设置")
    private Integer typeSet;
    //法定节假日自动排休
    @ApiModelProperty("法定节假日自动排休")
    private Integer isPaixiu;
    //必须打卡日期
    @ApiModelProperty("必须打卡日期")
    private Date bxdkTime;
    //无需打开日期
    @ApiModelProperty("无需打开日期")
    private Date wxdkTime;
    //考勤方式，1地点打卡，2WiFi打卡
    @ApiModelProperty("考勤方式，1地点打卡，2WiFi打卡")
    private Integer kqWay;
    //拍照打卡
    @ApiModelProperty("拍照打卡")
    private Integer isPzdk;
    //是否允许外勤打卡，1代表允许，0代表不允许
    @ApiModelProperty("是否允许外勤打卡，1代表允许，0代表不允许")
    private Integer isWq;
    //外勤打卡需审批
    @ApiModelProperty("外勤打卡需审批")
    private Integer wqApv;
    //外勤打卡需填写备注
    @ApiModelProperty("外勤打卡需填写备注")
    private Integer wqRemarks;
    //外勤打卡需拍照
    @ApiModelProperty("外勤打卡需拍照")
    private Integer wqPz;
    //允许员工隐藏详细地址
    @ApiModelProperty("允许员工隐藏详细地址")
    private Integer wqHideaddress;
    //上班打卡后,几小时后才能打下班卡
    @ApiModelProperty("上班打卡后,几小时后才能打下班卡")
    private Integer dkJgsj;
    //是否启用
    @ApiModelProperty("是否启用")
    private Integer turnOn;

}
