package com.hjy.cloud.t_apv.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.entity
 * @date 2021/3/3 17:52
 * description:
 */
@Data
public class TempApvEntity {

    private String pkApprovalId;
    private String approvalType;
    private String apvtypeName;
    private String applyPeople;
    private String sponsor;
    private Date startTime;
    private String approvalPeople1;
    private Date apvDate1;
    private Integer apvResult1;
    private String apvReason1;
    private String approvalPeople2;
    private Date apvDate2;
    private Integer apvResult2;
    private String apvReason2;
    private String approvalPeople3;
    private Date apvDate3;
    private Integer apvResult3;
    private String apvReason3;
    private String approvalPeople4;
    private Date apvDate4;
    private Integer apvResult4;
    private String apvReason4;
    private String approvalPeople5;
    private Date apvDate5;
    private Integer apvResult5;
    private String apvReason5;
    private String csr1;
    private String csr2;
    private String csr3;
    private String sourceId;
    private Integer prompt;
}
