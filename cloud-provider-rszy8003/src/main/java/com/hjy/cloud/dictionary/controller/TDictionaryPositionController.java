package com.hjy.cloud.dictionary.controller;


import com.hjy.cloud.dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.dictionary.service.TDictionaryPositionService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDictionaryPosition)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
@RestController
public class TDictionaryPositionController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryPositionService tDictionaryPositionService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/position/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryPositionService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryPosition 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/position/add")
    public CommonResult insert(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.insert(tDictionaryPosition);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryPosition 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/position/del")
    public CommonResult delete(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.delete(tDictionaryPosition);
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
    @PostMapping(value = "/dictionary/position/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryPositionService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryPosition 实体对象
     */
    @PostMapping(value = "/dictionary/position/get")
    public CommonResult selectOne(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.selectById(tDictionaryPosition);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryPosition 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/position/update")
    public CommonResult update(@RequestBody TDictionaryPosition tDictionaryPosition) throws FebsException {
        try {
            return tDictionaryPositionService.updateByPkId(tDictionaryPosition);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
