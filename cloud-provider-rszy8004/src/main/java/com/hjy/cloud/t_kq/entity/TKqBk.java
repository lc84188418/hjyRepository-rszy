package com.hjy.cloud.t_kq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * (TKqBk)表实体类
 *
 * @author makejava
 * @since 2021-03-16 14:50:58
 */
@Data
@ApiModel("补卡")
public class TKqBk {
    @ApiModelProperty("补卡主键")
    private String pkBkId;
    @ApiModelProperty("补卡规则名字")
    private String bkName;
    @ApiModelProperty("补卡负责人")
    private String bkStewards;
    @ApiModelProperty("限制补卡次数，每月可申请几次补卡")
    private Integer bkNum;
    @ApiModelProperty("限制补卡时间，可申请几天内补卡")
    private Integer bkDate;
    @ApiModelProperty("是否启用")
    private Integer turnOn;
    @ApiModelProperty("是否为默认规则")
    private Integer isDefault;
    //-----------
    private String stewardsName;
    private String bkGroupId;
    private String bkGroupName;
}
