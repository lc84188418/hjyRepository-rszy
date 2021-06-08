package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.t_apv.entity.DCcRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DCcRecord)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-08 18:11:21
 */
public interface DCcRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    DCcRecord selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dCcRecord 实例对象
     * @return 对象列表
     */
    List<DCcRecord> selectAllPage(DCcRecord dCcRecord);

    /**
     * 新增数据
     *
     * @param dCcRecord 实例对象
     * @return 影响行数
     */
    int insertSelective(DCcRecord dCcRecord);

    /**
     * 修改数据
     *
     * @param dCcRecord 实例对象
     * @return 影响行数
     */
    int updateByPkId(DCcRecord dCcRecord);

    /**
     * 通过主键删除数据
     *
     * @param dCcRecord 实例对象
     * @return 影响行数
     */
    int deleteById(DCcRecord dCcRecord);

    int insertCCRecordBatch(@Param("ccRecords")List<DCcRecord> ccRecordList);
}
