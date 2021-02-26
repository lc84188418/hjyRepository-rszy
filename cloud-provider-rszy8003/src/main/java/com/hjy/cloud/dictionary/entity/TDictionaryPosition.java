package com.hjy.cloud.dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryPosition)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
@Data
public class TDictionaryPosition {
    //职位信息主键
    private String pkPositionId;
    //职位名称
    private String positionName;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //是否启用
    private Integer turnOn;

}
