package com.hjy.cloud.t_log.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TLogRecord)表实体类
 *
 * @author makejava
 * @since 2021-03-02 10:02:01
 */
@Data
public class TLogRecord {

    private String pkRecordId;

    private String recordModule;

    private String recordType;

    private String recordDesc;

    private String recordRequParam;

    private String recordRespParam;

    private String recordUserId;

    private String recordUserName;

    private String recordFullName;

    private String recordMethod;

    private String recordUrl;

    private String recordIp;

    private Date recordTime;

    private String recordVersion;

}
