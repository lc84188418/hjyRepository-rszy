package com.hjy.cloud.t_index.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.t_index.service.TIndexBwlService;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * (TIndexBwl)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:50:55
 */
@Api(tags = "备忘录控制层")
@Slf4j
@RestController
public class TIndexBwlController {
    /**
     * 服务对象
     */
    @Resource
    private TIndexBwlService tIndexBwlService;

    /**
     * 1 跳转到新增页面
     */
//    @GetMapping(value = "/index/bwl/addPage")
//    public CommonResult insertPage() throws FebsException {
//        try {
//            return tIndexBwlService.insertPage();
//        } catch (Exception e) {
//            String message = "失败";
//            log.error(message,e);
//            throw new FebsException(message);
//        }
//    }

    /**
     * 新增数据
     *
     * @param tIndexBwl 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增备忘录-已完成", notes = "新增备忘录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bwlName",value = "备忘录名称",required = true,dataType = "string",paramType = "body",example = "备忘录名称"),
            @ApiImplicitParam(name = "bwlContent",value = "备忘录内容",required = true,dataType = "string",paramType = "body",example = "备忘录内容"),
            @ApiImplicitParam(name = "remindTime",value = "提醒日期",required = true,dataType = "date-time",paramType = "body",example = "2021-05-26 15:49:22"),
    })
    @OperLog(operModul = "首页-备忘录管理",operType = "添加",operDesc = "新增备忘录信息")
    //@RequiresPermissions({"bwl:add"})
    @PostMapping(value = "/index/bwl/add")
    public CommonResult insert(HttpSession session,HttpServletRequest request,@ApiIgnore() @RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.insert(session,request,tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @ApiOperation(value = "删除-已完成", notes = "删除备忘录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkBwlId",value = "主键id",required = true,dataType = "string",paramType = "path",example = "1"),
    })
    @OperLog(operModul = "首页-备忘录管理",operType = "删除",operDesc = "删除备忘录信息")
    //@RequiresPermissions({"bwl:del"})
    @DeleteMapping(value = "/index/bwl/del/{pkBwlId}")
    public CommonResult delete(@PathVariable("pkBwlId") String pkBwlId) throws FebsException {
        try {
            return tIndexBwlService.delete(pkBwlId);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @ApiOperation(value = "管理员分页+查询-已完成", notes = "用于管理员操作分页+查询，可查看所有的备忘录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "param", value = "暂时无条件", required = false, dataType = "object", paramType = "body", example = ""),
    })
    @OperLog(operModul = "首页-备忘录管理",operType = "查看",operDesc = "查看备忘录信息列表")
    //@RequiresPermissions({"bwl:adminView"})
    @PostMapping(value = "/index/bwl/admin/list")
    public CommonResult<PageResult<TIndexBwl>> selectAllAdmin(@RequestBody PageRequest<TIndexBwl> pageRequest) throws FebsException {
        try {
            return tIndexBwlService.selectAll(pageRequest);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @ApiOperation(value = "用户分页+查询-已完成", notes = "用户用户操作分页+查询，可查看自己的备忘录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "param", value = "暂时无条件", required = false, dataType = "object", paramType = "body", example = ""),
    })
    @OperLog(operModul = "首页-备忘录管理",operType = "查看",operDesc = "查看备忘录信息列表")
    //@RequiresPermissions({"bwl:view"})
    @PostMapping(value = "/index/bwl/list")
    public CommonResult<PageResult<TIndexBwl>> selectAll(HttpSession session, HttpServletRequest request,@RequestBody PageRequest<TIndexBwl> pageRequest) throws FebsException {
        try {
            return tIndexBwlService.selectAll(session,request,pageRequest);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     */
    @ApiOperation(value = "获取单个数据-已完成", notes = "查看单个备忘录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkBwlId", value = "主键", required = true, dataType = "string", paramType = "path", example = "1")
    })
    @OperLog(operModul = "首页-备忘录管理",operType = "查看",operDesc = "查看单个备忘录信息")
    //@RequiresPermissions({"bwl:get"})
    @PostMapping(value = "/index/bwl/get/{pkBwlId}")
    public CommonResult<TIndexBwl> selectOne(@PathVariable("pkBwlId") String pkBwlId) throws FebsException {
        try {
            return tIndexBwlService.selectById(pkBwlId);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tIndexBwl 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改备忘录信息-已完成", notes = "修改备忘录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bwlName",value = "备忘录名称",required = true,dataType = "string",paramType = "body",example = "备忘录名称"),
            @ApiImplicitParam(name = "bwlContent",value = "备忘录内容",required = true,dataType = "string",paramType = "body",example = "备忘录内容"),
            @ApiImplicitParam(name = "remindTime",value = "提醒日期",required = true,dataType = "date-time",paramType = "body",example = "2021-05-26 15:49:22"),
            @ApiImplicitParam(name = "pkBwlId",value = "主键",required = true,dataType = "string",paramType = "body",example = "1"),
    })
    @OperLog(operModul = "首页-备忘录管理",operType = "修改",operDesc = "修改备忘录信息")
    //@RequiresPermissions({"bwl:update"})
    @PutMapping(value = "/index/bwl/update")
    public CommonResult update(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.updateByPkId(tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
