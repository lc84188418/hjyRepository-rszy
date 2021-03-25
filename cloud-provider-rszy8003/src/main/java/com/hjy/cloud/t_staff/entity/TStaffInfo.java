package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffInfo)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:06:45
 */
@Data
public class TStaffInfo {

    private String pkStaffId;
    //姓名
    private String staffName;
    //性别
    private Integer staffSex;
    //年龄
    private Integer staffAge;
    //员工状态,1代表在职，0代表离职
    private Integer staffStatus;
    //部门
    private String fkDeptId;
    private String deptName;
    //职位
    private String fkPositionId;
    private String positionName;
    //工作地
    private String fkAddressId;
    private String workAddressName;
    //入职时间
    private Date entryTime;
    //合同类型,在字典中有
    private String fkHtlxId;
    private String htlxName;
    //招聘形式
    private String recruitWay;
    //试用期到期日
    private Date syqdqTime;
    //证件类型
    private String idType;
    //证件号
    private String idCard;
    //个人邮箱
    private String staffEmail;
    //电话
    private String staffTel;
    //籍贯
    private String nativePlace;
    //出生日期
    private String birthday;
    //民族，字典中有
    private String fkNationId;
    private String nationName;
    //户口所在地
    private String hkszd;
    //政治面貌
    private String politivalOutlook;
    //现住地址
    private String currentAddress;
    //婚姻状况
    private Integer isMarry;
    //最高学历,字典中有
    private String fkEducationId;
    private String educationName;
    //最高学历毕业院校
    private String zgxlbyyx;
    //外语等级
    private String wyDj;
    //计算机等级
    private String jsjDj;
    //所学专业
    private String major;
    //工资开银行
    private String bankName;
    //工资卡银行账户
    private String bankId;
    //所属支行
    private String branchBank;
    //所有人
    private String syr;
    //银行卡图片路径
    private String picturePath;
    //汇报类型名称1
    private String hblxName1;
    //汇报对象-业务,放员工ID
    private String object1;
    //汇报类型名称2
    private String hblxName2;
    //汇报对象-行政,放员工ID
    private String object2;
    //汇报类型名称3
    private String hblxName3;
    //汇报对象-人事,放员工ID
    private String object3;
    //汇报类型名称4
    private String hblxName4;
    //汇报对象-财务,放员工ID
    private String object4;
    //汇报类型名称5，备用的
    private String hblxName5;
    //汇报对象-备用,放员工ID
    private String object5;

}
