package com.hjy.cloud.t_system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (TSysPerms)实体类
 *
 * @author makejava
 * @since 2020-09-28 09:48:45
 */
@Data
@ApiModel("菜单")
public class TSysPerms implements Serializable {
    private static final long serialVersionUID = -81453380641482687L;
    private String pkPermsId;
    @ApiModelProperty(value = "父级菜单")
    private String pId;
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "父级菜单")
    private String path;
    @ApiModelProperty(value = "权限码")
    private String permsCode;
    @ApiModelProperty(value = "菜单类型1代表模块2主菜单3按钮")
    private String menuType;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;
    @ApiModelProperty(value = "修改人名字")
    private String modifyUsername;
    @ApiModelProperty(value = "修改人id")
    private String fkUserId;
    @ApiModelProperty(value = "次级菜单")
    private List<TSysPerms> child;
}