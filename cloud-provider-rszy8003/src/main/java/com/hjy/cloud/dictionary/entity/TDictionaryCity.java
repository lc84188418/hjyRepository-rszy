package com.hjy.cloud.dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryCity)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
@Data
public class TDictionaryCity {

    private String pkCityId;
    //城市名称
    private String cityName;
    //是否启用
    private Integer turnOn;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //所属省份
    private String fkProvinceId;

}
