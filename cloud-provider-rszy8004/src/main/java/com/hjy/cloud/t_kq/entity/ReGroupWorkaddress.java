package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (ReGroupWorkaddress)表实体类
 *
 * @author makejava
 * @since 2021-03-18 12:08:44
 */
@Data
public class ReGroupWorkaddress {

    private String pkGroupworkaddressId;
    //考勤组ID
    private String fkGroupId;
    //工作地ID
    private String fkWorkaddressId;
    //
    //工作地名称
    private String workaddressName;
}
