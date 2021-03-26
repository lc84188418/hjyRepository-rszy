package com.hjy.cloud.t_staff.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffQuit;
import com.hjy.cloud.t_staff.service.TStaffQuitService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TStaffQuit)表控制层
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@Api(tags = "员工管理-离职-控制层")
@Slf4j
@RestController
public class TStaffQuitController {
    /**
     * 服务对象
     */
    @Resource
    private TStaffQuitService tStaffQuitService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/staff/quit/addPage")
    public CommonResult insertPage(HttpServletRequest request) throws FebsException {
        try {
            return tStaffQuitService.insertPage(request);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping(value = "/staff/quit/add")
    public CommonResult insert(@RequestBody String param, HttpServletRequest request) throws FebsException {
        try {
            return tStaffQuitService.insert(request,param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffQuit 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/staff/quit/del")
    public CommonResult delete(@RequestBody TStaffQuit tStaffQuit) throws FebsException {
        try {
            return tStaffQuitService.delete(tStaffQuit);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 分页查询所有数据
     * 管理员
     * @param param json参数
     * @return 所有数据
     */
    @PostMapping(value = "/staff/quit/adminList")
    public CommonResult adminList(@RequestBody String param) throws FebsException {
        try {
            return tStaffQuitService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tStaffQuit 实体对象
     */
    @PostMapping(value = "/staff/quit/get")
    public CommonResult selectOne(@RequestBody TStaffQuit tStaffQuit) throws FebsException {
        try {
            return tStaffQuitService.selectById(tStaffQuit);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffQuit 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/staff/quit/update")
    public CommonResult update(@RequestBody TStaffQuit tStaffQuit) throws FebsException {
        try {
            return tStaffQuitService.updateByPkId(tStaffQuit);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
