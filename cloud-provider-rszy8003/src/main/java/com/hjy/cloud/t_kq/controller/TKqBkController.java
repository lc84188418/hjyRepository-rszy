package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqBk;
import com.hjy.cloud.t_kq.service.TKqBkService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TKqBk)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@Api(tags = "补卡控制层")
@Slf4j
@RestController
public class TKqBkController {
    /**
     * 服务对象
     */
    @Resource
    private TKqBkService tKqBkService;

    /**
     * 1 跳转到新增页面
     * 已上传
     */
    @GetMapping(value = "/kq/bk/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqBkService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     * 已上传
     * @param param 实体对象
     * @return 新增结果
     */
    //@RequiresPermissions({"bk:add"})
    @PostMapping(value = "/kq/bk/add")
    public CommonResult insert(@RequestBody String param) throws FebsException {
        try {
            return tKqBkService.insert(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqBk 实体对象
     * @return 删除结果
     */
    //@RequiresPermissions({"bk:del"})
    @DeleteMapping(value = "/kq/bk/del")
    public CommonResult delete(@RequestBody TKqBk tKqBk) throws FebsException {
        try {
            return tKqBkService.delete(tKqBk);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param param json参数
     * @return 所有数据
     */
    //@RequiresPermissions({"bk:view"})
    @PostMapping(value = "/kq/bk/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqBkService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tKqBk 实体对象
     */
    //@RequiresPermissions({"bk:get"})
    @PostMapping(value = "/kq/bk/get")
    public CommonResult selectOne(@RequestBody TKqBk tKqBk) throws FebsException {
        try {
            return tKqBkService.selectById(tKqBk);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param param 实体对象
     * @return 修改结果
     */
    //@RequiresPermissions({"bk:update"})
    @PutMapping(value = "/kq/bk/update")
    public CommonResult update(@RequestBody String param) throws FebsException {
        try {
            return tKqBkService.updateByPkId(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

}
