package com.hjy.cloud.t_dictionary.controller;


import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;
import com.hjy.cloud.t_dictionary.service.TDictionaryFileService;
import com.hjy.cloud.t_system.service.fileUpLoadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TDictionaryFile)表控制层
 *
 * @author makejava
 * @since 2021-03-02 15:16:31
 */
@RestController
public class TDictionaryFileController {
    /**
     * 服务对象
     */
    @Resource
    private TDictionaryFileService tDictionaryFileService;

    /**
     * 图标上传
     */
    @PostMapping(value = "/dictionary/file/upload/icon")
    public CommonResult iconFileUpload(MultipartFile file,HttpServletRequest httpRequest) throws FebsException {
        try {
            return tDictionaryFileService.iconFileUpload(file, httpRequest);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 其他图片文件上传
     */
    @PostMapping(value = "/dictionary/file/upload/other")
    public CommonResult otherFileUpload(MultipartFile file,HttpServletRequest httpRequest) throws FebsException {
        try {
            return tDictionaryFileService.otherFileUpload(file, httpRequest);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }
    /**
     * 新增数据
     *
     * @param tDictionaryFile 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/dictionary/file/add")
    public CommonResult insert(@RequestBody TDictionaryFile tDictionaryFile) throws FebsException {
        try {
            return tDictionaryFileService.insert(tDictionaryFile);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryFile 实体对象
     * @return 删除结果
     */
    @DeleteMapping(value = "/dictionary/file/del")
    public CommonResult delete(@RequestBody TDictionaryFile tDictionaryFile) throws FebsException {
        try {
            return tDictionaryFileService.delete(tDictionaryFile);
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
    @PostMapping(value = "/dictionary/file/list")
    public CommonResult selectAll(@RequestBody String param) throws FebsException {
        try {
            return tDictionaryFileService.selectAll(param);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tDictionaryFile 实体对象
     */
    @PostMapping(value = "/dictionary/file/get")
    public CommonResult selectOne(@RequestBody TDictionaryFile tDictionaryFile) throws FebsException {
        try {
            return tDictionaryFileService.selectById(tDictionaryFile);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryFile 实体对象
     * @return 修改结果
     */
    @PutMapping(value = "/dictionary/file/update")
    public CommonResult update(@RequestBody TDictionaryFile tDictionaryFile) throws FebsException {
        try {
            return tDictionaryFileService.updateByPkId(tDictionaryFile);
        } catch (Exception e) {
            String message = "失败";
            throw new FebsException(message);
        }
    }


}
