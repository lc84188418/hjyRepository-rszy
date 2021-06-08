package com.hjy.cloud.t_apv.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TApvGroup)表实体类
 *
 * @author makejava
 * @since 2021-02-26 14:50:50
 */
@Data
public class TApvGroup {
    //审批分组主键
    private String pkGroupId;
    //审批分组名称
    private String groupName;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //操作人
    private String operatePeople;
    //是否启用
    private Integer turnOn;

}
