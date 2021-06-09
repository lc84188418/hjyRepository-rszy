package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.result.ReassignApprovalResult;
import com.hjy.cloud.t_staff.service.TStaffReassignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (TStaffReassign)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@Api(tags = "员工管理-人员调动-控制层")
@Slf4j
@RestController
public class TStaffReassignController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffReassignService tStaffReassignService;

    /**
     * 管理员添加调动信息页面
     */
    @PostMapping(value = "/staff/reassign/addPage")
    public CommonResult insertPage(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.insertPage(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 管理员添加调动信息
     * @param tStaffReassign 实体对象
     * @return 新增结果
     */
    //@RequiresPermissions({"reassign:add"})
    @PostMapping(value = "/staff/reassign/add")
    public CommonResult insert(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.insert(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

//    /**
//     * 删除数据
//     *
//     * @param tStaffReassign 实体对象
//     * @return 删除结果
//     */
//    @DeleteMapping(value = "/staff/reassign/del")
//    public CommonResult delete(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
//        try {
//            return tStaffReassignService.delete(tStaffReassign);
//        } catch (Exception e) {
//            String message = "失败";
//            log.error(message,e);
//            throw new FebsException(message);
//        }
//    }

    /**
     * 分页查询所有数据
     * 现只限于管理端
     * @param param json参数
     * @return 所有数据
     */
    //@RequiresPermissions({"reassign:view"})
    @PostMapping(value = "/staff/reassign/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffReassignService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffReassign 实体对象
     */
    @PostMapping(value = "/staff/reassign/get")
    public CommonResult selectOne(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.selectById(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffReassign 实体对象
     * @return 修改结果
     */
    //@RequiresPermissions({"reassign:update"})
    @PutMapping(value = "/staff/reassign/update")
    public CommonResult update(@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.updateByPkId(tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 发起调动审批页面
     * 管理员
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-人员变动",operType = "发起调动",operDesc = "发起调动申请")
    @PostMapping(value = "/staff/reassign/initiateApvPage")
    public CommonResult initiateApvPage(HttpServletRequest request,@RequestBody TStaffReassign tStaffReassign) throws FebsException {
        try {
            return tStaffReassignService.initiateApvPage(request,tStaffReassign);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 发起调动审批
     * 管理员
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-人员变动",operType = "发起调动",operDesc = "发起调动申请")
    //@RequiresPermissions({"reassign:initiateApv"})
    @PostMapping(value = "/staff/reassign/initiateApv")
    public CommonResult initiateApv(HttpServletRequest request,@RequestBody String param) throws FebsException {
        try {
            return tStaffReassignService.initiateApv(request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 发起调动审批页面
     * 员工
     * @return 修改结果
     */
    @ApiOperation(value = "员工发起调动申请页面-已完成", notes = "无需参数，代入token即可")
    @OperLog(operModul = "工作台-全部申请-调动申请",operType = "发起调动",operDesc = "发起调动申请页面")
    @GetMapping(value = "/staff/reassign/initiateApvPage/user")
    public CommonResult<ReassignApprovalResult> userInitiateApvPage(HttpServletRequest request) throws FebsException {
        try {
            return tStaffReassignService.userInitiateApvPage(request);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 发起调动审批
     * 员工
     * @return 修改结果
     */
    @ApiOperation(value = "员工发起调动申请-已完成", notes = "员工发起调动申请,如果是管理员操作肯定报错")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reassignTime",value = "调动时间",required = true,dataType = "date",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "reassignType",value = "调动类型",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "reassignDeptId",value = "调动后部门id",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "reassignPosition",value = "调动后职位id",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "reassignAddress",value = "调动后工作地id",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "reassignReason",value = "调动原因",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "reassignReason",value = "调动原因",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "csrList",value = "抄送人",required = false,dataType = "object",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "apvList",value = "审批人",required = false,dataType = "object",paramType = "body",example = "1"),
    })
    @OperLog(operModul = "工作台-全部申请-调动申请",operType = "发起调动",operDesc = "发起调动申请")
    //@RequiresPermissions({"reassign:initiateApv"})
    @PostMapping(value = "/staff/reassign/initiateApv/user")
    public CommonResult userInitiateApv(HttpServletRequest request,@RequestBody String param) throws FebsException {
        try {
            return tStaffReassignService.userInitiateApv(request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}
