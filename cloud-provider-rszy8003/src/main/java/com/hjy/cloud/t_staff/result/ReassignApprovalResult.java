package com.hjy.cloud.t_staff.result;

import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_staff.result
 * @date 2021/6/3 16:17
 * description:
 */
@ApiModel("发起调动审批页面返回结果")
@Data
public class ReassignApprovalResult {
    @ApiModelProperty(value = "审批类型", example = "", required = false)
    private String approvalType;
    @ApiModelProperty(value = "审批流程", example = "", required = false)
    private List<TApvApproval> apvList;
    @ApiModelProperty(value = "抄送人", example = "", required = false)
    private List<TApvApproval> csrList;
    @ApiModelProperty(value = "当前来源详情", example = "", required = false)
    private Map<String,Object> currentSource;
    @ApiModelProperty(value = "合同类型列表", example = "", required = false)
    private List<TDictionaryHtlx> htlx;
    @ApiModelProperty(value = "所有部门列表", example = "", required = false)
    private List<TOutfitDept> depts;
    @ApiModelProperty(value = "所有职位列表", example = "", required = false)
    private List<TDictionaryPosition> positions;
    @ApiModelProperty(value = "所有工作地列表", example = "", required = false)
    private List<TOutfitWorkaddress> workaddresses;
    @ApiModelProperty(value = "公司列表", example = "", required = false)
    private List<TOutfitCompany> companies;
    @ApiModelProperty(value = "所有员工", example = "", required = false)
    private List<TOutfitCompany> staffInfos;
    @ApiModelProperty(value = "当前员工信息", example = "", required = false)
    private TStaffReassign staffInfo;
}
