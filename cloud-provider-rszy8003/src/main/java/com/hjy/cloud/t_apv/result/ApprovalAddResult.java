package com.hjy.cloud.t_apv.result;

import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import lombok.Data;

import java.util.List;

/**
 * @author liuchun
 * @Package com.hjy.cloud.t_apv.result
 * @date 2021/6/4 16:31
 * description:
 */
@Data
public class ApprovalAddResult {

    private List<TApvApvtype> apvtypes;

    private List<TStaffInfo> staffInfos;

    private List<TApvApproval> positions;

}
