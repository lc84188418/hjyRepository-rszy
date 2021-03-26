package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.ParamStatistics;
import com.hjy.cloud.t_kq.entity.TKqClock;
import com.hjy.cloud.t_kq.service.TKqClockService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * (TKqClock)表控制层
 *
 * @author makejava
 * @since 2021-03-19 18:00:15
 */
@Api(tags = "打卡控制层")
@Slf4j
@RestController
public class TKqClockController {
    /**
     * 服务对象
     */
    @Resource
    private TKqClockService tKqClockService;

    /**
     * 1 跳转到新增页面
     */
    @ApiOperation(value = "打卡前页面-已完成",
            notes = "入参:无\n" +
                    "回参：\nisKq：true,为true代表需要考勤\n" +
            "group：员工所属考勤组基本信息\n" +
            "workAddress：考勤组设置的办公地信息\n" +
            "todayBc：今日班次信息\n")
    @GetMapping(value = "/kq/clock/addPage")
    public CommonResult insertPage(HttpServletRequest request) throws FebsException {
        try {
            return tKqClockService.insertPage(request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tKqClock 实体对象
     * @return 新增结果
     */
    @ApiOperation(value = "上下班打卡-已完成",
            notes = "入参:\n上班时:onClockAddress/onIsWq/isDkr/fkGroupId\n" + "下班时:offClockAddress/offIsWq\n" +
                    "回参:\nclock:上下班打卡后的信息")
    @PostMapping(value = "/kq/clock/add")
    public CommonResult insert(@ApiParam(name = "打卡实体", required = true) @RequestBody TKqClock tKqClock,HttpServletRequest request) throws FebsException {
        try {
            return tKqClockService.insert(tKqClock,request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 个人打卡汇总统计
     *
     * @param param json参数
     * @return 所有数据
     */
    @ApiOperation(value = "个人汇总统计-已完成", notes = "入参：当weekOrMonth=月时，传入monthDate，当weekOrMonth=周 时，传入weekDate\n{\n" +
            "    \"weekOrMonth\": \"月\",\n" +
            "    \"weekDate\":\"2021.03.21-2021.03.27\",\n" +
            "    \"monthDate\":\"2021-03\"\n" +
            "}")
    @PostMapping(value = "/kq/clock/user/statistics/collect")
    public CommonResult userStatisticsCollect(@ApiParam(name = "月份/周", required = true) @RequestBody ParamStatistics param, HttpServletRequest request) throws FebsException {
        try {
            return tKqClockService.statisticsUser(param,request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 个人打卡每日记录统计
     * @return 所有数据
     */
    @ApiOperation(value = "个人每日记录-已完成", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monthDate",required = true,dataType = "String",paramType = "form",example = "\"2021-03\""),
            @ApiImplicitParam(name = "todayDate",required = false,dataType = "String",paramType = "form",example = "\"2021-03-24\"")
    })
    @PostMapping(value = "/kq/clock/user/statistics/everyDay")
    public CommonResult userStatisticsEveryDay(@ApiParam(name = "某一天数据", required = true) @RequestBody ParamStatistics param, HttpServletRequest request) throws FebsException {
        try {
            return tKqClockService.userStatisticsEveryDay(param,request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 计算周次
     * @param paramStatistics
     * @return 周次时间段
     */
    @ApiOperation(value = "计算周次的时间段-已完成",
            notes = "回参:周次时间段\n{\n" +
                    "\t\"code\": 200,\n" +
                    "\t\"status\": \"success\",\n" +
                    "\t\"msg\": \"获取数据成功\",\n" +
                    "\t\"data\": \"2021.03.28-2021.04.03\"\n" +
                    "}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastOrNext",required = false,dataType = "int",paramType = "form",example = "1"),
            @ApiImplicitParam(name = "weekDate",required = false,dataType = "String",paramType = "form",example = "\"2021.03.21-2021.03.27\"")
    })
    @PostMapping(value = "/kq/clock/getWeekSlot")
    public CommonResult getWeekSlot(@ApiParam(name = "统计传参实体", required = true) @RequestBody ParamStatistics paramStatistics) throws FebsException {
        try {
            return tKqClockService.getWeekSlot(paramStatistics);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 管理员查询所有打卡信息
     * @param tKqClock 实体对象
     */
    @ApiOperation(value = "查询所有打卡信息-已完成", notes = "已下方查询条件所有打卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffName",required = false,dataType = "String",paramType = "form",example = "马"),
            @ApiImplicitParam(name = "todayDate",required = false,dataType = "String",paramType = "form",example = "2021-03-18"),
            @ApiImplicitParam(name = "deptName",required = false,dataType = "String",paramType = "form",example = "研发部"),
            @ApiImplicitParam(name = "groupName",required = false,dataType = "String",paramType = "form",example = "研发考勤组"),
            @ApiImplicitParam(name = "pageNum",required = false,dataType = "String",paramType = "form",example = "1"),
            @ApiImplicitParam(name = "pageSize",required = false,dataType = "String",paramType = "form",example = "10")
    })
    @PostMapping(value = "/kq/clock/admin/list")
    public CommonResult adminList(@ApiParam(name = "实体参数", required = true) @RequestBody String tKqClock) throws FebsException {
        try {
            return tKqClockService.adminList(tKqClock);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 获取单条打卡信息
     *
     * @param tKqClock 实体对象
     */
    @ApiOperation(value = "获取单个数据详情-已完成", notes = "通过主键查询单条打卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkClockId",required = true,dataType = "String",paramType = "form",example = "fd6850ceea22492d9fc4270f22739192")
    })
    @PostMapping(value = "/kq/clock/get")
    public CommonResult selectOne(@ApiParam(name = "传入实体主键即可", required = true) @RequestBody TKqClock tKqClock) throws FebsException {
        try {
            return tKqClockService.selectById(tKqClock);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tKqClock 实体对象
     * @return 修改结果
     */
    @ApiOperation(value = "管理员修改打卡信息-未完成", notes = "只限于管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkClockId",required = true,dataType = "String",paramType = "form",example = "fd6850ceea22492d9fc4270f22739192"),
            @ApiImplicitParam(name = "onDutyTime",required = false,dataType = "Date",paramType = "form",example = "2021-03-23 11:45:02"),
            @ApiImplicitParam(name = "onClockAddress",required = false,dataType = "String",paramType = "form",example = "四川省成都市武侯区交子大道333号中海国际中心E座"),
            @ApiImplicitParam(name = "onIsWq",required = false,dataType = "Integer",paramType = "form",example = "0"),
            @ApiImplicitParam(name = "offDutyTime",required = false,dataType = "Date",paramType = "form",example = "2021-03-23 11:45:02"),
            @ApiImplicitParam(name = "offClockAddress",required = false,dataType = "String",paramType = "form",example = "四川省成都市武侯区交子大道333号中海国际中心E座"),
            @ApiImplicitParam(name = "offIsWq",required = false,dataType = "Integer",paramType = "form",example = "0")
    })
    @PutMapping(value = "/kq/clock/update")
    public CommonResult update(@ApiParam(name = "某些可修改项", required = true) @RequestBody TKqClock tKqClock) throws FebsException {
        try {
            return tKqClockService.updateByPkId(tKqClock);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
