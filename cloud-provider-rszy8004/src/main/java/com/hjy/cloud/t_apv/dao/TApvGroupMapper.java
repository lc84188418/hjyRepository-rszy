package com.hjy.cloud.t_apv.dao;

import com.hjy.cloud.t_apv.entity.TApvGroup;

import java.util.List;

/**
 * (TApvGroup)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-26 14:50:50
 */
public interface TApvGroupMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TApvGroup selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tApvGroup 实例对象
     * @return 对象列表
     */
    List<TApvGroup> selectAllPage(TApvGroup tApvGroup);

    /**
     * 新增数据
     *
     * @param tApvGroup 实例对象
     * @return 影响行数
     */
    int insertSelective(TApvGroup tApvGroup);

    /**
     * 修改数据
     *
     * @param tApvGroup 实例对象
     * @return 影响行数
     */
    int updateByPkId(TApvGroup tApvGroup);

    /**
     * 通过主键删除数据
     *
     * @param tApvGroup 实例对象
     * @return 影响行数
     */
    int deleteById(TApvGroup tApvGroup);
}
