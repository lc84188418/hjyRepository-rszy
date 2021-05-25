package com.hjy.cloud.t_log.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TLogException)表实体类
 *
 * @author makejava
 * @since 2021-03-02 10:01:57
 */
@Data
public class TLogException {

    private String pkExcId;

    private String excRequParam;

    private String excName;

    private String excMsg;

    private String operUserId;

    private String operUserName;

    private String operMethod;

    private String operUrl;

    private String operIp;

    private String operVersion;

    private Date excTime;

}
