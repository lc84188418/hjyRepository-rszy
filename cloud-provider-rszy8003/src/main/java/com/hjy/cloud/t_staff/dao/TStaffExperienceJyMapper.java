package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffExperienceJy;

import java.util.List;

/**
 * (TStaffExperienceJy)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
public interface TStaffExperienceJyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffExperienceJy selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffExperienceJy 实例对象
     * @return 对象列表
     */
    List<TStaffExperienceJy> selectAllPage(TStaffExperienceJy tStaffExperienceJy);

    /**
     * 新增数据
     *
     * @param tStaffExperienceJy 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffExperienceJy tStaffExperienceJy);

    /**
     * 修改数据
     *
     * @param tStaffExperienceJy 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffExperienceJy tStaffExperienceJy);

    /**
     * 通过主键删除数据
     *
     * @param tStaffExperienceJy 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffExperienceJy tStaffExperienceJy);
}
