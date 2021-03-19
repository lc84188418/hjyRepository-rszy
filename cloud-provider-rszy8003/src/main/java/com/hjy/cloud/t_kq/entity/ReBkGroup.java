package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (ReBkGroup)表实体类
 *
 * @author makejava
 * @since 2021-03-19 15:49:46
 */
@Data
public class ReBkGroup {

    private String pkBkgroupId;
    //补卡规则主键
    private String fkBkId;
    //考勤组主键
    private String fkGroupId;

}
