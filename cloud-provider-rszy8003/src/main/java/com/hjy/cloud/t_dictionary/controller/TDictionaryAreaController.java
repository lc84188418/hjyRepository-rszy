package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.t_dictionary.entity.TDictionaryArea;
import com.hjy.cloud.t_dictionary.service.TDictionaryAreaService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionaryArea)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:50
 */
@Api(tags = "数据字典-地级区-控制层")
@Slf4j
@RestController
public class TDictionaryAreaController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryAreaService tDictionaryAreaService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/area/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryAreaService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryArea 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "字典管理-区域管理",operType = "添加",operDesc = "新增地级区域信息")
    //@RequiresPermissions({"area:add"})
    @PostMapping(value = "/dictionary/area/add")
    public CommonResult insert(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.insert(tDictionaryArea);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryArea 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "字典管理-区域管理",operType = "删除",operDesc = "删除地级区域信息")
    //@RequiresPermissions({"area:del"})
    @DeleteMapping(value = "/dictionary/area/del")
    public CommonResult delete(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.delete(tDictionaryArea);
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
    @OperLog(operModul = "字典管理-区域管理",operType = "查看",operDesc = "查看地级区域信息列表")
    //@RequiresPermissions({"area:view"})
    @PostMapping(value = "/dictionary/area/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryAreaService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryArea 实体对象
     */
    @OperLog(operModul = "字典管理-区域管理",operType = "查看",operDesc = "查看单个地级区域信息")
    //@RequiresPermissions({"area:get"})
    @PostMapping(value = "/dictionary/area/get")
    public CommonResult selectOne(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.selectById(tDictionaryArea);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryArea 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "字典管理-区域管理",operType = "修改",operDesc = "修改地级区域基本信息")
    //@RequiresPermissions({"area:update"})
    @PutMapping(value = "/dictionary/area/update")
    public CommonResult update(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.updateByPkId(tDictionaryArea);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
