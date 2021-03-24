package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffEntry)表实体类
 *
 * @author makejava
 * @since 2021-02-26 10:55:26
 */
@Data
public class TStaffEntry {
    //入职信息主键
    private String pkEntryId;
    //姓名,必填
    private String staffName;
    //性别
    private Integer staffSex;
    //年龄
    private Integer staffAge;
    //部门
    private String staffDept;
    //职位
    private String staffPosition;
    //电话
    private String staffTel;
    //合同类型
    private String fkHtlxId;
    //工作地点
    private String workAddress;
    //招聘方式
    private String recruitWay;
    //证件类型
    private String idType;
    //证件号
    private String idCard;
    //入职日期-页面上添加,必填
    private Date entryTime;
    //个人邮箱
    private String email;
    //操作人
    private String operatedPeople;
    //状态,0代表刚添加完成入职信息，2代表已发起入职审批，正在审批中，1审批完成
    private Integer status;
    //备用
    private String remarks;
    //是否弃职
    private Integer isAbandon;
    //弃职时间
    private Date abandonTime;
    //弃职原因
    private String abandonReason;
    //入职说明
    private String entryDesc;
    //第一级审批ID
    private String apvId;

}
