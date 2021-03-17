package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqJb;
import com.hjy.cloud.t_kq.service.TKqJbService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TKqJb)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:51:00
 */
@RestController
public class TKqJbController {
    /**
     * 服务对象
     */
    @Resource
    private TKqJbService tKqJbService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/tKqJb/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqJbService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tKqJb 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/tKqJb/add")
    public CommonResult insert(@RequestBody TKqJb tKqJb) throws FebsException {
        try {
            return tKqJbService.insert(tKqJb);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqJb 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/tKqJb/del")
    public CommonResult delete(@RequestBody TKqJb tKqJb) throws FebsException {
        try {
            return tKqJbService.delete(tKqJb);
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
    @PostMapping(value = "/tKqJb/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqJbService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tKqJb 实体对象
     */
    @PostMapping(value = "/tKqJb/get")
    public CommonResult selectOne(@RequestBody TKqJb tKqJb) throws FebsException {
        try {
            return tKqJbService.selectById(tKqJb);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tKqJb 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/tKqJb/update")
    public CommonResult update(@RequestBody TKqJb tKqJb) throws FebsException {
        try {
            return tKqJbService.updateByPkId(tKqJb);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
