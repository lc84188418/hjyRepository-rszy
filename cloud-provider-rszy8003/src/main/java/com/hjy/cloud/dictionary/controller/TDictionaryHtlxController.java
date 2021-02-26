package com.hjy.cloud.dictionary.controller;


import com.hjy.cloud.dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.dictionary.service.TDictionaryHtlxService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDictionaryHtlx)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
@RestController
public class TDictionaryHtlxController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryHtlxService tDictionaryHtlxService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/htlx/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryHtlxService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryHtlx 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/htlx/add")
    public CommonResult insert(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.insert(tDictionaryHtlx);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryHtlx 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/htlx/del")
    public CommonResult delete(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.delete(tDictionaryHtlx);
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
    @PostMapping(value = "/dictionary/htlx/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryHtlxService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryHtlx 实体对象
     */
    @PostMapping(value = "/dictionary/htlx/get")
    public CommonResult selectOne(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.selectById(tDictionaryHtlx);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryHtlx 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/htlx/update")
    public CommonResult update(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.updateByPkId(tDictionaryHtlx);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
