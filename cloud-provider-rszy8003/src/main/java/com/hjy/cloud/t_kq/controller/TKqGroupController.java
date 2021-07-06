package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqGroup;
import com.hjy.cloud.t_kq.result.KqGroupResult;
import com.hjy.cloud.t_kq.service.TKqGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TKqGroup)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@Api(tags = "考勤分组控制层")
@Slf4j
@RestController
public class TKqGroupController {
    /**
     * 服务对象
     */
    @Resource
    private TKqGroupService tKqGroupService;
    @Value("${server.port}")
    private String serverPort;
    @GetMapping(value = "/provider/rszy/group")
    public CommonResult providerRszyGroup() throws FebsException {
        try {
            return new CommonResult(200, "success", "这里是提供者8003-group", serverPort);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 1 跳转到新增页面
     * 已上传
     */
    @GetMapping(value = "/kq/group/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqGroupService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     * 已上传
     * @return 新增结果
     */
    @ApiOperation(value = "添加-已完成", notes = "新增考勤组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName",value = "考勤组名称",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "kqAddress",value = "考勤地址",required = true,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "kqRange",value = "考勤地址",required = false,dataType = "int",paramType = "body",example = "100"),
            @ApiImplicitParam(name = "groupStewards",value = "考勤组负责人",required = false,dataType = "string",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "kqType",value = "考勤类型：1固定班制3自由工时",required = true,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "typeSet",value = "自由工时的工时设置",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "isPaixiu",value = "法定节假日自动排休",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "bxdkTime",value = "必须打卡日期",required = false,dataType = "date",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "wxdkTime",value = "无需打开日期",required = false,dataType = "date",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "kqWay",value = "考勤方式，1地点打卡，2WiFi打卡",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "isPzdk",value = "拍照打卡",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "isWq",value = "是否允许外勤打卡，1代表允许，0代表不允许",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "wqApv",value = "外勤打卡需审批",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "wqRemarks",value = "外勤打卡需填写备注",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "wqPz",value = "外勤打卡需拍照",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "wqHideaddress",value = "允许员工隐藏详细地址",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "dkJgsj",value = "上班打卡后,几小时后才能打下班卡",required = false,dataType = "int",paramType = "body",example = "1"),
            @ApiImplicitParam(name = "turnOn",value = "是否启用",required = false,dataType = "int",paramType = "body",example = "1"),
    })
    //@RequiresPermissions({"group:add"})
    @PostMapping(value = "/kq/group/add")
    public CommonResult insert(@RequestBody String param) throws FebsException {
        try {
            return tKqGroupService.insert(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     * 已上传
     * @param tKqGroup 实体对象
     * @return 删除结果
     */
    //@RequiresPermissions({"group:del"})
    @DeleteMapping(value = "/kq/group/del")
    public CommonResult delete(@RequestBody TKqGroup tKqGroup) throws FebsException {
        try {
            return tKqGroupService.delete(tKqGroup);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     * 已上传
     * @param param json参数
     * @return 所有数据
     */
    //@RequiresPermissions({"group:view"})
    @PostMapping(value = "/kq/group/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqGroupService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     * 已上传
     * @param tKqGroup 实体对象
     */
    //@RequiresPermissions({"group:get"})
    @PostMapping(value = "/kq/group/get")
    public CommonResult<KqGroupResult<TKqGroup>> selectOne(@RequestBody TKqGroup tKqGroup) throws FebsException {
        try {
            return tKqGroupService.selectById(tKqGroup);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     * 已上传
     * @param param 实体对象
     * @return 修改结果
     */
    //@RequiresPermissions({"group:update"})
    @PutMapping(value = "/kq/group/update")
    public CommonResult update(@RequestBody String param) throws FebsException {
        try {
            return tKqGroupService.updateByPkId(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
