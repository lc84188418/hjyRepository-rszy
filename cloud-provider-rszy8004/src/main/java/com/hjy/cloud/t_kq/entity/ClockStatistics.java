package com.hjy.cloud.t_kq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_kq.entity
 * @date 2021/5/24 17:15
 * description:
 */
@Data
@ApiModel("个人打卡统计")
public class ClockStatistics {
    @ApiModelProperty("个人打卡总统计")
    private StatisticsUser allStatistics;
    @ApiModelProperty("个人打卡每日记录统计")
    private List<TKqClock> statistics;
    @ApiModelProperty("个人打卡今日打卡记录")
    private TKqClock today;
}
