package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqJb;
import com.hjy.cloud.t_kq.service.TKqJbService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TKqJb)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:51:00
 */
@Api(tags = "加班规则控制层")
@Slf4j
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
    @GetMapping(value = "/kq/jb/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqJbService.insertPage();
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
    //@RequiresPermissions({"jbRule:add"})
    @PostMapping(value = "/kq/jb/add")
    public CommonResult insert(@RequestBody String param) throws FebsException {
        try {
            return tKqJbService.insert(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     * 已上传
     * @param tKqJb 实体对象
     * @return 删除结果
     */
    //@RequiresPermissions({"jbRule:del"})
    @DeleteMapping(value = "/kq/jb/del")
    public CommonResult delete(@RequestBody TKqJb tKqJb) throws FebsException {
        try {
            return tKqJbService.delete(tKqJb);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     * 已上传
     * @param param json参数
     * @return 所有数据
     */
    //@RequiresPermissions({"jbRule:view"})
    @PostMapping(value = "/kq/jb/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqJbService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     * 已上传
     * @param tKqJb 实体对象
     */
    //@RequiresPermissions({"jbRule:get"})
    @PostMapping(value = "/kq/jb/get")
    public CommonResult selectOne(@RequestBody TKqJb tKqJb) throws FebsException {
        try {
            return tKqJbService.selectById(tKqJb);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     * 已上传
     * @param param 实体对象
     * @return 修改结果
     */
    //@RequiresPermissions({"jbRule:update"})
    @PutMapping(value = "/kq/jb/update")
    public CommonResult update(@RequestBody String param) throws FebsException {
        try {
            return tKqJbService.updateByPkId(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
