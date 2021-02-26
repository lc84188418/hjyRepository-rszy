package com.hjy.cloud.dictionary.controller;


import com.hjy.cloud.dictionary.entity.TDictionaryEducation;
import com.hjy.cloud.dictionary.service.TDictionaryEducationService;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TDictionaryEducation)表控制层
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
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
            throw new FebsException(message);
        }
    }

    /**
     * 新增数据
     *
     * @param tDictionaryEducation 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/education/add")
    public CommonResult insert(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.insert(tDictionaryEducation);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryEducation 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/education/del")
    public CommonResult delete(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.delete(tDictionaryEducation);
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
    @PostMapping(value = "/dictionary/education/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryEducationService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryEducation 实体对象
     */
    @PostMapping(value = "/dictionary/education/get")
    public CommonResult selectOne(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.selectById(tDictionaryEducation);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryEducation 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/education/update")
    public CommonResult update(@RequestBody TDictionaryEducation tDictionaryEducation) throws FebsException {
        try {
            return tDictionaryEducationService.updateByPkId(tDictionaryEducation);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
