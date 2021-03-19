package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.TKqClock;

import java.util.List;

/**
 * (TKqClock)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-19 17:46:25
 */
public interface TKqClockMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TKqClock selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqClock 实例对象
     * @return 对象列表
     */
    List<TKqClock> selectAllPage(TKqClock tKqClock);

    /**
     * 新增数据
     *
     * @param tKqClock 实例对象
     * @return 影响行数
     */
    int insertSelective(TKqClock tKqClock);

    /**
     * 修改数据
     *
     * @param tKqClock 实例对象
     * @return 影响行数
     */
    int updateByPkId(TKqClock tKqClock);

    /**
     * 通过主键删除数据
     *
     * @param tKqClock 实例对象
     * @return 影响行数
     */
    int deleteById(TKqClock tKqClock);
}
