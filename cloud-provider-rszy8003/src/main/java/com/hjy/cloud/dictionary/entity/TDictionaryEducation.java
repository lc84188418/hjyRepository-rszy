package com.hjy.cloud.dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryEducation)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
@Data
public class TDictionaryEducation {

    private String pkEducationId;
    //学历名称
    private String educationName;
    //是否启用
    private Integer turnOn;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}
