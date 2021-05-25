package com.hjy.cloud.t_dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryNation)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
@Data
public class TDictionaryNation {

    private String pkNationId;
    //民族名称
    private String nationName;
    //是否启用
    private Integer turnOn;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}
