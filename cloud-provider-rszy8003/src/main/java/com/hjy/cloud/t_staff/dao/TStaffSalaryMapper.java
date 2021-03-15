package com.hjy.cloud.t_staff.dao;

import com.hjy.cloud.common.entity.DSalaryRecord;
import com.hjy.cloud.t_staff.entity.TStaffSalary;

import java.util.List;

/**
 * (TStaffSalary)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
public interface TStaffSalaryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pk_id 主键
     * @return 实例对象
     */
    TStaffSalary selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tStaffSalary 实例对象
     * @return 对象列表
     */
    List<TStaffSalary> selectAllPage(TStaffSalary tStaffSalary);
//    List<TStaffSalary> selectAllPage2(TStaffSalary tStaffSalary);
    List<DSalaryRecord> selectRecordAllPage(DSalaryRecord entity);


    /**
     * 新增数据
     *
     * @param tStaffSalary 实例对象
     * @return 影响行数
     */
    int insertSelective(TStaffSalary tStaffSalary);

    /**
     * 修改数据
     *
     * @param tStaffSalary 实例对象
     * @return 影响行数
     */
    int updateByPkId(TStaffSalary tStaffSalary);

    /**
     * 通过主键删除数据
     *
     * @param tStaffSalary 实例对象
     * @return 影响行数
     */
    int deleteById(TStaffSalary tStaffSalary);

    int insertSalaryRecord(DSalaryRecord tStaffSalary);

}
