package com.hjy.cloud.t_log.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_log.entity.TLogException;
import com.hjy.cloud.t_log.service.TLogExceptionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TLogException)表控制层
 *
 * @author makejava
 * @since 2021-03-02 10:02:00
 */
@Api(tags = "异常日志控制层")
@Slf4j
@RestController
public class TLogExceptionController {
    /**
     * 服务对象
     */
    @Resource
    private TLogExceptionService tLogExceptionService;

    /**
     * 删除数据
     *
     * @param tLogException 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "日志管理-异常日志",operType = "删除",operDesc = "删除异常日志")
    @RequiresPermissions({"logException:del"})
    @DeleteMapping(value = "/log/exception/del")
    public CommonResult delete(@RequestBody TLogException tLogException) throws FebsException {
        try {
            return tLogExceptionService.delete(tLogException);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    @OperLog(operModul = "日志管理-异常日志",operType = "查看",operDesc = "查看异常日志")
    @RequiresPermissions({"logException:view"})
    @PostMapping(value = "/log/exception/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tLogExceptionService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tLogException 实体对象
     */
    @OperLog(operModul = "日志管理-异常日志",operType = "查看",operDesc = "查看单个异常日志")
    @RequiresPermissions({"logException:get"})
    @PostMapping(value = "/log/exception/get")
    public CommonResult selectOne(@RequestBody TLogException tLogException) throws FebsException {
        try {
            return tLogExceptionService.selectById(tLogException);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }



}
