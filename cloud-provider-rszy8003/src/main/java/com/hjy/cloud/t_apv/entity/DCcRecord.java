package com.hjy.cloud.t_apv.entity;

import lombok.Data;

/**
 * (DCcRecord)表实体类
 *
 * @author makejava
 * @since 2021-03-08 18:11:21
 */
@Data
public class DCcRecord {

    private String pkCcId;

    private String fkStaffId;

    private String staffName;

    private String firstApvrecordId;
    //-----------
    //岗位名字
    private String stationName;

}
