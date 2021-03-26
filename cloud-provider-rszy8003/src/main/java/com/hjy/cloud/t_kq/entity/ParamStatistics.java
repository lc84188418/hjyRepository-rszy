package com.hjy.cloud.t_kq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_kq.entity
 * @date 2021/3/25 18:00
 * description:
 */
@Data
@ApiModel("打卡统计传参类")
public class ParamStatistics {
    @ApiModelProperty(value = "统计按周或月",example = "周",required = true)
    private String weekOrMonth;
    @ApiModelProperty(value = "完整周时间段",example = "2021.03.21-2021.03.27")
    private String weekDate;
    @ApiModelProperty(hidden = true)
    private String startDate;
    @ApiModelProperty(hidden = true)
    private String endDate;
    @ApiModelProperty(value = "月份",example = "2021-03")
    private String monthDate;
    @ApiModelProperty(hidden = true)
    private String fkStaffId;
    @ApiModelProperty(value = "0:本周、1:下周、-1:上周时间段")
    private int lastOrNext;
    @ApiModelProperty(hidden = true)
    private String isWorkingHours;
    @ApiModelProperty(value = "日期",example = "2021-03-24")
    private String todayDate;
}
