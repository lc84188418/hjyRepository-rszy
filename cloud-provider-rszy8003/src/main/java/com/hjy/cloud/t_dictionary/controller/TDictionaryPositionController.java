package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.t_dictionary.service.TDictionaryPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionaryPosition)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
@Api(tags = "数据字典-职位-控制层")
@Slf4j
@RestController
public class TDictionaryPositionController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryPositionService tDictionaryPositionService;

    /**
     * 1 跳转到新增页面
     */
//    @GetMapping(value = "/dictionary/position/addPage")
//    public CommonResult insertPage() throws FebsException {
//        try {
//            return tDictionaryPositionService.insertPage();
//        } catch (Exception e) {
//            String message = "失败";
//            log.error(message,e);
//            throw new FebsException(message);
//        }
//    }

    /**
     * 新增数据
     *
     * @param tDictionaryPosition 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "添加-已完成", notes = "测试完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionName", value = "职位名称",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "turnOn", value = "是否启用",required = true, dataType = "int", paramType = "body", example = "1"),
    })
    @OperLog(operModul = "字典管理-职位管理",operType = "添加",operDesc = "新增职位基本信息")
    //@RequiresPermissions({"position:add"})
    @PostMapping(value = "/dictionary/position/add")
    public CommonResult insert(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.insert(tDictionaryPosition);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryPosition 实体对象
     * @return 删除结果
     */
    @ApiOperation(value = "删除-已完成", notes = "对接测试完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkPositionId", value = "职位信息主键",required = true, dataType = "string", paramType = "body", example = "1"),
    })
    @OperLog(operModul = "字典管理-职位管理",operType = "删除",operDesc = "删除职位基本信息")
    //@RequiresPermissions({"position:del"})
    @DeleteMapping(value = "/dictionary/position/del")
    public CommonResult delete(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.delete(tDictionaryPosition);
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
    @ApiOperation(value = "查询-已完成", notes = "对接测试完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionName", value = "职位名称",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "pageNum", value = "页码",required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "条数",required = false, dataType = "int", paramType = "body", example = "10"),
    })
    @OperLog(operModul = "字典管理-职位管理",operType = "查看",operDesc = "查看职位信息列表")
    //@RequiresPermissions({"position:view"})
    @PostMapping(value = "/dictionary/position/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryPositionService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryPosition 实体对象
     */
    @OperLog(operModul = "字典管理-职位管理",operType = "查看",operDesc = "查看单个职位信息")
    //@RequiresPermissions({"position:get"})
    @PostMapping(value = "/dictionary/position/get")
    public CommonResult selectOne(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.selectById(tDictionaryPosition);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryPosition 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改-已完成", notes = "对接测试完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkPositionId", value = "职位信息主键",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "positionName", value = "职位名称",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "turnOn", value = "是否启用",required = false, dataType = "string", paramType = "body", example = "10"),
    })
    @OperLog(operModul = "字典管理-职位管理",operType = "修改",operDesc = "修改职位基本信息")
    //@RequiresPermissions({"position:update"})
    @PutMapping(value = "/dictionary/position/update")
    public CommonResult update(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.updateByPkId(tDictionaryPosition);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
