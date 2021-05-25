package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (ReGroupWorkingdays)表实体类
 *
 * @author makejava
 * @since 2021-03-18 12:08:44
 */
@Data
public class ReGroupWorkingdays {

    private String pkGroupworkingdaysId;
    //考勤组ID
    private String fkGroupId;
    //工作日,星期一星期天
    private String workingDays;
    //班次
    private String fkBcId;
    //考勤类型
    private Integer kqType;
}
