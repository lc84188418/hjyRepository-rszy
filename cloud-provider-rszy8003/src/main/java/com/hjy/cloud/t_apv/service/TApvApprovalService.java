package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.domin.CommonResult;
import org.apache.ibatis.annotations.Param;

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
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tApvApproval
     * @return
     */
    CommonResult insert(TApvApproval tApvApproval);

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
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);

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
     * 待审批
     *
     * @return 修改结果
     */
    CommonResult waitApv();
    /**
     * 审批设置
     *
     * @return 修改结果
     */
    CommonResult approvalSet();
}
