package com.hjy.cloud.t_apv.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.result.ApprovalAddResult;
import com.hjy.cloud.t_apv.result.ApprovalSource;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.utils.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * (TApvApproval)表控制层
 * 审批流程设置
 * @author makejava
 * @since 2021-02-26 14:50:48
 */
@Api(tags = "审批流程控制层")
@Slf4j
@RestController
public class TApvApprovalController {
    /**
     * 服务对象
     */
    @Resource
    private TApvApprovalService tApvApprovalService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/apv/approval/addPage")
    public CommonResult<ApprovalAddResult> insertPage() throws FebsException {
        try {
            return tApvApprovalService.insertPage();
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
    //@RequiresPermissions({"approval:add"})
    @PostMapping(value = "/apv/approval/add")
    public CommonResult insert(@RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.insert(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApproval 实体对象
     * @return 删除结果
     */
    //@RequiresPermissions({"approval:del"})
    @DeleteMapping(value = "/apv/approval/del")
    public CommonResult delete(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.delete(tApvApproval);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tApvApproval 实体对象
     */
    //@RequiresPermissions({"approval:get"})
    @PostMapping(value = "/apv/approval/get")
    public CommonResult selectOne(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.selectById(tApvApproval);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvApproval 实体对象
     * @return 修改结果
     */
    //@RequiresPermissions({"approval:update"})
    @PutMapping(value = "/apv/approval/update")
    public CommonResult update(@RequestBody TApvApproval tApvApproval) throws FebsException {
        try {
            return tApvApprovalService.updateByPkId(tApvApproval);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 审批设置
     *
     * @return 修改结果
     */
    //@RequiresPermissions({"approval:set"})
    @GetMapping(value = "/apv/approval/set")
    public CommonResult approvalSet() throws FebsException {
        try {
            return tApvApprovalService.approvalSet();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @ApiOperation(value = "审批已处理-已完成", notes = "这个已经对接好了")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "10"),
    })
    //@RequiresPermissions({"admin:apvComplete"})
    @GetMapping(value = "/apv/approval/ApvComplete")
    public CommonResult<PageResult<DApvRecord>> adminApvComplete(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) throws FebsException {
        try {
            return tApvApprovalService.ApvComplete(pageNum,pageSize);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 待审批,是指所有没有审批完成的记录，非操作用户自己的
     * 管理员
     * @return 修改结果
     */
    @ApiOperation(value = "管理员-待审批-已完成", notes = "这个已经对接好了")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "10"),
    })
    //@RequiresPermissions({"admin:waitApv"})
    @GetMapping(value = "/apv/approval/waitApv")
    public CommonResult<PageResult<DApvRecord>> adminWaitApv(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) throws FebsException {
        try {
            return tApvApprovalService.waitApv(pageNum,pageSize);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 待审批,是指操作用户自己的,其中prompt等于1  则该自己审批
     *
     * @return 修改结果
     */
    @ApiOperation(value = "用户-待审批-已完成", notes = "这个已经对接好了")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "10"),
    })
    //@RequiresPermissions({"user:waitApv"})
    @GetMapping(value = "/apv/approval/waitApvUser")
    public CommonResult waitApv(HttpSession session, HttpServletRequest request) throws FebsException {
        try {
            return tApvApprovalService.waitApvUser(session,request);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 审批,审批
     *
     * @return 修改结果
     */
    @ApiOperation(value = "用户-审批-已完成", notes = "用户对操作流程进行审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId",value = "页码",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "apvResult",value = "审批结果,1代表通过2代表驳回",required = true,dataType = "int",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "approvalType",value = "审批类型",required = true,dataType = "int",paramType = "body",example = "10"),
    })
    //@RequiresPermissions({"user:approval"})
    @PostMapping(value = "/apv/approval/approvalUser")
    public CommonResult approval(HttpSession session, HttpServletRequest request, @RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.approval(session,request,param);
        } catch (Exception e) {
            String message = "审批失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 该审批来源的详情
     * @return 修改结果
     */
    @ApiOperation(value = "审批来源的详情-已完成", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "approvalType",value = "审批类型",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "sourceId",value = "来源",required = true,dataType = "string",paramType = "body",example = "10"),
    })
    @PostMapping(value = "/apv/approval/waitApvDetail")
    public CommonResult<ApprovalSource> waitApvDetail(@RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.waitApvDetail(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 审批流程的详情
     *
     * @return 修改结果
     */
    @ApiOperation(value = "审批流程的详情-已完成", notes = "已对接完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apvId",value = "审批的第一id",required = true,dataType = "string",paramType = "body",example = "1"),
    })
    @PostMapping(value = "/apv/approval/apvProcessDetail")
    public CommonResult apvProcessDetail(@RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.apvProcessDetail(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 审批记录处理
     * 一、我发起的审批
     */
    @ApiOperation(value = "我发起的审批-已完成", notes = "我发起的审批-暂时未做筛选功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = false, dataType = "int", paramType = "body", example = "1"),
    })
    @PostMapping(value = "/apv/approval/list/sponsor")
    public CommonResult<PageResult<DApvRecord>> apvRecordListSponsor(HttpSession session, HttpServletRequest request, @RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.apvRecordListSponsor(session,request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 抄送给我的审批
     * @param session
     * @param request
     * @param param
     * @return
     * @throws FebsException
     */
    @ApiOperation(value = "抄送给我的审批-已完成", notes = "抄送给我的审批-暂时未做筛选功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int", paramType = "body", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = false, dataType = "int", paramType = "body", example = "1"),
    })
    @PostMapping(value = "/apv/approval/list/cc_to_me")
    public CommonResult<PageResult<DApvRecord>> apvRecordListCCToMe(HttpSession session, HttpServletRequest request, @RequestBody String param) throws FebsException {
        try {
            return tApvApprovalService.apvRecordListCCToMe(session,request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
