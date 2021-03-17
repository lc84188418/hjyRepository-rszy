package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqBk;
import com.hjy.cloud.t_kq.service.TKqBkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TKqBk)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@RestController
public class TKqBkController {
    /**
     * 服务对象
     */
    @Resource
    private TKqBkService tKqBkService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/tKqBk/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqBkService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tKqBk 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/tKqBk/add")
    public CommonResult insert(@RequestBody TKqBk tKqBk) throws FebsException {
        try {
            return tKqBkService.insert(tKqBk);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqBk 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/tKqBk/del")
    public CommonResult delete(@RequestBody TKqBk tKqBk) throws FebsException {
        try {
            return tKqBkService.delete(tKqBk);
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
    @PostMapping(value = "/tKqBk/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqBkService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tKqBk 实体对象
     */
    @PostMapping(value = "/tKqBk/get")
    public CommonResult selectOne(@RequestBody TKqBk tKqBk) throws FebsException {
        try {
            return tKqBkService.selectById(tKqBk);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tKqBk 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/tKqBk/update")
    public CommonResult update(@RequestBody TKqBk tKqBk) throws FebsException {
        try {
            return tKqBkService.updateByPkId(tKqBk);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
