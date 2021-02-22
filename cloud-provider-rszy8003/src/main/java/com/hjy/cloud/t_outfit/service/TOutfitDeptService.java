package com.hjy.cloud.t_outfit.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_system.entity.ReDeptUser;

import java.util.List;

/**
 * (TOutfitDept)表服务接口
 *
 * @author makejava
 * @since 2021-02-23 00:07:06
 */
public interface TOutfitDeptService {
    /**
     * 通过ID查询单条数据
     *
     * @param pkDeptId 主键
     * @return 实例对象
     */
    TOutfitDept selectById(String pkDeptId) throws Exception;


    /**
     * 新增数据
     *
     * @param tOutfitDept 实例对象
     * @return 实例对象
     */
    int insert(TOutfitDept tOutfitDept) throws Exception;

    /**
     * 修改数据
     *
     * @param tOutfitDept 实例对象
     * @return 实例对象
     */
    int updateById(TOutfitDept tOutfitDept) throws Exception;

    /**
     * 通过主键删除数据
     *
     * @param pkDeptId 主键
     * @return 是否成功
     */
    int deleteById(String pkDeptId);

    /**
     * 查询所有数据
     *
     * @return list
     */
    List<TOutfitDept> selectAll() throws Exception;

    /**
     * 通过实体查询所有数据
     *
     * @return list
     */
    List<TOutfitDept> selectAllByEntity(TOutfitDept tOutfitDept) throws Exception;

    List<String> selectAllDeptName();

    List<String> selectDeptUser_userIded();

    List<String> selectDeptUserByDept(String deptIdStr);
    //删除原有的部门及用户角色
    int deleteDeptUserByDeptId(String fk_dept_id);
    //添加部门用户
    int addDeptUserByList(String fk_dept_id, List<String> idList);

    List<TOutfitDept> selectAllIdAndName();

    void addDeptUserByDeptUser(ReDeptUser deptUser);
    //删除该用户的已分配的部门
    int deleteDeptUserByUserId(String pkUserId);

    String selectDeptIdByUserId(String idStr);

    CommonResult addUser(String param);
//
//    CommonResult deptDel(String param);
}