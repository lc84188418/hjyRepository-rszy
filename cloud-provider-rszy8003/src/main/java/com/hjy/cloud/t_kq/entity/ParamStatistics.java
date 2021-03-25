package com.hjy.cloud.t_kq.entity;

import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_kq.entity
 * @date 2021/3/25 18:00
 * description:
 */
@Data
public class ParamStatistics {
    private String weekOrMonth;
    private String startDate;
    private String endDate;
    private String monthDate;
    private String fkStaffId;

}
