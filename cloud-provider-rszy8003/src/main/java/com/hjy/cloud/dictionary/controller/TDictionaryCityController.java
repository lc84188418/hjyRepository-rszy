package com.hjy.cloud.dictionary.controller;


import com.hjy.cloud.dictionary.entity.TDictionaryCity;
import com.hjy.cloud.dictionary.service.TDictionaryCityService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDictionaryCity)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
@RestController
public class TDictionaryCityController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryCityService tDictionaryCityService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/city/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryCityService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryCity 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/city/add")
    public CommonResult insert(@RequestBody TDictionaryCity tDictionaryCity) throws FebsException {
        try {
            return tDictionaryCityService.insert(tDictionaryCity);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryCity 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/city/del")
    public CommonResult delete(@RequestBody TDictionaryCity tDictionaryCity) throws FebsException {
        try {
            return tDictionaryCityService.delete(tDictionaryCity);
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
    @PostMapping(value = "/dictionary/city/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryCityService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryCity 实体对象
     */
    @PostMapping(value = "/dictionary/city/get")
    public CommonResult selectOne(@RequestBody TDictionaryCity tDictionaryCity) throws FebsException {
        try {
            return tDictionaryCityService.selectById(tDictionaryCity);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryCity 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/city/update")
    public CommonResult update(@RequestBody TDictionaryCity tDictionaryCity) throws FebsException {
        try {
            return tDictionaryCityService.updateByPkId(tDictionaryCity);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
