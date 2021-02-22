package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TSysRole)实体类
 *
 * @author makejava
 * @since 2020-09-28 09:48:48
 */
@Data
public class TSysRole implements Serializable {
    private static final long serialVersionUID = -20850190211268218L;
    /**
     * 主键id
     */
    private String pkRoleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 启用状态
     */
    private Integer enableStatus;
    /**
     * 修改时间
     */
    private Date modifyTime;
}