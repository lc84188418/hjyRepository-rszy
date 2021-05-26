package com.hjy.cloud.t_apv.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_apv.entity.TApvGroup;
import com.hjy.cloud.t_apv.service.TApvGroupService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApvGroup)表控制层
 *
 * @author makejava
 * @since 2021-02-26 14:50:50
 */
@Api(tags = "审批分组控制层")
@Slf4j
@RestController
public class TApvGroupController {
    /**
     * 服务对象
     */
    @Resource
    private TApvGroupService tApvGroupService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/apv/group/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tApvGroupService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tApvGroup 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批分组",operType = "添加",operDesc = "新增审批分组")
    //@RequiresPermissions({"apvGroup:add"})
    @PostMapping(value = "/apv/group/add")
    public CommonResult insert(@RequestBody TApvGroup tApvGroup) throws FebsException {
        try {
            return tApvGroupService.insert(tApvGroup);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvGroup 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批分组",operType = "删除",operDesc = "删除审批分组")
    //@RequiresPermissions({"apvGroup:del"})
    @DeleteMapping(value = "/apv/group/del")
    public CommonResult delete(@RequestBody TApvGroup tApvGroup) throws FebsException {
        try {
            return tApvGroupService.delete(tApvGroup);
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
    @OperLog(operModul = "审批管理-审批设置-审批分组",operType = "查看",operDesc = "查看审批分组列表")
    //@RequiresPermissions({"apvGroup:view"})
    @PostMapping(value = "/apv/group/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tApvGroupService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tApvGroup 实体对象
     */
    @OperLog(operModul = "审批管理-审批设置-审批分组",operType = "查看",operDesc = "查看单个审批分组详情")
    //@RequiresPermissions({"apvGroup:get"})
    @PostMapping(value = "/apv/group/get")
    public CommonResult selectOne(@RequestBody TApvGroup tApvGroup) throws FebsException {
        try {
            return tApvGroupService.selectById(tApvGroup);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvGroup 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批分组",operType = "修改",operDesc = "修改审批分组信息")
    //@RequiresPermissions({"apvGroup:update"})
    @PutMapping(value = "/apv/group/update")
    public CommonResult update(@RequestBody TApvGroup tApvGroup) throws FebsException {
        try {
            return tApvGroupService.updateByPkId(tApvGroup);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
