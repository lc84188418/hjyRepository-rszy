package com.hjy.cloud.t_outfit.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_outfit.service.TOutfitWorkaddressService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TOutfitWorkaddress)表控制层
 *
 * @author makejava
 * @since 2021-02-24 10:59:42
 */
@RestController
public class TOutfitWorkaddressController {
    /**
     * 服务对象
     */
    @Resource
    private TOutfitWorkaddressService tOutfitWorkaddressService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/outfit/workaddress/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tOutfitWorkaddressService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tOutfitWorkaddress 实体对象
     * @return 新增结果
     */
    @RequiresPermissions({"workaddress:add"})
    @PostMapping(value = "/outfit/workaddress/add")
    public CommonResult insert(@RequestBody TOutfitWorkaddress tOutfitWorkaddress) throws FebsException {
        try {
            return tOutfitWorkaddressService.insert(tOutfitWorkaddress);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitWorkaddress 实体对象
     * @return 删除结果
     */
    @RequiresPermissions({"workaddress:del"})
    @DeleteMapping(value = "/outfit/workaddress/del")
    public CommonResult delete(@RequestBody TOutfitWorkaddress tOutfitWorkaddress) throws FebsException {
        try {
            return tOutfitWorkaddressService.delete(tOutfitWorkaddress);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @RequiresPermissions({"workaddress:view"})
    @PostMapping(value = "/outfit/workaddress/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tOutfitWorkaddressService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tOutfitWorkaddress 实体对象
     */
    @RequiresPermissions({"workaddress:view"})
    @PostMapping(value = "/outfit/workaddress/get")
    public CommonResult selectOne(@RequestBody TOutfitWorkaddress tOutfitWorkaddress) throws FebsException {
        try {
            return tOutfitWorkaddressService.selectById(tOutfitWorkaddress);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tOutfitWorkaddress 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/outfit/workaddress/update")
    public CommonResult update(@RequestBody TOutfitWorkaddress tOutfitWorkaddress) throws FebsException {
        try {
            return tOutfitWorkaddressService.updateByPkId(tOutfitWorkaddress);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
