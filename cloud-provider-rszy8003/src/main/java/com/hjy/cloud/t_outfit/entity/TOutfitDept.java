package com.hjy.cloud.t_outfit.entity;

import lombok.Data;

import java.util.Date;

/**
 * (TOutfitDept)表实体类
 *
 * @author makejava
 * @since 2021-02-23 00:07:06
 */
@Data
public class TOutfitDept {
    //部门主键ID
    private String pkDeptId;
    //部门名称
    private String deptName;
    //部门地址
    private String deptAddress;
    //创建时间
    private Date createTime;
    //部门级别
    private Integer deptLevel;
    //上级部门ID
    private String superiorDept;
    //部门领导
    private String deptLeader;
    //备注
    private String remarks;
    //修改时间
    private Date modifyTime;
    //领导证件号
    private String leaderCard;
    //最大人数
    private Integer maxNum;

}