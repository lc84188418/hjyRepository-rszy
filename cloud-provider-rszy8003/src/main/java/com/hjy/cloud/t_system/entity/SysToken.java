package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * token实体类
 * @Author liuchun
 * @Date 2019/3/31 10:56
 * @Version 1.0
 */
@Data
public class SysToken implements Serializable {

    /**
     * tokenID
     */
    private String pkTokenId;
    /**
     * 用户ID
     */
    private String fkUserId;
    /**
     * 用户名
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * ip
     */
    private String ip;
    /**
     * fullName
     */
    private String fullName;
    /**
     * idcard
     */
    private String idcard;
    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
