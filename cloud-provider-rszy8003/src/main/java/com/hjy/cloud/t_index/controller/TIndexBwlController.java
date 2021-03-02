package com.hjy.cloud.t_index.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.t_index.service.TIndexBwlService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TIndexBwl)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:50:55
 */
@RestController
public class TIndexBwlController {
    /**
     * 服务对象
     */
    @Resource
    private TIndexBwlService tIndexBwlService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/index/bwl/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tIndexBwlService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tIndexBwl 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/index/bwl/add")
    public CommonResult insert(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.insert(tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tIndexBwl 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/index/bwl/del")
    public CommonResult delete(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.delete(tIndexBwl);
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
    @PostMapping(value = "/index/bwl/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tIndexBwlService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tIndexBwl 实体对象
     */
    @PostMapping(value = "/index/bwl/get")
    public CommonResult selectOne(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.selectById(tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tIndexBwl 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/index/bwl/update")
    public CommonResult update(@RequestBody TIndexBwl tIndexBwl) throws FebsException {
        try {
            return tIndexBwlService.updateByPkId(tIndexBwl);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
