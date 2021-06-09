package com.hjy.cloud.t_apv.result;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.result
 * @date 2021/6/9 19:10
 * description:
 */
@Data
@ApiModel("审批来源返回结果集")
public class ApprovalSource {
    private SourceDetailResult source;
}
