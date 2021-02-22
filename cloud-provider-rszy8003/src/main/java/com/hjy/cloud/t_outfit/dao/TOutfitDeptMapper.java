package com.hjy.cloud.t_outfit.dao;

import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TOutfitDept)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-23 00:07:06
 */
public interface TOutfitDeptMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pkDeptId 主键
     * @return 实例对象
     */
    TOutfitDept selectById(String pkDeptId);

    /**
     * 新增数据
     *
     * @param TOutfitDept 实例对象
     * @return 影响行数
     */
    int insertSelective(TOutfitDept TOutfitDept);

    /**
     * 修改数据
     *
     * @param TOutfitDept 实例对象
     * @return 影响行数
     */
    int updateById(TOutfitDept TOutfitDept);

    /**
     * 通过主键删除数据
     *
     * @param pkDeptId 主键
     * @return 影响行数
     */
    int deleteById(String pkDeptId);

    /**
     * 查询所有行数据
     * @return 对象列表
     */
    List<TOutfitDept> selectAll();
    /**
     * 通过实体作为筛选条件查询
     *
     * @param TOutfitDept 实例对象
     * @return 对象列表
     */
    List<TOutfitDept> selectAllByEntity(TOutfitDept TOutfitDept);


    List<String> selectAllDeptName();

    List<String> selectDeptUser_userIded();

    List<String> selectDeptUserByDept(@Param("fkDeptId")String deptIdStr);

    int deleteDeptUserByDeptId(@Param("fkDeptId")String fk_dept_id);

    /**
     * 批量添加部门用户
     */
    int addDeptUserByList(@Param("idList")List<ReDeptUser> idList);

    List<TOutfitDept> selectAllIdAndName();

    int addDeptUserByDeptUser(ReDeptUser deptUser);

    int deleteDeptUserByUserId(@Param("fkUserId")String fkUserId);

    String selectDeptIdByUserId(@Param("fkUserId")String fkUserId);
}