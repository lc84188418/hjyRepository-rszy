package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.t_staff.entity.TStaffContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TStaffContract)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
public interface TStaffContractMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkContrctId 主键
     * @return 实例对象
     */
    TStaffContract selectByPkId(@Param("pkContrctId")String pkContrctId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffContract 实例对象
     * @return 对象列表
     */
    List<TStaffContract> selectAllPage(TStaffContract tStaffContract);

    /**
     * 新增数据
     *
     * @param tStaffContract 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffContract tStaffContract);

    /**
     * 修改数据
     *
     * @param tStaffContract 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffContract tStaffContract);

    /**
     * 通过主键删除数据
     *
     * @param tStaffContract 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffContract tStaffContract);

}
