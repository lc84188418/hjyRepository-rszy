package com.hjy.cloud.common.entity;

import java.util.Date;

import lombok.Data;

/**
 * (DSalaryRecord)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:46:29
 */
@Data
public class DSalaryRecord {

    private String pkSalaryId;
    //员工ID
    private String fkStaffId;
    //基本薪资
    private float jbXz;
    //岗位薪资
    private float gwXz;
    //绩效薪资
    private float jxXz;
    //全勤补贴
    private float qqBt;
    //交通补贴
    private float jtBt;
    //餐宿补贴
    private float csBt;
    //迟到/早退扣款
    private float cdZtKq;
    //缺卡/报扣款
    private float qkBKq;
    //事假/病假扣款
    private float sjBjKq;
    //其他扣款
    private float qtKq;
    //社保扣款
    private float sbKq;
    //公积金扣款
    private float gjjKq;
    //个人所得税扣款
    private float grsdsKq;
    //发送状态
    private Integer sendStatus;
    //查看状态
    private Integer checkStatus;
    //确认状态
    private Integer confirmStatus;
    //操作人，也就是发送薪资的人
    private String oepratePeople;
    //发送时间
    private Date sendTime;

}
