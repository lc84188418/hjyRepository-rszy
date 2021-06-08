package com.hjy.cloud.t_staff.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TStaffExperienceJy)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
@Data
public class TStaffExperienceJy {
    //教育经历信息主键
    private String pkExperienceId;
    //学校名称
    private String schoolName;
    //专业
    private String majorName;
    //入校时间
    private Date admissionTime;
    //毕业时间
    private Date graduationTime;
    //学历
    private String fkEducationId;
    //是否取得学位
    private Integer isDegree;
    //教育证书照1
    private String card1Path;
    //教育证书照2
    private String card2Path;
    //关联员工ID
    private String fkStaffId;

}
