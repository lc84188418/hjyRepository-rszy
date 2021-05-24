package com.hjy.cloud.t_kq.entity;

import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_kq.entity
 * @date 2021/5/24 16:02
 * description:
 */
@Data
@ApiModel("打卡前页面返回实体")
public class ClockAddPage {
    @ApiModelProperty("打卡记录")
    private TKqClock clock;
    @ApiModelProperty("考勤组")
    private TKqGroup group;
    @ApiModelProperty("工作地")
    private TOutfitWorkaddress workAddress;
    @ApiModelProperty("班次")
    private TKqBc tKqBc;
    @ApiModelProperty(value = "所处时间段，1代表当前时间在上下班时间段之前",example = "1")
    private int isTimeSlot;
}
