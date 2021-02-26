package com.hjy.cloud.dictionary.controller;


import com.hjy.cloud.dictionary.entity.TDictionaryProvince;
import com.hjy.cloud.dictionary.service.TDictionaryProvinceService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDictionaryProvince)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
@RestController
public class TDictionaryProvinceController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryProvinceService tDictionaryProvinceService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/province/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryProvinceService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryProvince 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/province/add")
    public CommonResult insert(@RequestBody TDictionaryProvince tDictionaryProvince) throws FebsException {
        try {
            return tDictionaryProvinceService.insert(tDictionaryProvince);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryProvince 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/province/del")
    public CommonResult delete(@RequestBody TDictionaryProvince tDictionaryProvince) throws FebsException {
        try {
            return tDictionaryProvinceService.delete(tDictionaryProvince);
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
    @PostMapping(value = "/dictionary/province/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryProvinceService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryProvince 实体对象
     */
    @PostMapping(value = "/dictionary/province/get")
    public CommonResult selectOne(@RequestBody TDictionaryProvince tDictionaryProvince) throws FebsException {
        try {
            return tDictionaryProvinceService.selectById(tDictionaryProvince);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryProvince 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/province/update")
    public CommonResult update(@RequestBody TDictionaryProvince tDictionaryProvince) throws FebsException {
        try {
            return tDictionaryProvinceService.updateByPkId(tDictionaryProvince);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
