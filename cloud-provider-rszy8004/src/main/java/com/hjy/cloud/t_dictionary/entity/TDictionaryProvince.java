package com.hjy.cloud.t_dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryProvince)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
@Data
public class TDictionaryProvince {

    private String pkProvinceId;
    //省份名称
    private String provinceName;
    //是否启用
    private Integer turnOn;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //简称
    private String abbreviation;
    //省会
    private String provinceCapital;

}
