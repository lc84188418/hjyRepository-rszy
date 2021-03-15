package com.hjy.cloud.t_staff.entity;

import lombok.Data;

/**
 * (TStaffSalary)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
@Data
public class TStaffSalary {

    private String pkSalaryId;
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
    //--------
    private int isSend;
    //应得工资
    private double dueSalary;
    //实得工资
    private double takeHomePay;

}
