package com.hjy.cloud.common.entity;

import java.util.Date;

import lombok.Data;

/**
 * (DSalaryRecord)表实体类
 *
 * @author makejava
 * @since 2021-03-15 12:12:03
 */
@Data
public class DSalaryRecord {

    private String pkSalaryrecordId;
    //员工ID
    private String fkStaffId;
    //员工姓名
    private String staffName;
    //基本薪资
    private double jbXz;
    //岗位薪资
    private double gwXz;
    //绩效薪资
    private double jxXz;
    //全勤补贴
    private double qqBt;
    //交通补贴
    private double jtBt;
    //餐宿补贴
    private double csBt;
    //迟到/早退扣款
    private double cdZtKq;
    //缺卡/报扣款
    private double qkBKq;
    //事假/病假扣款
    private double sjBjKq;
    //其他扣款
    private double qtKq;
    //社保扣款
    private double sbKq;
    //公积金扣款
    private double gjjKq;
    //个人所得税扣款
    private double grsdsKq;
    //应得工资
    private double dueSalary;
    //实得工资
    private double takeHomePay;
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
