package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (TKqBc)表实体类
 *
 * @author makejava
 * @since 2021-03-16 14:50:55
 */
@Data
public class TKqBc {

    private String pkBcId;
    //1代表一天一次上下班，2代表一天两次上下班，3代表一天三次上下班
    private Integer dkNum;
    //上下班时间段,9:00-18:00如果一天两次上下班，用+连接
    private String timeSlot;
    //是否休息
    private Integer isRest;
    //休息时间段
    private String restSlot;
    //弹性打卡，1代表晚到晚走早到早走
    private Integer txdk;
    //班次负责人
    private String bcStewards;
    //是否启用
    private Integer turnOn;
    //是否默认规则
    private Integer isDefault;
    //------------
    private String stewardsName;
}
