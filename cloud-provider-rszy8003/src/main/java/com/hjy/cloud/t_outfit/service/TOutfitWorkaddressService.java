package com.hjy.cloud.t_outfit.service;

import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TOutfitWorkaddress)表服务接口
 *
 * @author makejava
 * @since 2021-02-24 10:59:42
 */
public interface TOutfitWorkaddressService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tOutfitWorkaddress
     * @return
     */
    CommonResult insert(TOutfitWorkaddress tOutfitWorkaddress);

    /**
     * 修改数据
     *
     * @param tOutfitWorkaddress
     * @return
     */
    CommonResult updateByPkId(TOutfitWorkaddress tOutfitWorkaddress);

    /**
     * 删除数据
     *
     * @param tOutfitWorkaddress
     * @return
     */
    CommonResult delete(TOutfitWorkaddress tOutfitWorkaddress);

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
     * @param tOutfitWorkaddress 实体对象
     * @return
     */
    CommonResult selectById(TOutfitWorkaddress tOutfitWorkaddress);
}
