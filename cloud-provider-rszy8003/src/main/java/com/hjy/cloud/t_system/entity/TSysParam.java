package com.hjy.cloud.t_system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TSysParam)实体类
 *
 * @author makejava
 * @since 2020-09-28 09:48:44
 */
@Data
public class TSysParam implements Serializable {
    private static final long serialVersionUID = 120120666405092963L;
    /**
     * 参数主键id
     */
    private String pkParamId;
    /**
     * 键
     */
    private String paramKey;
    /**
     * 值
     */
    private String paramValue;
    /**
     * 修改人
     */
    private String operatorPeople;
    /**
     * 修改时间
     */
    private Date operatorTime;
}