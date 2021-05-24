package com.hjy.cloud.t_outfit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * (TOutfitWorkaddress)表实体类
 *
 * @author makejava
 * @since 2021-02-24 10:59:42
 */
@Data
@ApiModel("工作地")
public class TOutfitWorkaddress {
    @ApiModelProperty("考勤组主键id")
    private String pkWorkadressId;
    @ApiModelProperty(value = "名称", example = "", required = false)
    private String workadressName;
    @ApiModelProperty(value = "城市", example = "", required = false)
    private String fkCityId;
    @ApiModelProperty(value = "地级区", example = "", required = false)
    private String fkAreaId;
    @ApiModelProperty(value = "详细地址", example = "", required = false)
    private String workadressAddress;
    @ApiModelProperty(value = "类型", example = "", required = false)
    private String workadressType;
    @ApiModelProperty(value = "主要负责人", example = "", required = false)
    private String workadressFzr;
    @ApiModelProperty(value = "负责人联系方式", example = "", required = false)
    private String workadressTel;

}
