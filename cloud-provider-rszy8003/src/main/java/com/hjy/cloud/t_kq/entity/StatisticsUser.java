package com.hjy.cloud.t_kq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_kq.entity
 * @date 2021/3/25 14:58
 * description:
 */
@Data
@ApiModel("打卡记录-实体类")
public class StatisticsUser {
    @ApiModelProperty("员工id")
    private String fkStaffId;
    @ApiModelProperty("员工姓名")
    private String staffName;
    @ApiModelProperty("工号")
    private String jobId;
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
    @ApiModelProperty("平均工时")
    private double workingHours;
    @ApiModelProperty("出勤天数")
    private double onDutyNum;
    @ApiModelProperty("出勤班次")
    private double onDutyBc;
    @ApiModelProperty("休息天数")
    private double restNum;
    @ApiModelProperty("迟到")
    private String cdDesc;
    @ApiModelProperty("早退")
    private String ztDesc;
    @ApiModelProperty("缺卡")
    private double lackClockNum;
    @ApiModelProperty("旷工")
    private double absenteeismNum;
    @ApiModelProperty("外勤")
    private double fieldNum;
    @ApiModelProperty("加班")
    private String jbDesc;
}
