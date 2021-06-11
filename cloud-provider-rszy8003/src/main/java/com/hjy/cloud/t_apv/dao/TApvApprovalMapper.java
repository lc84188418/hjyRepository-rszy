package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TApvApproval)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-26 14:50:47
 */
public interface TApvApprovalMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TApvApproval selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tApvApproval 实例对象
     * @return 对象列表
     */
    List<TApvApproval> selectAllPage(TApvApproval tApvApproval);

    List<TApvApproval> selectAllHandle(TApvApproval selectEntity);

    int insertBatch(@Param("approvals")List<TApvApproval> approvals);

    /**
     * 修改数据
     *
     * @param tApvApproval 实例对象
     * @return 影响行数
     */
    int updateByPkId(TApvApproval tApvApproval);

    /**
     * 通过主键删除数据
     *
     * @param tApvApproval 实例对象
     * @return 影响行数
     */
    int deleteById(TApvApproval tApvApproval);

    List<TApvApproval> selectApvByType(@Param("apvType")String pk_apv_type);

    TApvApproval selectApvSet(@Param("pkApprovalId")String pkApprovalId,@Param("approvalType")String approvalType,@Param("dataType")int dataType,@Param("isStart")int isStart);

    int deleteApvRecordBySourceId(@Param("sourceId")String sourceId);

    /**
     * 待审批,操作用户自己的
     */
    List<DApvRecord> waitApvUser(@Param("apvApproval")String userId);

    List<DApvRecord> selectApvRecordBySourceId_UserId(@Param("approvalType")String approvalType,@Param("sourceId")String sourceId, @Param("userId")String userId);


    int updateApvRecord(DApvRecord apvRecord);
    int selectCountByEntity(DApvRecord apvRecord);

    int deleteByType(@Param("approvalType")String approvalType);

}
