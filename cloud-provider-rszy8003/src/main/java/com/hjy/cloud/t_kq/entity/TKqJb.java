package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (TKqJb)表实体类
 *
 * @author makejava
 * @since 2021-03-16 14:51:00
 */
@Data
public class TKqJb {

    private String pkJbId;
    //加班规则名称
    private String jbName;
    //风险预警计算频率,每月，每周，每天
    private String fxyjFrequency;
    //风险预警加班时间
    private Integer fxyjHour;
    //计算方式，暂时不用
    private Integer jsWay;
    //加班单位，分钟，半小时，小时，半天，天
    private String jbUnit;
    //是否记为调休或加班。1代表记为调休或加班费，0代表不计入
    private Integer isTxJbf;
    //调休规则
    private String txRule;
    //加班费规则
    private String jbfRule;
    //是否启用
    private Integer turnOn;
    //-----------
    private String jbGroupId;
    private String jbGroupName;

}
