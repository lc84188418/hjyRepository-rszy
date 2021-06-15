package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffContract;
import com.hjy.cloud.t_staff.service.TStaffContractService;
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
 * (TStaffContract)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
@Api(tags = "员工管理-合同-控制层")
@Slf4j
@RestController
public class TStaffContractController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffContractService tStaffContractService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/contract/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffContractService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffContract 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "查看-已完成", notes = "查看单个入职员工的档案信息详情,其中员工需访问接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fkStaffId",value = "员工关联主键",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "staffName",value = "姓名",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "contractQx",value = "合同期限",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "startTime",value = "合同开始日期",required = true,dataType = "date",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "fkContractType",value = "合同类型ID",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "endTime",value = "合同结束日期",required = true,dataType = "date",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "contractAttribute",value = "合同属性",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "signTime",value = "合同签订日期",required = true,dataType = "date",paramType = "body",example = "1"),
    })
    @OperLog(operModul = "人员管理-合同管理",operType = "添加",operDesc = "添加员工合同信息")
    //@RequiresPermissions({"contract:add"})
    @PostMapping(value = "/staff/contract/add")
    public CommonResult insert(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.insert(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffContract 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "删除",operDesc = "删除员工合同信息")
    //@RequiresPermissions({"contract:del"})
    @DeleteMapping(value = "/staff/contract/del")
    public CommonResult delete(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.delete(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 管理员分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @ApiOperation(value = "查看-已完成", notes = "查询所有签订合同的记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "staffName",value = "员工姓名",required = false,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "idcard",value = "员工证件号",required = false,dataType = "string",paramType = "body",example = "1"),
    })
    @OperLog(operModul = "人员管理-合同管理",operType = "查看",operDesc = "管理员查看员工合同信息列表")
    //@RequiresPermissions({"contract:adminView"})
    @PostMapping(value = "/staff/contract/admin/list")
    public CommonResult adminList(@RequestBody String param) throws FebsException {
        try {
            return tStaffContractService.adminList(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 用户查询个人合同信息
     * 暂时不要该功能
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "查看",operDesc = "个人查看自己合同信息")
    //@RequiresPermissions({"contract:userView"})
    @PostMapping(value = "/staff/contract/user/list")
    public CommonResult userList(HttpSession session, HttpServletRequest request, @RequestBody String param) throws FebsException {
        try {
            return tStaffContractService.userList(session,request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffContract 实体对象
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "查看",operDesc = "查看单条合同信息")
    //@RequiresPermissions({"contract:get"})
    @PostMapping(value = "/staff/contract/get")
    public CommonResult selectOne(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.selectById(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffContract 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-合同管理",operType = "修改",operDesc = "修改单条合同信息")
    //@RequiresPermissions({"contract:update"})
    @PutMapping(value = "/staff/contract/update")
    public CommonResult update(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.updateByPkId(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 发起续签合同页面
     *
     * @param tStaffContract 实体对象
     */
    @PostMapping(value = "/staff/contract/renewalPage")
    public CommonResult renewalPage(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.renewalPage(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 发起续签合同
     *
     * @param tStaffContract 实体对象
     */
    @ApiOperation(value = "发起续签合同-已完成", notes = "发起续签合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkContrctId",value = "合同信息主键",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "contractAttribute",value = "合同属性",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "contractQx",value = "合同期限",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "startTime",value = "合同开始日期",required = true,dataType = "date-time",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "endTime",value = "合同结束日期",required = true,dataType = "date-time",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "signTime",value = "合同签订日期",required = true,dataType = "date-time",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "fkContractCompany",value = "合同公司ID",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "fkContractType",value = "合同类型ID",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "fkStaffId",value = "员工关联主键id",required = true,dataType = "string",paramType = "body",example = "1"),
    })
    @PostMapping(value = "/staff/contract/renewal")
    public CommonResult renewal(@RequestBody TStaffContract tStaffContract) throws FebsException {
        try {
            return tStaffContractService.renewal(tStaffContract);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}
