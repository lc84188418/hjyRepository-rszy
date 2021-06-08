package com.hjy.cloud.t_outfit.entity;

import lombok.Data;

import java.util.Date;

/**
 * (TOutfitCompany)表实体类
 *
 * @author makejava
 * @since 2021-02-23 13:59:53
 */
@Data
public class TOutfitCompany {

    private String pkCompanyId;
    //公司名称
    private String companyName;
    //公司地址
    private String companyAddress;
    //社会信用代码
    private String shxydm;
    //成立时间
    private Date clsj;
    //法人代表
    private String frdb;
    //法人证件号
    private String frzjh;
    //公司联系电话
    private String tel;
    //开户银行
    private String khyh;
    //银行账号
    private String yhzh;
    //上级公司
    private String sjgs;
    //公司简介
    private String introduction;
    //领域
    private String industry;
    //业务标签
    private String label;
    //企业荣誉
    private String enterpriseHonor;
    //注册资金
    private Long registeredCapital;
    //经营状态
    private Integer operationStatus;
    //经营范围
    private String operationArea;
    //备注
    private String remarks;
    private Integer startRow;
    private Integer endRow;


}