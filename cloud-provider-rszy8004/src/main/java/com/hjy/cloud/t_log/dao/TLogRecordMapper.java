package com.hjy.cloud.t_log.dao;

import com.hjy.cloud.t_log.entity.TLogRecord;

import java.util.List;

/**
 * (TLogRecord)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-02 10:02:01
 */
public interface TLogRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TLogRecord selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tLogRecord 实例对象
     * @return 对象列表
     */
    List<TLogRecord> selectAllPage(TLogRecord tLogRecord);

    /**
     * 新增数据
     *
     * @param tLogRecord 实例对象
     * @return 影响行数
     */
    int insertSelective(TLogRecord tLogRecord);

    /**
     * 修改数据
     *
     * @param tLogRecord 实例对象
     * @return 影响行数
     */
    int updateByPkId(TLogRecord tLogRecord);

    /**
     * 通过主键删除数据
     *
     * @param tLogRecord 实例对象
     * @return 影响行数
     */
    int deleteById(TLogRecord tLogRecord);
}
