package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (ReJbGroup)表实体类
 *
 * @author makejava
 * @since 2021-03-18 12:08:44
 */
@Data
public class ReJbGroup {

    private String pkJbgroupId;

    private String fkJbId;

    private String fkGroupId;
    //------------
    private String jbName;

}
