package com.hjy.cloud.t_apv.result;

import com.hjy.cloud.t_apv.entity.TempApvEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.result
 * @date 2021/6/4 10:28
 * description:
 */
@Data
public class ApprovalRecordResult {
    @ApiModelProperty(value = "审批记录", example = "", required = false)
    private List<TempApvEntity> apvRecords;
}
