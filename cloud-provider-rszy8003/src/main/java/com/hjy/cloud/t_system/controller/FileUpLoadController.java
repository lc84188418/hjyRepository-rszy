package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.exception.FebsException;
import com.hjy.cloud.t_system.service.fileUpLoadService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传控制层
 *
 * @author wangdengjun
 * @since 2020-09-28 17:48:11
 */
@Api(tags = "文件上传-控制层")
@Slf4j
@RestController
public class FileUpLoadController {
    @Autowired
    private fileUpLoadService fileUpLoadService;

    /**
     * 单个上传视频
     */
    //@RequiresPermissions({"upload:video"})
    @PostMapping("/file/upload/video")
    public CommonResult singleVideoUpLoad(MultipartFile file, HttpServletRequest httpRequest) throws FebsException {
        try {
            return fileUpLoadService.singleVideoUpLoad(file, httpRequest);
        } catch (Exception e) {
            String message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    /**
     * 单个上传图片
     */
    //@RequiresPermissions({"upload:pic"})
    @PostMapping("/file/upload/pic")
    public CommonResult singlePicUpLoad(MultipartFile file, HttpServletRequest httpRequest) throws FebsException {
        try {
            return fileUpLoadService.singlePicUpLoad(file, httpRequest);
        } catch (Exception e) {
            String message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    /**
     * 单个上传文档
     */
    //@RequiresPermissions({"upload:doc"})
    @PostMapping("/file/upload/doc")
    public CommonResult singleDocUpLoad(MultipartFile file, HttpServletRequest httpRequest) throws FebsException {
        try {
            return fileUpLoadService.singleDocUpLoad(file, httpRequest);
        } catch (Exception e) {
            String message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 批量上传视频
     */
    //@RequiresPermissions({"upload:video"})
    @PostMapping("/file/upload/batch/video")
    public CommonResult batchVideoUpLoad(MultipartFile[] file, HttpServletRequest httpRequest) throws FebsException {
        try {
            return fileUpLoadService.batchVideoUpLoad(file, httpRequest);
        } catch (Exception e) {
            String message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 批量上传图片
     */
    //@RequiresPermissions({"upload:pic"})
    @PostMapping("/file/upload/batch/pic")
    public CommonResult batchPicUpLoad(MultipartFile[] file, HttpServletRequest httpRequest) throws FebsException {
        try {
            return fileUpLoadService.batchPicUpLoad(file, httpRequest);
        } catch (Exception e) {
            String message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 批量上传文档
     */
    //@RequiresPermissions({"upload:doc"})
    @PostMapping("/file/upload/batch/doc")
    public CommonResult batchDocUpLoad(MultipartFile[] file, HttpServletRequest httpRequest) throws FebsException {
        try {
            return fileUpLoadService.batchDocUpLoad(file, httpRequest);
        } catch (Exception e) {
            String message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}