package com.hjy.cloud.t_apv.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.service.TApvApvtypeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApvApvtype)表控制层
 *
 * @author makejava
 * @since 2021-02-26 14:50:49
 */
@Api(tags = "审批类型控制层")
@Slf4j
@RestController
public class TApvApvtypeController {
    /**
     * 服务对象
     */
    @Resource
    private TApvApvtypeService tApvApvtypeService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/apv/apvType/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tApvApvtypeService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tApvApvtype 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批类型",operType = "添加",operDesc = "添加审批类型")
    //@RequiresPermissions({"apvType:add"})
    @PostMapping(value = "/apv/apvType/add")
    public CommonResult insert(@RequestBody TApvApvtype tApvApvtype) throws FebsException {
        try {
            return tApvApvtypeService.insert(tApvApvtype);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApvtype 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批类型",operType = "删除",operDesc = "删除审批类型")
    //@RequiresPermissions({"apvType:del"})
    @DeleteMapping(value = "/apv/apvType/del")
    public CommonResult delete(@RequestBody TApvApvtype tApvApvtype) throws FebsException {
        try {
            return tApvApvtypeService.delete(tApvApvtype);
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
    @OperLog(operModul = "审批管理-审批设置-审批类型",operType = "查看",operDesc = "查看审批类型列表")
    //@RequiresPermissions({"apvType:view"})
    @PostMapping(value = "/apv/apvType/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tApvApvtypeService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tApvApvtype 实体对象
     */
    @OperLog(operModul = "审批管理-审批设置-审批类型",operType = "查看",operDesc = "查看审批类型详情")
    //@RequiresPermissions({"apvType:get"})
    @PostMapping(value = "/apv/apvType/get")
    public CommonResult selectOne(@RequestBody TApvApvtype tApvApvtype) throws FebsException {
        try {
            return tApvApvtypeService.selectById(tApvApvtype);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvApvtype 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批类型",operType = "修改",operDesc = "修改审批类型")
    //@RequiresPermissions({"apvType:update"})
    @PutMapping(value = "/apv/apvType/update")
    public CommonResult update(@RequestBody TApvApvtype tApvApvtype) throws FebsException {
        try {
            return tApvApvtypeService.updateByPkId(tApvApvtype);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 查询审批流程详情
     *
     * @param tApvApvtype 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "审批管理-审批设置-审批类型",operType = "查询",operDesc = "查询审批流程详情")
    @PostMapping(value = "/apv/apvType/apvProcessDetail")
    public CommonResult apvProcessDetail(@RequestBody TApvApvtype tApvApvtype) throws FebsException {
        try {
            return tApvApvtypeService.apvProcessDetail(tApvApvtype);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}
