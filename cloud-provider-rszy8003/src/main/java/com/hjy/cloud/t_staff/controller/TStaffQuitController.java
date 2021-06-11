package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffQuit;
import com.hjy.cloud.t_staff.service.TStaffQuitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (TStaffQuit)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@Api(tags = "员工管理-离职-控制层")
@Slf4j
@RestController
public class TStaffQuitController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffQuitService tStaffQuitService;

    /**
     * 1 跳转到新增页面
     */
    @ApiOperation(value = "员工发起离职申请页面-已完成", notes = "该接口前端已采用其他接口实现，")
    @GetMapping(value = "/staff/quit/addPage")
    public CommonResult insertPage(HttpServletRequest request) throws FebsException {
        try {
            return tStaffQuitService.insertPage(request);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @ApiOperation(value = "员工发起离职申请-已完成", notes = "员工发起离职申请,如果是管理员操作肯定报错")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "quitType",value = "离职类型",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "quitReason",value = "离职原因",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "quitTime",value = "离职日期",required = true,dataType = "date",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "remarks",value = "备注",required = false,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "csrList",value = "抄送人",required = false,dataType = "object",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "apvList",value = "审批人",required = false,dataType = "object",paramType = "body",example = "1"),
    })
    @PostMapping(value = "/staff/quit/add")
    public CommonResult insert(@ApiIgnore() @RequestBody String param, HttpServletRequest request) throws FebsException {
        try {
            return tStaffQuitService.insert(request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffQuit 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/quit/del")
    public CommonResult delete(@RequestBody TStaffQuit tStaffQuit) throws FebsException {
        try {
            return tStaffQuitService.delete(tStaffQuit);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     * 管理员
     * @param param json参数
     * @return 所有数据
     */
    @ApiOperation(value = "查询离职数据-已完成", notes = "管理员查询离职数据，quitStatus = 1 代表已离职 quitStatus = 0 代表离职中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "staffName",value = "员工姓名，支持模糊查询",required = false,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "quitStatus",value = "离职审批状态,",required = true,dataType = "int",paramType = "body",example = "1"),
    })
    //@RequiresPermissions({"staffQuit:adminView"})
    @PostMapping(value = "/staff/quit/adminList")
    public CommonResult adminList(@RequestBody String param) throws FebsException {
        try {
            return tStaffQuitService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffQuit 实体对象
     */
    @PostMapping(value = "/staff/quit/get")
    public CommonResult selectOne(@RequestBody TStaffQuit tStaffQuit) throws FebsException {
        try {
            return tStaffQuitService.selectById(tStaffQuit);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffQuit 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/quit/update")
    public CommonResult update(@RequestBody TStaffQuit tStaffQuit) throws FebsException {
        try {
            return tStaffQuitService.updateByPkId(tStaffQuit);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
