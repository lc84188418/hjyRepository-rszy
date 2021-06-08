package com.hjy.cloud.t_apv.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TApvApvtype)表实体类
 *
 * @author makejava
 * @since 2021-02-26 14:50:49
 */
@Data
public class TApvApvtype {
    //审批类型字典主键
    private String pkApvtypeId;
    //审批类型名称
    private String apvtypeName;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //是否启用
    private Integer turnOn;
    //操作人ID
    private String operatePeople;
    //流程说明
    private String processDesc;
    //所属分组ID
    private String fkGroupId;
    //图标路径
    private String iconPath;
}
