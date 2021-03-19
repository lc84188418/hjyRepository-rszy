package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
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
    @ApiOperation(value = "新增页面", notes = "参数:")
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
    @ApiOperation(value = "新增", notes = "参数:打卡详细地址、若要判断外勤计算距离后传入onIsWq或offIsWq，1代表外勤")
    @PostMapping(value = "/kq/clock/add")
    public CommonResult insert(@ApiParam(name = "Json参数", required = true) @RequestBody TKqClock tKqClock) throws FebsException {
        try {
            return tKqClockService.insert(tKqClock);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqClock 实体对象
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "参数:实体主键pkClockId")
    @DeleteMapping(value = "/kq/clock/del")
    public CommonResult delete(@ApiParam(name = "传入实体主键", required = true) @RequestBody TKqClock tKqClock) throws FebsException {
        try {
            return tKqClockService.delete(tKqClock);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @ApiOperation(value = "分页+查询", notes = "参数:分页参数(pageNum、pageSize)+查询条件()")
    @PostMapping(value = "/kq/clock/list")
    public CommonResult selectAll(@ApiParam(name = "分页参数+查询条件", required = true) @RequestBody String param) throws FebsException {
        try {
            return tKqClockService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

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
