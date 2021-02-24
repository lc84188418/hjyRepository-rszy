package com.hjy.cloud.t_outfit.entity;

import lombok.Data;

/**
 * (TOutfitWorkaddress)表实体类
 *
 * @author makejava
 * @since 2021-02-24 10:59:42
 */
@Data
public class TOutfitWorkaddress {

    private String pkWorkadressId;
    //名称
    private String workadressName;
    //城市
    private String fkCityId;
    //地级区
    private String fkAreaId;
    //详细地址
    private String workadressAddress;
    //类型
    private String workadressType;
    //主要负责人
    private String workadressFzr;
    //负责人联系方式
    private String workadressTel;

}
