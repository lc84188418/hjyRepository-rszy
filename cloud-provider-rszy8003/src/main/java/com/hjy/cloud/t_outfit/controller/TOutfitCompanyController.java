package com.hjy.cloud.t_outfit.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.service.TOutfitCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TOutfitCompany)表控制层
 *
 * @author makejava
 * @since 2021-02-23 15:33:47
 */
@Api(tags = "公司控制层")
@Slf4j
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
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tOutfitCompany 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "机构管理-公司管理",operType = "添加",operDesc = "添加公司基本信息")
    //@RequiresPermissions({"company:add"})
    @PostMapping(value = "/outfit/company/add")
    public CommonResult insert(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.insert(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitCompany 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "机构管理-公司管理",operType = "删除",operDesc = "删除公司基本信息")
    //@RequiresPermissions({"company:del"})
    @DeleteMapping(value = "/outfit/company/del")
    public CommonResult delete(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.delete(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "机构管理-公司管理",operType = "查看",operDesc = "查看公司信息列表")
    //@RequiresPermissions({"company:view"})
    @PostMapping(value = "/outfit/company/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tOutfitCompanyService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tOutfitCompany
     */
    @OperLog(operModul = "机构管理-公司管理",operType = "查看",operDesc = "查看单个公司信息")
    //@RequiresPermissions({"company:get"})
    @PostMapping(value = "/outfit/company/get")
    public CommonResult selectOne(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.selectById(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tOutfitCompany 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "机构管理-公司管理",operType = "修改",operDesc = "修改公司基本信息")
    //@RequiresPermissions({"company:update"})
    @PutMapping(value = "/outfit/company/update")
    public CommonResult update(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException {
        try {
            return tOutfitCompanyService.updateByPkId(tOutfitCompany);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

//    /**
//     * 分配部门UI
//     * @param tOutfitCompany 实体对象
//     * @return 修改结果
//     */
//    @PostMapping("/outfit/company/distributeDeptUI")
//    public CommonResult distributeDeptUI(@RequestBody TOutfitCompany tOutfitCompany) throws FebsException{
//        try {
//            return tOutfitCompanyService.distributeDeptUI(tOutfitCompany);
//        } catch (Exception e) {
//            String message = "失败";
//            log.error(message,e);
//            throw new FebsException(message);
//        }
//    }
//    /**
//     * 分配部门
//     * @param param json参数
//     * @return 修改结果
//     */
//    @OperLog(operModul = "机构管理-公司管理",operType = "添加部门",operDesc = "为该公司新分配部门")
//    //@RequiresPermissions({"company:distributeDept"})
//    @PostMapping("/outfit/company/distributeDept")
//    public CommonResult distributeDeptUI(@RequestBody String param) throws FebsException{
//        try {
//            return tOutfitCompanyService.distributeDept(param);
//        } catch (Exception e) {
//            String message = "失败";
//            log.error(message,e);
//            throw new FebsException(message);
//        }
//    }
    /**
     * 组织架构
     * 分公司、分部门来查询
     * @param
     * @return 修改结果
     */
    @GetMapping("/outfit/organization/structure")
    public CommonResult structure() throws FebsException{
        try {
            return tOutfitCompanyService.structure();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @ApiOperation(value = "查看-已完成", notes = "获取组织架构（公司+部门）树数据")
    @GetMapping("/outfit/organization/tree")
    public CommonResult structureTree() throws FebsException{
        try {
            return tOutfitCompanyService.structureTree();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}