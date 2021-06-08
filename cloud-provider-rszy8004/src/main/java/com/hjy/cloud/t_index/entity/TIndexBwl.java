package com.hjy.cloud.t_index.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TIndexBwl)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:50:52
 */
@Data
public class TIndexBwl {

    private String pkBwlId;
    //备忘录名称
    private String bwlName;
    //备忘录内容
    private String bwlContent;
    //提醒日期
    private Date remindTime;
    //添加此备忘录的用户员工ID
    private String fkUserId;
    //是否已发送
    private int isSend;

}
