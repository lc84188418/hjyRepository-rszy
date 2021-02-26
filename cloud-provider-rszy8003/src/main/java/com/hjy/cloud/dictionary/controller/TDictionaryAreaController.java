package com.hjy.cloud.dictionary.controller;


import com.hjy.cloud.dictionary.entity.TDictionaryArea;
import com.hjy.cloud.dictionary.service.TDictionaryAreaService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDictionaryArea)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:50
 */
@RestController
public class TDictionaryAreaController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryAreaService tDictionaryAreaService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/area/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryAreaService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryArea 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/area/add")
    public CommonResult insert(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.insert(tDictionaryArea);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryArea 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/area/del")
    public CommonResult delete(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.delete(tDictionaryArea);
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
    @PostMapping(value = "/dictionary/area/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryAreaService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryArea 实体对象
     */
    @PostMapping(value = "/dictionary/area/get")
    public CommonResult selectOne(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.selectById(tDictionaryArea);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryArea 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/area/update")
    public CommonResult update(@RequestBody TDictionaryArea tDictionaryArea) throws FebsException {
        try {
            return tDictionaryAreaService.updateByPkId(tDictionaryArea);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
