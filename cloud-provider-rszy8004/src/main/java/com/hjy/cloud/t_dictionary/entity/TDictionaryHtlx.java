package com.hjy.cloud.t_dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryHtlx)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
@Data
public class TDictionaryHtlx {
    //合同类型主键
    private String pkHtlxId;
    //合同类型
    private String contracType;
    //是否启用
    private Integer turnOn;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}
