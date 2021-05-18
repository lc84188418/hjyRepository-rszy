package com.hjy.cloud.t_train.entity;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * (TTrainInfo)表实体类
 *
 * @author makejava
 * @since 2021-03-01 16:41:40
 */
@Data
@ApiModel("liuchun-培训信息-实体类")
public class TTrainInfo<User> {
    @ApiModelProperty(value = "培训信息主键", example = "", required = false)
    private String pkInfoId;
    @ApiModelProperty(value = "培训活动名称", example = "", required = false)
    private String trainName;
    @ApiModelProperty(value = "培训内容", example = "", required = false)
    private String trainContent;
    @ApiModelProperty(value = "培训开始时间", example = "", required = false)
    private Date startTime;
    @ApiModelProperty(value = "培训结束时间", example = "", required = false)
    private Date endTime;
    @ApiModelProperty(value = "培训地点", example = "", required = false)
    private String trainAddress;
    @ApiModelProperty(value = "培训机构", example = "", required = false)
    private String trainOrganization;
    @ApiModelProperty(value = "培训人姓名", example = "", required = false)
    private String trainPeople;
    @ApiModelProperty(value = "参与者", example = "", required = false,hidden = true)
    private String ourJoin;
    @ApiModelProperty(value = "参与者集合", example = "", required = false)
    private List<User> join;

}
