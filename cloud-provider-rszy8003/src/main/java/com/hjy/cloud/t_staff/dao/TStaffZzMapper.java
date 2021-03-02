package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffZz;

import java.util.List;

/**
 * (TStaffZz)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:18
 */
public interface TStaffZzMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffZz selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffZz 实例对象
     * @return 对象列表
     */
    List<TStaffZz> selectAllPage(TStaffZz tStaffZz);

    /**
     * 新增数据
     *
     * @param tStaffZz 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffZz tStaffZz);

    /**
     * 修改数据
     *
     * @param tStaffZz 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffZz tStaffZz);

    /**
     * 通过主键删除数据
     *
     * @param tStaffZz 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffZz tStaffZz);
}
