package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffContract)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:13
 */
@Data
public class TStaffContract {

    private String pkContrctId;
    //员工关联主键
    private String fkStaffId;
    //姓名
    private String staffName;
    //部门
    private String fkDeptId;
    //证件号,不可为空
    private String idcard;
    //合同状态
    private Integer contrctStatus;
    //合同签订类型，1代表正签，0代表续签
    private Integer signStatus;
    //合同类型ID，在字典中
    private String fkContractType;
    private String typeName;
    //合同属性
    private String contractAttribute;
    //合同期限
    private String contractQx;
    //合同公司ID，
    private String fkContractCompany;
    private String companyName;
    //合同开始日期
    private Date startTime;
    //合同签订日期
    private Date signTime;
    //合同结束日期
    private Date endTime;
    //合同签订次数
    private Integer signNum;
    //审批状态,0代表未发起审批，2代表审批中，1代表审批完成
    private Integer approvalStatus;
    //附件
    private String annex;

}
