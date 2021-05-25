package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffExperienceGz;

import java.util.List;

/**
 * (TStaffExperienceGz)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
public interface TStaffExperienceGzMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffExperienceGz selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffExperienceGz 实例对象
     * @return 对象列表
     */
    List<TStaffExperienceGz> selectAllPage(TStaffExperienceGz tStaffExperienceGz);

    /**
     * 新增数据
     *
     * @param tStaffExperienceGz 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffExperienceGz tStaffExperienceGz);

    /**
     * 修改数据
     *
     * @param tStaffExperienceGz 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffExperienceGz tStaffExperienceGz);

    /**
     * 通过主键删除数据
     *
     * @param tStaffExperienceGz 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffExperienceGz tStaffExperienceGz);
}
