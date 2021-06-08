package com.hjy.cloud.t_system.service;

import com.hjy.cloud.domin.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传服务接口
 *
 * @author wangdengjun
 * @since 2020-07-27 16:15:29
 */
public interface fileUpLoadService {


    /**
     * 单个上传视频
     *
     * @param file
     * @return
     * @
     */
    CommonResult singleVideoUpLoad(MultipartFile file, HttpServletRequest httpRequest);

    /**
     * 单个上传图片
     *
     * @param file
     * @return
     * @
     */
    CommonResult singlePicUpLoad(MultipartFile file, HttpServletRequest httpRequest);

    /**
     * 单个上传文档
     *
     * @param file
     * @return
     * @
     */
    CommonResult singleDocUpLoad(MultipartFile file, HttpServletRequest httpRequest);

    /**
     * 批量上传视频
     *
     * @param file
     * @return
     * @
     */
    CommonResult batchVideoUpLoad(MultipartFile[] file, HttpServletRequest httpRequest);

    /**
     * 批量上传图片
     *
     * @param file
     * @return
     * @
     */
    CommonResult batchPicUpLoad(MultipartFile[] file, HttpServletRequest httpRequest);

    /**
     * 批量上传文档
     *
     * @param file
     * @return
     * @
     */
    CommonResult batchDocUpLoad(MultipartFile[] file, HttpServletRequest httpRequest);


}