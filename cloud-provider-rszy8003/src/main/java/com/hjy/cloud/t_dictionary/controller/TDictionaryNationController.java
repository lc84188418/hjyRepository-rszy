package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.t_dictionary.entity.TDictionaryNation;
import com.hjy.cloud.t_dictionary.service.TDictionaryNationService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionaryNation)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
@Api(tags = "数据字典-民族-控制层")
@Slf4j
@RestController
public class TDictionaryNationController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryNationService tDictionaryNationService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/nation/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryNationService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryNation 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "字典管理-民族管理",operType = "添加",operDesc = "新增名族基本信息")
    @RequiresPermissions({"nation:add"})
    @PostMapping(value = "/dictionary/nation/add")
    public CommonResult insert(@RequestBody TDictionaryNation tDictionaryNation) throws FebsException {
        try {
            return tDictionaryNationService.insert(tDictionaryNation);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 批量新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/nation/addBatch")
    public CommonResult addBatch(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryNationService.addBatch(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryNation 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "字典管理-民族管理",operType = "删除",operDesc = "删除名族基本信息")
    @RequiresPermissions({"nation:del"})
    @DeleteMapping(value = "/dictionary/nation/del")
    public CommonResult delete(@RequestBody TDictionaryNation tDictionaryNation) throws FebsException {
        try {
            return tDictionaryNationService.delete(tDictionaryNation);
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
    @OperLog(operModul = "字典管理-民族管理",operType = "查看",operDesc = "查看名族信息列表")
    @RequiresPermissions({"nation:view"})
    @PostMapping(value = "/dictionary/nation/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryNationService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryNation 实体对象
     */
    @OperLog(operModul = "字典管理-民族管理",operType = "查看",operDesc = "查看单个名族信息")
    @RequiresPermissions({"nation:get"})
    @PostMapping(value = "/dictionary/nation/get")
    public CommonResult selectOne(@RequestBody TDictionaryNation tDictionaryNation) throws FebsException {
        try {
            return tDictionaryNationService.selectById(tDictionaryNation);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryNation 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "字典管理-民族管理",operType = "修改",operDesc = "修改名族基本信息")
    @RequiresPermissions({"nation:update"})
    @PutMapping(value = "/dictionary/nation/update")
    public CommonResult update(@RequestBody TDictionaryNation tDictionaryNation) throws FebsException {
        try {
            return tDictionaryNationService.updateByPkId(tDictionaryNation);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
