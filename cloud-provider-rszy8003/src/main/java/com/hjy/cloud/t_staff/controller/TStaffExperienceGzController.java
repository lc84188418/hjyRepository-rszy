package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffExperienceGz;
import com.hjy.cloud.t_staff.service.TStaffExperienceGzService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffExperienceGz)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
@Api(tags = "员工管理-工作经历-控制层")
@Slf4j
@RestController
public class TStaffExperienceGzController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffExperienceGzService tStaffExperienceGzService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/experienceGz/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffExperienceGzService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffExperienceGz 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/experienceGz/add")
    public CommonResult insert(@RequestBody TStaffExperienceGz tStaffExperienceGz) throws FebsException {
        try {
            return tStaffExperienceGzService.insert(tStaffExperienceGz);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffExperienceGz 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/experienceGz/del")
    public CommonResult delete(@RequestBody TStaffExperienceGz tStaffExperienceGz) throws FebsException {
        try {
            return tStaffExperienceGzService.delete(tStaffExperienceGz);
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
    @PostMapping(value = "/staff/experienceGz/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffExperienceGzService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffExperienceGz 实体对象
     */
    @PostMapping(value = "/staff/experienceGz/get")
    public CommonResult selectOne(@RequestBody TStaffExperienceGz tStaffExperienceGz) throws FebsException {
        try {
            return tStaffExperienceGzService.selectById(tStaffExperienceGz);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffExperienceGz 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/experienceGz/update")
    public CommonResult update(@RequestBody TStaffExperienceGz tStaffExperienceGz) throws FebsException {
        try {
            return tStaffExperienceGzService.updateByPkId(tStaffExperienceGz);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
