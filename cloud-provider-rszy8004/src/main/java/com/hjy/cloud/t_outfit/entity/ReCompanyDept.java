package com.hjy.cloud.t_outfit.entity;

import lombok.Data;

/**
 * (ReCompanyDept)表实体类
 *
 * @author makejava
 * @since 2021-02-24 10:16:40
 */
@Data
public class ReCompanyDept {

    private String pkCompanydeptId;
    //公司ID
    private String fkCompanyId;
    //部门ID
    private String fkDeptId;

}
