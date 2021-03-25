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
    @ApiModelProperty("打卡记录主键")
    private String pkClockId;
    @ApiModelProperty("员工id")
    private String fkStaffId;
    @ApiModelProperty("员工姓名")
    private String staffName;
    @ApiModelProperty("工号")
    private String jobId;
    @ApiModelProperty("当时日期")
    private Date todayDate;
    @ApiModelProperty("上班打卡时间")
    private Date onDutyTime;
    @ApiModelProperty("上班打卡地点,上班时传入")
    private String onClockAddress;
    @ApiModelProperty("上班打卡IP")
    private String onClockIp;
    @ApiModelProperty("上班打卡是否外勤,上班时传入")
    private Integer onIsWq;
    @ApiModelProperty("下班打卡时间")
    private Date offDutyTime;
    @ApiModelProperty("下班班打卡地点")
    private String offClockAddress;
    @ApiModelProperty("下班班打卡IP")
    private String offClockIp;
    @ApiModelProperty("下班打卡是否外勤")
    private Integer offIsWq;
    @ApiModelProperty("工时")
    private double workingHours;
    @ApiModelProperty("是否迟到")
    private Integer isCd;
    @ApiModelProperty("迟到时间")
    private String cdMinute;
    @ApiModelProperty("迟到描述")
    private String cdDesc;
    @ApiModelProperty("是否早退")
    private Integer isZt;
    @ApiModelProperty("早退时间")
    private String ztMinute;
    @ApiModelProperty("早退描述")
    private String ztDesc;
    @ApiModelProperty("是否加班")
    private Integer isJb;
    @ApiModelProperty("加班时间")
    private String jbMinute;
    @ApiModelProperty("加班描述")
    private String jbDesc;
    @ApiModelProperty("是否为打卡日,上班时传入")
    private Integer isDkr;
    @ApiModelProperty("当时所属部门id")
    private String fkDeptId;
    @ApiModelProperty("当时所属部门")
    private String deptName;
    @ApiModelProperty("当时所属考勤组id,上班时传入")
    private String fkGroupId;
    @ApiModelProperty("当时所属考勤组名称")
    private String groupName;
    @ApiModelProperty("当时职位id")
    private String fkPositionId;
    @ApiModelProperty("当时职位名称")
    private String positionName;
}
