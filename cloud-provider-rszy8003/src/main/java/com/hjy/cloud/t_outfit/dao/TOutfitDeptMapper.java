package com.hjy.cloud.t_outfit.dao;

import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.entity.TOutfitStructure;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TOutfitDept)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-24 21:13:38
 */
public interface TOutfitDeptMapper {

    /**
     * 分页记录条数
     * @return 记录条数
     */
    int selectSize(TOutfitDept tOutfitDept);

    /**
     * 通过ID查询单条数据
     * @return 实例对象
     */
    TOutfitDept selectByPkId(String pk_id);

    /**
     * 通过实体作为筛选条件查询
     * @return 对象列表
     */
    List<TOutfitDept> selectAllPage(TOutfitDept tOutfitDept);

    /**
     * 新增数据
     * @return 影响行数
     */
    int insertSelective(TOutfitDept tOutfitDept);
    /**
     * 新增所有列数据
     * @return 影响行数
     */
    int insert(TOutfitDept tOutfitDept);

    /**
     * 修改数据
     * @return 影响行数
     */
    int updateByPkId(TOutfitDept tOutfitDept);

    /**
     * 通过主键删除数据
     * @return 影响行数
     */
    int deleteById(TOutfitDept tOutfitDept);
    /**
     * 获取所有部门列表
     * @return 所有部门的ID和名称
     */
    List<TOutfitDept> selectAllIdAndName();

    /**
     * 查询已分配的用户部门并进行回显
     * @return
     */
    List<String> selectDeptUser_userIded(@Param("fkCompanyId")String fkCompanyId);
    /**
     * 查询已分配的用户部门并进行回显
     * @return
     */
    List<String> selectDeptUserByDept(@Param("fkDeptId")String fkDeptId,@Param("fkCompanyId")String fkCompanyId);
    /**
     * 查询所有部门名称
     * @return
     */
    List<String> selectAllDeptName();
    /**
     * 删除原有的部门及用户
     * @return
     */
    int deleteDeptUserByDeptId(@Param("fkDeptId")String fkDeptId,@Param("fkCompanyId")String fkCompanyId);
    /**
     * 批量添加部门用户
     * @return
     */
    int addDeptUserByList(@Param("idList") List<ReDeptUser> idList);
    /**
     * 通过用户ID删除原有部门信息
     * @return
     */
    int deleteDeptUserByUserId(@Param("pkUserId")String pkUserId);
    /**
     * 添加用户部门
     * @return
     */
    int addDeptUserByDeptUser(ReDeptUser deptUser);

    String selectDeptIdByUserId(@Param("fkUserId")String idStr);
    /**
     * 查询该部门是否含有上级部门
     * @return
     */
    String selectSuperiorDeptId(@Param("pkDeptId")String pkDeptId);
    /**
     * 合并部门前获取所有部门数据，除开当前部门
     */
    List<TOutfitDept> selectAllIdAndName_BBKDQBM(@Param("pkDeptId")String pkDeptId);
    /**
     * 通过部门ID删除公司部门信息
     */
    int deleteCompanyDeptByDeptId(@Param("fkDeptId")String fkDeptId);

    /**
     * 移动该部门的人到目标部门
     * @param hbdDeptId
     */
    void updateByDeptId(@Param("hbDeptId")String hbDeptId,@Param("hbdDeptId")String hbdDeptId);

    List<TOutfitStructure> selectStructure();
}