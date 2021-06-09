package com.hjy.cloud.t_staff.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_staff.result
 * @date 2021/6/9 15:04
 * description:
 */
@Data
@ApiModel("员工选择")
public class StaffInfos {
    @ApiModelProperty(value = "员工主键id", example = "", required = false)
    private String pkStaffId;
    @ApiModelProperty(value = "姓名", example = "", required = false)
    private String staffName;
    @ApiModelProperty(value = "部门id", example = "", required = false)
    private String fkDeptId;
    @ApiModelProperty(value = "部门名称，只做查询展示用", example = "", hidden = true)
    private String deptName;
    @ApiModelProperty(value = "职位id", example = "", required = false)
    private String fkPositionId;
    @ApiModelProperty(value = "职位名称，只做查询展示用", example = "", hidden = true)
    private String positionName;
}
