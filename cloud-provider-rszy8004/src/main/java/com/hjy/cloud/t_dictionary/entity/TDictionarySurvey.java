package com.hjy.cloud.t_dictionary.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TDictionarySurvey)表实体类
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
@Data
public class TDictionarySurvey {
    //员工概况主键
    private String pkSurveyId;
    //员工概况
    private String staffSurvey;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //是否启用
    private Integer turnOn;

}
