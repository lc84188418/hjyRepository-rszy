package com.hjy.cloud.t_kq.entity;

import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * (TKqClock)表实体类
 *
 * @author makejava
 * @since 2021-03-19 17:26:09
 */
@Data
@ApiModel("打卡记录-实体类")
public class TKqClock {
    //打卡记录主键
    @ApiModelProperty("打卡记录主键")
    private String pkClockId;
    //员工id
    @ApiModelProperty("员工id")
    private String fkStaffId;
    //工号
    @ApiModelProperty("工号")
    private String jobId;
    //当时日期
    @ApiModelProperty("当时日期")
    private Date todayDate;
    //上班打卡时间
    @ApiModelProperty("上班打卡时间")
    private Date onDutyTime;
    //上班打卡地点
    @ApiModelProperty("上班打卡地点")
    private String onCloudAddress;
    //上班打卡IP
    @ApiModelProperty("上班打卡IP")
    private String onCloudIp;
    //上班打卡是否外勤
    @ApiModelProperty("上班打卡是否外勤")
    private String onIsWq;
    //下班打卡时间
    @ApiModelProperty("下班打卡时间")
    private Date offDutyTime;
    //下班班打卡地点
    @ApiModelProperty("下班班打卡地点")
    private String offCloudAddress;
    //下班班打卡IP
    @ApiModelProperty("下班班打卡IP")
    private String offCloudIp;
    //下班打卡是否外勤
    @ApiModelProperty("下班打卡是否外勤")
    private String offIsWq;
    //工时
    @ApiModelProperty("工时")
    private double workingHours;
    //是否迟到
    @ApiModelProperty("是否迟到")
    private Integer isCd;
    @ApiModelProperty("迟到时间")
    private String cdMinute;
    //是否早退
    @ApiModelProperty("是否早退")
    private Integer isZt;
    @ApiModelProperty("早退时间")
    private String ztMinute;
    @ApiModelProperty("是否为打卡日")
    private Integer isDkr;
    //当时所属部门
    @ApiModelProperty("当时所属部门")
    private String fkDeptId;
    //当时所属考勤组
    @ApiModelProperty("当时所属考勤组")
    private String fkGroupId;
    //当时职位
    @ApiModelProperty("当时职位")
    private String fkPositionId;

}
