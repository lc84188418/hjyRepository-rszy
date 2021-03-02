package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffExperienceJy;
import com.hjy.cloud.t_staff.service.TStaffExperienceJyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffExperienceJy)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:15
 */
@RestController
public class TStaffExperienceJyController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffExperienceJyService tStaffExperienceJyService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/experienceJy/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tStaffExperienceJyService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tStaffExperienceJy 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/staff/experienceJy/add")
    public CommonResult insert(@RequestBody TStaffExperienceJy tStaffExperienceJy) throws FebsException {
        try {
            return tStaffExperienceJyService.insert(tStaffExperienceJy);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffExperienceJy 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/experienceJy/del")
    public CommonResult delete(@RequestBody TStaffExperienceJy tStaffExperienceJy) throws FebsException {
        try {
            return tStaffExperienceJyService.delete(tStaffExperienceJy);
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
    @PostMapping(value = "/staff/experienceJy/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tStaffExperienceJyService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffExperienceJy 实体对象
     */
    @PostMapping(value = "/staff/experienceJy/get")
    public CommonResult selectOne(@RequestBody TStaffExperienceJy tStaffExperienceJy) throws FebsException {
        try {
            return tStaffExperienceJyService.selectById(tStaffExperienceJy);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffExperienceJy 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/experienceJy/update")
    public CommonResult update(@RequestBody TStaffExperienceJy tStaffExperienceJy) throws FebsException {
        try {
            return tStaffExperienceJyService.updateByPkId(tStaffExperienceJy);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
