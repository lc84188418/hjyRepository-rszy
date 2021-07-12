package com.hjy.cloud.t_outfit.service;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;

/**
 * (TOutfitCompany)表服务接口
 *
 * @author makejava
 * @since 2021-02-23 15:33:46
 */
public interface TOutfitCompanyService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tOutfitCompany
     * @return
     */
    CommonResult insert(TOutfitCompany tOutfitCompany);

    /**
     * 修改数据
     *
     * @param tOutfitCompany
     * @return
     */
    CommonResult updateByPkId(TOutfitCompany tOutfitCompany);

    /**
     * 删除数据
     *
     * @param tOutfitCompany
     * @return
     */
    CommonResult delete(TOutfitCompany tOutfitCompany);

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
     * @param tOutfitCompany
     * @return
     */
    CommonResult selectById(TOutfitCompany tOutfitCompany);
    /**
     * 分配部门UI
     *
     * @param tOutfitCompany
     * @return
     */
    CommonResult distributeDeptUI(TOutfitCompany tOutfitCompany);
    /**
     * 分配部门
     *
     * @param param
     * @return
     */
    CommonResult distributeDept(String param);
    /**
     * 组织架构
     *
     * @return
     */
    CommonResult structure();

    CommonResult structureTree();
}