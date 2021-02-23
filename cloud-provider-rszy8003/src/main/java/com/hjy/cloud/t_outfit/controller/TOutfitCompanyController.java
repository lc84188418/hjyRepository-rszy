package com.hjy.cloud.t_outfit.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.service.TOutfitCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TOutfitCompany)表控制层
 *
 * @author makejava
 * @since 2021-02-23 15:33:47
 */
@RestController
public class TOutfitCompanyController {
    /**
     * 服务对象
     */
    @Resource
    private TOutfitCompanyService tOutfitCompanyService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/outfit/company/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tOutfitCompanyService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tOutfitCompany 实体对象
     * @return 新增结果
     */
    @RequiresPermissions({"company:add"})
    @PostMapping(value = "/outfit/company/add")
    public CommonResult insert(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.insert(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitCompany 实体对象
     * @return 删除结果
     */
    @RequiresPermissions({"company:del"})
    @DeleteMapping(value = "/outfit/company/del")
    public CommonResult delete(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.delete(tOutfitCompany);
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
    @RequiresPermissions({"company:view"})
    @PostMapping(value = "/outfit/company/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tOutfitCompanyService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tOutfitCompany
     */
    @RequiresPermissions({"company:get"})
    @PostMapping(value = "/outfit/company/get")
    public CommonResult selectOne(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.selectById(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tOutfitCompany 实体对象
     * @return 修改结果
     */
    @RequiresPermissions({"company:update"})
    @PutMapping(value = "/outfit/company/update")
    public CommonResult update(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.updateByPkId(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}