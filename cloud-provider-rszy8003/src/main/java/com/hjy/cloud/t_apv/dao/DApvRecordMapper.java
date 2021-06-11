package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.t_apv.entity.DApvRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DApvRecord)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-08 18:09:44
 */
public interface DApvRecordMapper {
    //通过ID查询单条数据
    DApvRecord selectByPkId(@Param("pkRecordId")String pkRecordId);

    //通过ID查询是否有数据
    int selectCountByEntity(DApvRecord dApvRecord);

    //通过实体作为筛选条件查询
    List<DApvRecord> selectAllEntity(DApvRecord dApvRecord);

    List<String> selectFirstRecordIdsByFkStaffId(@Param("fkStaffId")String fkStaffId);

    //通过主键集合作为条件查询
    List<DApvRecord> selectAllByIds(@Param("ids") List<String> idList);
    //新增数据
    int insertSelective(DApvRecord dApvRecord);
    /**
     * 批量添加审批记录
     *
     * @return 影响行数
     */
    int insertApvRecordBatch(@Param("apvRecords")List<DApvRecord> apvRecords);
    //通锅主键修改数据
    int updateByPkId(DApvRecord dApvRecord);
//    //修改审批完成IsIng
//    int updateIsIngBySourceId(@Param("sourceId")String sourceId);
    //修改审批状态apvStatus
    int updateApvStatusBySourceId(@Param("sourceId")String sourceId,@Param("apvStatus")int apvStatus);

    //通过实体数据删除数据
    int deleteByEntity(DApvRecord dApvRecord);

    //通过主键删除数据
    int deleteByPkId(String pkId);

    DApvRecord selectSourceIdById(@Param("pkRecordId")String pkRecordId);
}
