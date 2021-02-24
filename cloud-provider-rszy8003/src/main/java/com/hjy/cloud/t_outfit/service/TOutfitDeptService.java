package com.hjy.cloud.t_outfit.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_system.entity.ReDeptUser;

import java.util.List;

/**
 * (TOutfitDept)表服务接口
 *
 * @author makejava
 * @since 2021-02-24 21:13:39
 */
public interface TOutfitDeptService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tOutfitDept
     * @return
     */
    CommonResult insert(TOutfitDept tOutfitDept);

    /**
     * 修改数据
     *
     * @param tOutfitDept
     * @return
     */
    CommonResult updateByPkId(TOutfitDept tOutfitDept);

    /**
     * 删除数据
     *
     * @param tOutfitDept
     * @return
     */
    CommonResult delete(TOutfitDept tOutfitDept);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);

    /**
     * 获取单个数据
     *
     * @param tOutfitDept 实体对象
     * @return
     */
    CommonResult selectById(TOutfitDept tOutfitDept);
    /**
     * 给部门下发用户UI
     *
     * @param param json参数
     * @return
     */
    CommonResult addUserUI(String param);
    /**
     * 给部门下发用户
     *
     * @param param json参数
     * @return
     */
    CommonResult addUser(String param);

    /**
     * 添加用户部门
     */
    void addDeptUserByDeptUser(ReDeptUser deptUser);
    /**
     * 获取所有部门的ID和名称
     */
    List<TOutfitDept> selectAllIdAndName();
    /**
     * 获取所有部门名称
     */
    List<String> selectAllDeptName();

    String selectDeptIdByUserId(String idStr);
}