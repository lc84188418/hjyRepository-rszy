package com.hjy.cloud.t_log.dao;

import com.hjy.cloud.t_log.entity.TLogException;

import java.util.List;

/**
 * (TLogException)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-02 10:01:59
 */
public interface TLogExceptionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TLogException selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tLogException 实例对象
     * @return 对象列表
     */
    List<TLogException> selectAllPage(TLogException tLogException);

    /**
     * 新增数据
     *
     * @param tLogException 实例对象
     * @return 影响行数
     */
    int insertSelective(TLogException tLogException);

    /**
     * 修改数据
     *
     * @param tLogException 实例对象
     * @return 影响行数
     */
    int updateByPkId(TLogException tLogException);

    /**
     * 通过主键删除数据
     *
     * @param tLogException 实例对象
     * @return 影响行数
     */
    int deleteById(TLogException tLogException);
}
