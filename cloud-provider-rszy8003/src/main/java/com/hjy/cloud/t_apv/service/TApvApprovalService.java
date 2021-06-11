package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.result.ApprovalAddResult;
import com.hjy.cloud.t_apv.result.ApprovalSource;
import com.hjy.cloud.utils.page.PageResult;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TApvApproval)表服务接口
 *
 * @author makejava
 * @since 2021-02-26 14:50:47
 */
public interface TApvApprovalService {
    CommonResult<ApprovalAddResult> insertPage();
    CommonResult insert(String param);
    CommonResult updateByPkId(TApvApproval tApvApproval);
    CommonResult delete(TApvApproval tApvApproval);
    CommonResult selectById(TApvApproval tApvApproval);
    /**
     * 删除与一级审批相关联的审批数据
     */
    int deleteApvRecordBySourceId(@Param("sourceId")String sourceId);
    //管理端-待审批
    CommonResult<PageResult<DApvRecord>> waitApv(int pageNum, int pageSize);
    //管理端-已处理
    CommonResult<PageResult<DApvRecord>> ApvComplete(int pageNum, int pageSize);
    /**
     * 待审批,操作用户自己的
     */
    CommonResult waitApvUser(HttpSession session,HttpServletRequest request);
    //员工端-我发起的申请
    CommonResult<PageResult<DApvRecord>> apvRecordListSponsor(HttpSession session, HttpServletRequest request, String param);
    //员工端-抄送给我的
    CommonResult<PageResult<DApvRecord>> apvRecordListCCToMe(HttpSession session, HttpServletRequest request, String param);
    //审批设置
    CommonResult approvalSet();
    //该审批流程的详情
    CommonResult<ApprovalSource> waitApvDetail(String param);
    //审批
    CommonResult approval(HttpSession session, HttpServletRequest request,String param);

    TApvApproval selectApvSet(String pk_apv_id, String approvalType,int dataType, int isStart);
    /**
     * 查询某审批类型的默认抄送人
     */
    List<TApvApproval> selectAllPage(TApvApproval tApvApproval);
    //审批流程的详情
    CommonResult apvProcessDetail(String param);

}
