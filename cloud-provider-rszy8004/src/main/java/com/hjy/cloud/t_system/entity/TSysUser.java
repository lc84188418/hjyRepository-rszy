package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TSysUser)实体类
 *
 * @author makejava
 * @since 2020-09-28 09:48:47
 */
@Data
public class TSysUser implements Serializable {
    private static final long serialVersionUID = -33905200480123950L;
    /**
     * 主键id
     */
    private String pkUserId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginDate;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String tel;
    /**
     * 证件号
     */
    private String idcard;
    /**
     * 真名
     */
    private String fullName;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 职位
     */
    private String workPosition;
    /**
     * 工作内容
     */
    private String workContent;
    /**
     * ip
     */
    private String ip;
    /**
     * 启用状态  1启用0禁用
     */
    private String enableStatus;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 用户有效期止
     */
    private Date expireTime;
    /**
     * 部门,主要用于查询时方便展示，实际有关联表
     */
    private String deptName;
    /**
     * 角色
     */
    private String roleName;
}