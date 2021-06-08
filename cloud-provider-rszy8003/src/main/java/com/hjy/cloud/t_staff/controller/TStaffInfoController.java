package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * (TStaffInfo)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:06:48
 */
@Api(tags = "员工管理-员工档案-控制层")
@Slf4j
@RestController
public class TStaffInfoController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffInfoService tStaffInfoService;

    @ApiOperation(value = "直接添加员工档案-已完成", notes = "直接添加员工信息，并会自动创建系统账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffName",value = "姓名",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "staffSex",value = "性别",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "idCard",value = "证件号",required = true,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "staffAge",value = "年龄",required = false,dataType = "int",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "fkDeptId",value = "部门id",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "fkPositionId",value = "职位id",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "fkWorkaddressId",value = "工作地id",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "entryTime",value = "入职时间",required = false,dataType = "date-time",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "fkHtlxId",value = "合同类型id",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "recruitWay",value = "招聘形式",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "syqdqTime",value = "试用期到期日",required = false,dataType = "date-time",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "idType",value = "证件类型",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "staffEmail",value = "员工邮箱",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "staffTel",value = "员工电话",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "nativePlace",value = "籍贯",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "birthday",value = "出生日期,直接输入就行",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "fkNationId",value = "民族id",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "hkszd",value = "户口所在地",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "politivalOutlook",value = "政治面貌",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "currentAddress",value = "现住地址",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "isMarry",value = "婚姻状况",required = false,dataType = "int",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "fkEducationId",value = "学历id",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "zgxlbyyx",value = "最高学历毕业院校",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "wyDj",value = "外语等级",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "jsjDj",value = "计算机等级",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "major",value = "所学专业",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "bankName",value = "工资开银行",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "bankId",value = "工资卡银行账户",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "branchBank",value = "所属支行",required = false,dataType = "string",paramType = "body",example = "10"),
            @ApiImplicitParam(name = "syr",value = "所有人",required = false,dataType = "string",paramType = "body",example = "10"),
    })
    @OperLog(operModul = "人员管理-名单管理",operType = "添加",operDesc = "直接添加员工信息")
    //@RequiresPermissions({"staffInfo:add"})
    @PostMapping("/staff/info/add")
    public CommonResult add(@ApiIgnore() @RequestBody TStaffInfo tStaffInfo) throws FebsException{
        try {
            return tStaffInfoService.insert(tStaffInfo);
        } catch (Exception e) {
            String message = "数据添加失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffInfo 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "人员管理-名单管理",operType = "删除",operDesc = "删除入职员工信息记录")
    //@RequiresPermissions({"staffInfo:del"})
    @DeleteMapping(value = "/staff/info/del")
    public CommonResult delete(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.delete(tStaffInfo);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     * 管理员
     *
     * @param param json参数
     * @return 所有数据
     */
    @ApiOperation(value = "查看-已完成", notes = "查看单个入职员工的档案信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "条数",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "staffStatus",value = "员工状态,0离职1入职2转正",required = false,dataType = "int",paramType = "body",example = "1"),
    })
    @OperLog(operModul = "人员管理-名单管理",operType = "查看",operDesc = "查看入职员工信息列表")
    //@RequiresPermissions({"staffInfo:adminView"})
    @PostMapping(value = "/staff/info/adminList")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffInfoService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffInfo 实体对象
     */
    @ApiOperation(value = "查看-已完成", notes = "查看单个入职员工的档案信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkStaffId",value = "员工主键id",required = true,dataType = "string",paramType = "body",example = "1"),
    })
    @OperLog(operModul = "人员管理-名单管理",operType = "查看",operDesc = "查看单个入职员工信息详情")
    //@RequiresPermissions({"staffInfo:get"})
    @PostMapping(value = "/staff/info/get")
    public CommonResult selectOne(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.selectById(tStaffInfo);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffInfo 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "人员管理-名单管理",operType = "修改",operDesc = "修改入职员工信息")
    //@RequiresPermissions({"staffInfo:update"})
    @PutMapping(value = "/staff/info/update")
    public CommonResult update(@RequestBody TStaffInfo tStaffInfo) throws FebsException {
        try {
            return tStaffInfoService.updateByPkId(tStaffInfo);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
