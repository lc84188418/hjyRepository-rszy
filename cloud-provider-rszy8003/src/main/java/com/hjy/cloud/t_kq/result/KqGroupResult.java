package com.hjy.cloud.t_kq.result;

import com.hjy.cloud.t_kq.entity.*;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_kq.result
 * @date 2021/6/4 14:34
 * description:
 */

@Data
public class KqGroupResult<T> {
    @ApiModelProperty(value = "当前实体信息", example = "", required = false)
    private T entity;
    @ApiModelProperty(value = "所有员工列表", example = "", required = false)
    private List<TStaffInfo> staffList;
    @ApiModelProperty(value = "已选参与考勤人员列表", example = "", required = false)
    private List<ReGroupStaff> joins;
    @ApiModelProperty(value = "已选无需考勤人员列表", example = "", required = false)
    private List<ReGroupStaff> frees;
    @ApiModelProperty(value = "所有班次列表", example = "", required = false)
    private List<TKqBc> bcList;
    @ApiModelProperty(value = "所有班次列表", example = "", required = false)
    private List<ReGroupWorkingdays> bcs;
    @ApiModelProperty(value = "所有工作日", example = "", required = false)
    private List<ReGroupWorkingdays> workingDaysList;
    @ApiModelProperty(value = "已选工作日设置", example = "", required = false)
    private List<ReGroupWorkingdays> workingDays;
    @ApiModelProperty(value = "工作地列表", example = "", required = false)
    private List<TOutfitWorkaddress> workaddressList;
    @ApiModelProperty(value = "已选工作地", example = "", required = false)
    private List<TOutfitWorkaddress> workaddress;
    @ApiModelProperty(value = "所有加班规则列表", example = "", required = false)
    private List<TKqJb> jbRuleList;
    @ApiModelProperty(value = "已选加班规则", example = "", required = false)
    private List<ReJbGroup> jbRules;
}
