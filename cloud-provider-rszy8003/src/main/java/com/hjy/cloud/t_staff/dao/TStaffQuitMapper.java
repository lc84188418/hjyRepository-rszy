package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffQuit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TStaffQuit)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
public interface TStaffQuitMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffQuit selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffQuit 实例对象
     * @return 对象列表
     */
    List<TStaffQuit> selectAllPage(TStaffQuit tStaffQuit);

    List<TStaffQuit> selectAllByEntity(TStaffQuit tStaffQuit);

    /**
     * 新增数据
     *
     * @param tStaffQuit 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffQuit tStaffQuit);

    /**
     * 修改数据
     *
     * @param tStaffQuit 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffQuit tStaffQuit);

    /**
     * 通过主键删除数据
     *
     * @param tStaffQuit 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffQuit tStaffQuit);

    TStaffQuit selectByStaffId(@Param("fkStaffId") String fkUserId);

}
