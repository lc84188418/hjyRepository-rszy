package com.hjy.cloud.t_log.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_log.entity.TLogException;
import com.hjy.cloud.t_log.service.TLogExceptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TLogException)表控制层
 *
 * @author makejava
 * @since 2021-03-02 10:02:00
 */
@RestController
public class TLogExceptionController {
    /**
     * 服务对象
     */
    @Resource
    private TLogExceptionService tLogExceptionService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/log/exception/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tLogExceptionService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tLogException 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/log/exception/add")
    public CommonResult insert(@RequestBody TLogException tLogException) throws FebsException {
        try {
            return tLogExceptionService.insert(tLogException);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tLogException 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/log/exception/del")
    public CommonResult delete(@RequestBody TLogException tLogException) throws FebsException {
        try {
            return tLogExceptionService.delete(tLogException);
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
    @PostMapping(value = "/log/exception/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tLogExceptionService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tLogException 实体对象
     */
    @PostMapping(value = "/log/exception/get")
    public CommonResult selectOne(@RequestBody TLogException tLogException) throws FebsException {
        try {
            return tLogExceptionService.selectById(tLogException);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tLogException 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/log/exception/update")
    public CommonResult update(@RequestBody TLogException tLogException) throws FebsException {
        try {
            return tLogExceptionService.updateByPkId(tLogException);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
