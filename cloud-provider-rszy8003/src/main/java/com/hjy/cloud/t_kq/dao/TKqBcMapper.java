package com.hjy.cloud.t_kq.dao;

import com.hjy.cloud.t_kq.entity.TKqBc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TKqBc)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-16 14:50:56
 */
public interface TKqBcMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TKqBc selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tKqBc 实例对象
     * @return 对象列表
     */
    List<TKqBc> selectAllPage(TKqBc tKqBc);

    /**
     * 新增数据
     *
     * @param tKqBc 实例对象
     * @return 影响行数
     */
    int insertSelective(TKqBc tKqBc);

    /**
     * 修改数据
     *
     * @param tKqBc 实例对象
     * @return 影响行数
     */
    int updateByPkId(TKqBc tKqBc);

    /**
     * 通过主键删除数据
     *
     * @param tKqBc 实例对象
     * @return 影响行数
     */
    int deleteById(TKqBc tKqBc);

    int deleteGroupBcByBcId(@Param("fkBcId")String fkBcId);

    TKqBc selectDefaultBc();
}
