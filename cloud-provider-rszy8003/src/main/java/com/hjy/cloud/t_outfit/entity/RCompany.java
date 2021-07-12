package com.hjy.cloud.t_outfit.entity;

import lombok.Data;

import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_outfit.entity
 * @date 2021/7/12 15:42
 * description:
 */
@Data
public class RCompany {
    private String pkCompanyId;
    //公司名称
    private String companyName;
    private List<RDept> depts;
}
