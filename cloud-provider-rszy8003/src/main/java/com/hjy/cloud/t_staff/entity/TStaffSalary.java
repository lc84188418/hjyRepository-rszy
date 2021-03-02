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
    //基本薪资
    private Object jbXz;
    //岗位薪资
    private Object gwXz;
    //绩效薪资
    private Object jxXz;
    //全勤补贴
    private Object qqBt;
    //交通补贴
    private Object jtBt;
    //餐宿补贴
    private Object csBt;
    //迟到/早退扣款
    private Object cdZtKq;
    //缺卡/报扣款
    private Object qkBKq;
    //事假/病假扣款
    private Object sjBjKq;
    //其他扣款
    private Object qtKq;
    //社保扣款
    private Object sbKq;
    //公积金扣款
    private Object gjjKq;
    //个人所得税扣款
    private Object grsdsKq;

}
