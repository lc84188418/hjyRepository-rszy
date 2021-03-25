package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.ParamStatistics;
import com.hjy.cloud.t_kq.entity.TKqClock;
import com.hjy.cloud.t_kq.service.TKqClockService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
    @ApiOperation(value = "新增页面",
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
    @ApiOperation(value = "上下班打卡",
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
     * 个人打卡统计
     *
     * @param param json参数
     * @return 所有数据
     */
    @ApiOperation(value = "个人打卡统计", notes = "参数:查询条件(按时间，周/月)")
    @PostMapping(value = "/kq/clock/statistics/user")
    public CommonResult statisticsUser(@ApiParam(name = "月份/周", required = true) @RequestBody ParamStatistics param, HttpServletRequest request) throws FebsException {
        try {
            return tKqClockService.statisticsUser(param,request);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 计算周次
     *
     * @param param json参数
     * @return 所有数据
     */
    /**
     * 通过主键查询单条数据
     *
     * @param tKqClock 实体对象
     */
    @ApiOperation(value = "获取单个数据详情", notes = "参数:实体主键pkClockId")
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
    @ApiOperation(value = "修改", notes = "参数:实体数据")
    @PutMapping(value = "/kq/clock/update")
    public CommonResult update(@ApiParam(name = "参数一般比添加接口多一主键pkClockId", required = true) @RequestBody TKqClock tKqClock) throws FebsException {
        try {
            return tKqClockService.updateByPkId(tKqClock);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
