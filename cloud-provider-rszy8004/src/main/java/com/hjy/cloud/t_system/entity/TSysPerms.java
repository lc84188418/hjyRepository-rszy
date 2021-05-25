package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TSysPerms)实体类
 *
 * @author makejava
 * @since 2020-09-28 09:48:45
 */
@Data
public class TSysPerms implements Serializable {
    private static final long serialVersionUID = -81453380641482687L;
    private String pkPermsId;
    /**
     * 父级菜单
     */
    private String pId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 路由路径
     */
    private String path;
    /**
     * 权限码
     */
    private String permsCode;
    /**
     * 菜单类型1代表模块2类别3主菜单4按钮
     */
    private String menuType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人名字
     */
    private String modifyUsername;
    /**
     * 修改人id
     */
    private String fkUserId;
}