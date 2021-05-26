package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.common.annotation.OperLog;
import com.hjy.cloud.t_dictionary.entity.TDictionaryEducation;
import com.hjy.cloud.t_dictionary.service.TDictionaryEducationService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TDictionaryEducation)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
@Api(tags = "数据字典-学历-控制层")
@Slf4j
@RestController
public class TDictionaryEducationController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryEducationService tDictionaryEducationService;

    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/dictionary/education/addPage")
    public CommonResult insertPage() throws FebsException {
        try {
            return tDictionaryEducationService.insertPage();
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryEducation 实体对象
     * @return 新增结果
     */
    @OperLog(operModul = "字典管理-学历管理",operType = "添加",operDesc = "新增学历基本信息")
    //@RequiresPermissions({"education:add"})
    @PostMapping(value = "/dictionary/education/add")
    public CommonResult insert(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.insert(tDictionaryEducation);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryEducation 实体对象
     * @return 删除结果
     */
    @OperLog(operModul = "字典管理-学历管理",operType = "删除",operDesc = "删除学历基本信息")
    //@RequiresPermissions({"education:del"})
    @DeleteMapping(value = "/dictionary/education/del")
    public CommonResult delete(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.delete(tDictionaryEducation);
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
    @OperLog(operModul = "字典管理-学历管理",operType = "查看",operDesc = "查看学历信息列表")
    //@RequiresPermissions({"education:view"})
    @PostMapping(value = "/dictionary/education/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryEducationService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryEducation 实体对象
     */
    @OperLog(operModul = "字典管理-学历管理",operType = "查看",operDesc = "查看单个学历信息")
    //@RequiresPermissions({"education:get"})
    @PostMapping(value = "/dictionary/education/get")
    public CommonResult selectOne(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.selectById(tDictionaryEducation);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryEducation 实体对象
     * @return 修改结果
     */
    @OperLog(operModul = "字典管理-学历管理",operType = "修改",operDesc = "修改学历基本信息")
    //@RequiresPermissions({"education:update"})
    @PutMapping(value = "/dictionary/education/update")
    public CommonResult update(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.updateByPkId(tDictionaryEducation);
        } catch (Exception e) {
            String message = "失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


}
