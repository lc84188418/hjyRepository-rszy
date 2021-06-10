package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.result.EntryApprovalResult;
import com.hjy.cloud.t_staff.service.TStaffEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (TStaffEntry)表控制层
 *
 * @author makejava
 * @since 2021-02-26 10:55:27
 */
@Api(tags = "员工管理-入职-控制层")
@Slf4j
@RestController
public class TStaffEntryController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffEntryService tStaffEntryService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/entry/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffEntryService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffEntry 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增待入职-已完成", notes = "测试完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffName", value = "姓名",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "staffSex", value = "性别",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "staffAge", value = "年龄",required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "staffDept", value = "部门",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "staffPosition", value = "职位",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "staffTel", value = "电话",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "fkHtlxId", value = "合同类型",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "workAddress", value = "工作地点",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "recruitWay", value = "招聘方式",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "idType", value = "证件类型",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "idCard", value = "证件号",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "entryTime", value = "入职日期",required = true, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "email", value = "个人邮箱",required = false, dataType = "string", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "entryDesc", value = "入职说明",required = false, dataType = "string", paramType = "body", example = "1"),
    })
    @OperLog(operModul = "人员管理-入职管理",operType = "添加",operDesc = "新增入职基本信息")
    //@RequiresPermissions({"entry:add"})
    @PostMapping(value = "/staff/entry/add")
    public CommonResult insert(@RequestBody TStaffEntry tStaffEntry, HttpServletRequest request) throws FebsException {
        try {
            return tStaffEntryService.insert(tStaffEntry,request);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffEntry 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "删除",operDesc = "删除入职基本信息")
    //@RequiresPermissions({"entry:del"})
    @DeleteMapping(value = "/staff/entry/del")
    public CommonResult delete(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.delete(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 管理员-分页查询所有待入职的数据
     * status ！= 1
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "查看",operDesc = "查看入职信息列表")
    //@RequiresPermissions({"entry:adminView"})
    @PostMapping(value = "/staff/entry/adminList")
    public CommonResult adminList(@RequestBody String param) throws FebsException {
        try {
            return tStaffEntryService.adminList(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 员工-查询个人入职信息
     * @param servletRequest json参数
     * @return 所有数据
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "查看",operDesc = "查看个人入职信息列表")
    @PostMapping(value = "/staff/entry/userGet")
    public CommonResult userGet(HttpServletRequest servletRequest) throws FebsException {
        try {
            return tStaffEntryService.userGet(servletRequest);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffEntry 实体对象
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "查看",operDesc = "查看单条入职信息")
    //@RequiresPermissions({"entry:get"})
    @PostMapping(value = "/staff/entry/get")
    public CommonResult selectOne(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.selectById(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffEntry 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "修改",operDesc = "修改单条入职信息")
    //@RequiresPermissions({"entry:update"})
    @PutMapping(value = "/staff/entry/update")
    public CommonResult update(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.updateByPkId(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 弃职
     *
     * @param tStaffEntry 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-入职管理",operType = "弃职",operDesc = "员工弃职")
    @PutMapping(value = "/staff/entry/giveUp")
    public CommonResult giveUp(@RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.giveUp(tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 管理端 发起入职审批页面
     *
     * @param tStaffEntry 实体对象中的pkEntryId
     * @return 修改结果
     */
    @ApiOperation(value = "管理端发起入职审批页面-已完成", notes = "发起入职审批前获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkEntryId",value = "入职信息主键",required = true,dataType = "string",paramType = "body",example = "1"),
    })
    @PostMapping(value = "/staff/entry/approvalPage")
    public CommonResult<EntryApprovalResult> approvalPage(HttpServletRequest request, @RequestBody TStaffEntry tStaffEntry) throws FebsException {
        try {
            return tStaffEntryService.approvalPage(request,tStaffEntry);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 管理端发起入职审批
     *
     * @return 修改结果
     */
    @ApiOperation(value = "管理端发起入职审批-已完成", notes = "只有管理员可以操作，发起入职审批，若是员工端操作，肯定报错")
    @OperLog(operModul = "人员管理-入职管理",operType = "入职审批",operDesc = "员工入职审批")
    @PostMapping(value = "/staff/entry/approval")
    public CommonResult approval(HttpServletRequest request,@RequestBody String param) throws FebsException {
        try {
            return tStaffEntryService.approval(request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

}
