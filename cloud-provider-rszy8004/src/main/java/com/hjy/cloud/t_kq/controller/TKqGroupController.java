package com.hjy.cloud.t_kq.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_kq.entity.TKqGroup;
import com.hjy.cloud.t_kq.service.TKqGroupService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TKqGroup)表控制层
 *
 * @author makejava
 * @since 2021-03-16 14:50:59
 */
@Api(tags = "考勤分组控制层")
@Slf4j
@RestController
public class TKqGroupController {
    /**
     * 服务对象
     */
    @Resource
    private TKqGroupService tKqGroupService;
    @Value("${server.port}")
    private String serverPort;
    @GetMapping(value = "/provider/rszy/group")
    public CommonResult providerRszyGroup() throws FebsException {
        try {
            return new CommonResult(200, "success", "这里是提供者8003-group", serverPort);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    /**
     * 1 跳转到新增页面
     * 已上传
     */
    @GetMapping(value = "/kq/group/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tKqGroupService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     * 已上传
     * @return 新增结果
     */
    @PostMapping(value = "/kq/group/add")
    public CommonResult insert(@RequestBody String param) throws FebsException {
        try {
            return tKqGroupService.insert(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     * 已上传
     * @param tKqGroup 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/kq/group/del")
    public CommonResult delete(@RequestBody TKqGroup tKqGroup) throws FebsException {
        try {
            return tKqGroupService.delete(tKqGroup);
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
    @PostMapping(value = "/kq/group/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tKqGroupService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     * 已上传
     * @param tKqGroup 实体对象
     */
    @PostMapping(value = "/kq/group/get")
    public CommonResult selectOne(@RequestBody TKqGroup tKqGroup) throws FebsException {
        try {
            return tKqGroupService.selectById(tKqGroup);
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
    @PutMapping(value = "/kq/group/update")
    public CommonResult update(@RequestBody String param) throws FebsException {
        try {
            return tKqGroupService.updateByPkId(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
