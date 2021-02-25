package com.hjy.cloud.t_outfit.entity;

import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_outfit.entity
 * @date 2021/2/25 14:21
 * description:
 */
@Data
public class TOutfitStructure {
    /**
     * 公司ID
     */
    private String companyId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 在职人数
     */
    private Integer zzNum;
    /**
     * 编制人数
     */
    private Integer bzNum;
    /**
     * 负责人
     */
    private String fzr;
}
