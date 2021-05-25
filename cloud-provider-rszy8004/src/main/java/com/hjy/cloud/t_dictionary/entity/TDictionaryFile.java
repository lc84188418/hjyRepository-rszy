package com.hjy.cloud.t_dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionaryFile)表实体类
 *
 * @author makejava
 * @since 2021-03-02 15:16:30
 */
@Data
public class TDictionaryFile {
    //主键
    private String pkFileId;
    //图片名称
    private String fileName;
    //图片路径
    private String filePath;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //是否启用
    private Integer turnOn;
    //文件类型，1为图片文件，0为其他
    private Integer fileType;

}
