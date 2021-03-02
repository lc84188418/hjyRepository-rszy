package com.hjy.cloud.t_train.entity;

import java.util.Date;

import lombok.Data;

/**
 * (TTrainInfo)表实体类
 *
 * @author makejava
 * @since 2021-03-01 16:41:40
 */
@Data
public class TTrainInfo {

    private String pkInfoId;
    //培训活动名称
    private String trainName;
    //培训内容
    private String trainContent;
    //培训开始时间
    private Date startTime;
    //培训结束时间
    private Date endTime;
    //培训地点
    private String trainAddress;
    //培训机构
    private String trainOrganization;
    //培训人ID
    private String trainPeople;

}
