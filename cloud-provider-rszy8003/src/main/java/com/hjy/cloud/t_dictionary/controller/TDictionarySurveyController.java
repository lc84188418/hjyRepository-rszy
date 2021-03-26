package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.t_dictionary.entity.TDictionarySurvey;
import com.hjy.cloud.t_dictionary.service.TDictionarySurveyService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionarySurvey)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:55
 */
@Api(tags = "数据字典-员工概括-控制层")
@Slf4j
@RestController
public class TDictionarySurveyController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionarySurveyService tDictionarySurveyService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/survey/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionarySurveyService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionarySurvey 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "字典管理-概括管理",operType = "添加",operDesc = "新增员工基本信息")
    @RequiresPermissions({"survey:add"})
    @PostMapping(value = "/dictionary/survey/add")
    public CommonResult insert(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.insert(tDictionarySurvey);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionarySurvey 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "字典管理-概括管理",operType = "删除",operDesc = "删除员工基本信息")
    @RequiresPermissions({"survey:del"})
    @DeleteMapping(value = "/dictionary/survey/del")
    public CommonResult delete(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.delete(tDictionarySurvey);
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
    @OperLog(operModul = "字典管理-概括管理",operType = "查看",operDesc = "查看员工信息列表")
    @RequiresPermissions({"survey:view"})
    @PostMapping(value = "/dictionary/survey/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionarySurveyService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionarySurvey 实体对象
     */
    @OperLog(operModul = "字典管理-概括管理",operType = "查看",operDesc = "查看单个员工信息")
    @RequiresPermissions({"survey:get"})
    @PostMapping(value = "/dictionary/survey/get")
    public CommonResult selectOne(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.selectById(tDictionarySurvey);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionarySurvey 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "字典管理-概括管理",operType = "修改",operDesc = "修改员工基本信息")
    @RequiresPermissions({"survey:update"})
    @PutMapping(value = "/dictionary/survey/update")
    public CommonResult update(@RequestBody TDictionarySurvey tDictionarySurvey) throws FebsException {
        try {
            return tDictionarySurveyService.updateByPkId(tDictionarySurvey);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
