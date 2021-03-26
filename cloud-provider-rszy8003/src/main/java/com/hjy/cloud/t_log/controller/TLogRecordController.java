package com.hjy.cloud.t_log.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_log.entity.TLogRecord;
import com.hjy.cloud.t_log.service.TLogRecordService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TLogRecord)表控制层
 *
 * @author makejava
 * @since 2021-03-02 10:02:01
 */
@Api(tags = "操作日志控制层")
@Slf4j
@RestController
public class TLogRecordController {
    /**
     * 服务对象
     */
    @Resource
    private TLogRecordService tLogRecordService;

    /**
     * 删除数据
     *
     * @param tLogRecord 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "日志管理-操作日志",operType = "删除",operDesc = "删除日志信息")
    @RequiresPermissions({"logRecord:del"})
    @DeleteMapping(value = "/log/record/del")
    public CommonResult delete(@RequestBody TLogRecord tLogRecord) throws FebsException {
        try {
            return tLogRecordService.delete(tLogRecord);
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
    @OperLog(operModul = "日志管理-操作日志",operType = "查看",operDesc = "查看日志信息列表")
    @RequiresPermissions({"logRecord:view"})
    @PostMapping(value = "/log/record/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tLogRecordService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tLogRecord 实体对象
     */
    @OperLog(operModul = "日志管理-操作日志",operType = "查看",operDesc = "查看单个日志信息")
    @RequiresPermissions({"logRecord:get"})
    @PostMapping(value = "/log/record/get")
    public CommonResult selectOne(@RequestBody TLogRecord tLogRecord) throws FebsException {
        try {
            return tLogRecordService.selectById(tLogRecord);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

}
