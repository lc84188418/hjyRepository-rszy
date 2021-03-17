package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (TKqBk)表实体类
 *
 * @author makejava
 * @since 2021-03-16 14:50:58
 */
@Data
public class TKqBk {

    private String pkBkId;
    //补卡规则名字
    private String bkName;
    //补卡负责人
    private String bkStewards;
    //限制补卡次数，每月可申请几次补卡
    private Integer bkNum;
    //限制补卡时间，可申请几天内补卡
    private Integer bkDate;
    //是否启用
    private Integer turnOn;
}
