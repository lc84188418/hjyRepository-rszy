package com.hjy.cloud.t_log.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_log.entity.TLogRecord;
import com.hjy.cloud.t_log.service.TLogRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TLogRecord)表控制层
 *
 * @author makejava
 * @since 2021-03-02 10:02:01
 */
@RestController
public class TLogRecordController {
    /**
     * 服务对象
     */
    @Resource
    private TLogRecordService tLogRecordService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/log/record/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tLogRecordService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tLogRecord 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/log/record/add")
    public CommonResult insert(@RequestBody TLogRecord tLogRecord) throws FebsException {
        try {
            return tLogRecordService.insert(tLogRecord);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tLogRecord 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/log/record/del")
    public CommonResult delete(@RequestBody TLogRecord tLogRecord) throws FebsException {
        try {
            return tLogRecordService.delete(tLogRecord);
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
    @PostMapping(value = "/log/record/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tLogRecordService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tLogRecord 实体对象
     */
    @PostMapping(value = "/log/record/get")
    public CommonResult selectOne(@RequestBody TLogRecord tLogRecord) throws FebsException {
        try {
            return tLogRecordService.selectById(tLogRecord);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tLogRecord 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/log/record/update")
    public CommonResult update(@RequestBody TLogRecord tLogRecord) throws FebsException {
        try {
            return tLogRecordService.updateByPkId(tLogRecord);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}