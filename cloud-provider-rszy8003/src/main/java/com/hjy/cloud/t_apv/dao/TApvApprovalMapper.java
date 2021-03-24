package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.common.entity.DApvRecord;
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
     * 分页记录条数
     *
     * @param tApvApproval 实例对象
     * @return 记录条数
     */
    int selectSize(TApvApproval tApvApproval);

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

    /**
     * 新增数据
     *
     * @param tApvApproval 实例对象
     * @return 影响行数
     */
    int insertSelective(TApvApproval tApvApproval);

    int insert(TApvApproval tApvApproval);
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
    /**
     * 所有末尾的审批流程
     *
     * @return 影响行数
     */
    List<TApvApproval> selectAllEnding();
    /**
     * 查询该审批类型是否已添加过数据
     *
     * @return 影响行数
     */
    TApvApproval selectByType(@Param("apvType") String apvType);

    List<TApvApproval> selectApvByType(@Param("apvType")String pk_apv_type);

    TApvApproval selectApvSet(@Param("pkApprovalId")String pkApprovalId,@Param("approvalType")String approvalType,@Param("dataType")int dataType,@Param("isStart")int isStart);
    /**
     * 批量添加审批记录
     *
     * @return 影响行数
     */
    int insertApvRecordBatch(@Param("apvRecords")List<DApvRecord> apvRecords);
    int insertApvRecord(DApvRecord apvRecords);
    /**
     * 通过审批记录主键查询数据
     * @param pkRecordId
     * @return
     */
    DApvRecord selectApvRecordById(@Param("pkRecordId")String pkRecordId);
    DApvRecord selectApvRecordByPkId(@Param("pkRecordId")String pkRecordId);

    int deleteApvRecordBySourceId(@Param("sourceId")String sourceId);
    /**
     * 待审批,是指所有没有审批完成的记录，非操作用户自己的
     */
    List<DApvRecord> waitApv();
    /**
     * 待审批,操作用户自己的
     */
    List<DApvRecord> waitApvUser(@Param("apvApproval")String userId);

    List<DApvRecord> selectApvRecordBySourceId_UserId(@Param("approvalType")String approvalType,@Param("sourceId")String sourceId, @Param("userId")String userId);

    int updateIsIngBySourceId(@Param("sourceId")String sourceId);

    int updateApvRecord(DApvRecord apvRecord);

    /**
     * 通过第一审批记录的ID查询整个审批流程记录信息
     * @param firstApvRecordId
     * @return
     */
    List<DApvRecord> selectByFirstApvRecordId(@Param("firstApvRecordId")String firstApvRecordId);

    int deleteByType(@Param("approvalType")String approvalType);

    int updateBatchApvRecord(@Param("apvRecordList")List<DApvRecord> updateList);
}
