package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TempApvEntity;
import com.hjy.cloud.t_apv.result.ApprovalAddResult;
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
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult<ApprovalAddResult> insertPage();

    /**
     * 添加数据
     *
     * @param param
     * @return
     */
    CommonResult insert(String param);

    /**
     * 修改数据
     *
     * @param tApvApproval
     * @return
     */
    CommonResult updateByPkId(TApvApproval tApvApproval);

    /**
     * 删除数据
     *
     * @param tApvApproval
     * @return
     */
    CommonResult delete(TApvApproval tApvApproval);

    /**
     * 获取单个数据
     *
     * @param tApvApproval 实体对象
     * @return
     */
    CommonResult selectById(TApvApproval tApvApproval);
    DApvRecord selectApvRecordById(String pkRecordId);
    /**
     * 将末尾的审批流程isEnding改为0
     *
     * @param entity 实体对象
     * @return
     */
    int updateAPV_isending(TApvApproval entity);
    /**
     * 删除与一级审批相关联的审批数据
     */
    int deleteApvRecordBySourceId(@Param("sourceId")String sourceId);
    /**
     * 待审批,是指所有没有审批完成的记录，非操作用户自己的
     *
     */
    CommonResult<PageResult<DApvRecord>> waitApv(int pageNum, int pageSize);
    //审批已处理
    CommonResult<PageResult<DApvRecord>> ApvComplete(int pageNum, int pageSize);
    /**
     * 待审批,操作用户自己的
     *
     */
    CommonResult waitApvUser(HttpSession session,HttpServletRequest request);
    /**
     * 我发起的申请
     *
     */
    CommonResult<PageResult<TempApvEntity>> apvRecordListSponsor(HttpSession session, HttpServletRequest request, String param);
    CommonResult<PageResult<TempApvEntity>> apvRecordListCCToMe(HttpSession session, HttpServletRequest request, String param);

    /**
     * 审批设置
     *
     * @return 修改结果
     */
    CommonResult approvalSet();
    /**
     * 该审批流程的详情
     *
     * @return 修改结果
     */
    CommonResult waitApvDetail(String param);
    /**
     * 审批
     *
     * @return 修改结果
     */
    CommonResult approval(HttpSession session, HttpServletRequest request,String param);

    TApvApproval selectApvSet(String pk_apv_id, String approvalType,int dataType, int isStart);
    /**
     * 查询某审批类型的默认抄送人
     */
    List<TApvApproval> selectAllPage(TApvApproval tApvApproval);
    /**
     * 审批流程的详情
     *
     * @return 修改结果
     */
    CommonResult apvProcessDetail(String param);
    /**
     * 批量添加审批记录
     *
     * @return 修改结果
     */
    int insertApvRecordBatch(List<DApvRecord> apvRecordList);

}
