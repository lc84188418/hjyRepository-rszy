package com.hjy.cloud.t_kq.entity;

import lombok.Data;

import java.util.Date;

/**
 * (TKqGroup)表实体类
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@Data
public class TKqGroup {

    private String pkGroupId;
    //考勤组名称
    private String groupName;
    //考勤组负责人
    private String groupStewards;
    //1固定班制2排班制3自由工时
    private Integer kqType;
    //法定节假日自动排休
    private Integer isPaixiu;
    //必须打卡日期
    private Date bxdkTime;
    //无需打开日期
    private Date wxdkTime;
    //考勤方式，1地点打卡，2WiFi打开
    private Integer kqWay;
    //是否允许外勤打卡，1代表允许，0代表不允许
    private Integer isWq;
    //是否启用
    private Integer turnOn;
}
