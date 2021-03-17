package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * (ReGroupStaff)表实体类
 *
 * @author makejava
 * @since 2021-03-16 15:44:49
 */
@Data
public class ReGroupStaff {
    //考勤组-员工关系表主键
    private String pkGroupstaffId;
    //考勤组ID
    private String fkGroupId;
    //员工ID
    private String fkStaffId;
    //是否需要考勤，1代表需要考勤，0代表无需考勤
    private Integer isKq;

}
