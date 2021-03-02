package com.hjy.cloud.t_dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryArea)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:47
 */
@Data
public class TDictionaryArea {

    private String pkAreaId;
    //地级区名称
    private String areaName;
    //是否启用
    private Integer turnOn;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //所属城市
    private String fkCityId;

}
