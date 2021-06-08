package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.service.TDictionaryHtlxService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionaryHtlx)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
@Api(tags = "数据字典-合同类型-控制层")
@Slf4j
@RestController
public class TDictionaryHtlxController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryHtlxService tDictionaryHtlxService;

    /**
     * 1 跳转到新增页面
     */

    @GetMapping(value = "/dictionary/htlx/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryHtlxService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryHtlx 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "字典管理-合同类型",operType = "添加",operDesc = "新增合同类型基本信息")
    @RequiresPermissions({"htlx:add"})
    @PostMapping(value = "/dictionary/htlx/add")
    public CommonResult insert(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.insert(tDictionaryHtlx);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryHtlx 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "字典管理-合同类型",operType = "删除",operDesc = "删除合同类型基本信息")
    @RequiresPermissions({"htlx:del"})
    @DeleteMapping(value = "/dictionary/htlx/del")
    public CommonResult delete(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.delete(tDictionaryHtlx);
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
    @OperLog(operModul = "字典管理-合同类型",operType = "查看",operDesc = "查看合同类型列表")
    @RequiresPermissions({"htlx:view"})
    @PostMapping(value = "/dictionary/htlx/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryHtlxService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryHtlx 实体对象
     */
    @OperLog(operModul = "字典管理-合同类型",operType = "查看",operDesc = "查看单个合同类型基本信息")
    @RequiresPermissions({"htlx:get"})
    @PostMapping(value = "/dictionary/htlx/get")
    public CommonResult selectOne(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.selectById(tDictionaryHtlx);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryHtlx 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "字典管理-合同类型",operType = "修改",operDesc = "修改合同类型基本信息")
    @RequiresPermissions({"htlx:update"})
    @PutMapping(value = "/dictionary/htlx/update")
    public CommonResult update(@RequestBody TDictionaryHtlx tDictionaryHtlx) throws FebsException {
        try {
            return tDictionaryHtlxService.updateByPkId(tDictionaryHtlx);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
