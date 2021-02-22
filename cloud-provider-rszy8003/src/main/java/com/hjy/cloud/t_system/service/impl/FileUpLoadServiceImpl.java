package com.hjy.cloud.t_system.service.impl;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.file.FileUtils;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.t_system.service.ShiroService;
import com.hjy.cloud.t_system.service.fileUpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传服务实现类
 *
 * @author wangdengjun
 * @since 2020-07-27 16:15:29
 */
@Service
public class FileUpLoadServiceImpl implements fileUpLoadService {

    @Autowired
    private ShiroService shiroService;

    @Override
    public CommonResult singleVideoUpLoad(MultipartFile file, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        if (!FileUtils.isVideoFile(file)) {
            return new CommonResult(201, "success", "非视频格式文件，不能上传", null);
        }
        Map<String, Object> pathMap = new HashMap<String, Object>();
        pathMap.put("path", FileUtils.fileUpload(file, sysToken.getUsername()));
        return new CommonResult(200, "success", "上传成功!", pathMap);
    }

    @Override
    public CommonResult singlePicUpLoad(MultipartFile file, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        if (!FileUtils.isPicFile(file)) {
            return new CommonResult(201, "success", "非图片格式文件，不能上传", null);
        }
        Map<String, Object> pathMap = new HashMap<String, Object>();
        pathMap.put("path", FileUtils.fileUpload(file, sysToken.getUsername()));
        return new CommonResult(200, "success", "上传成功!", pathMap);
    }

    @Override
    public CommonResult singleDocUpLoad(MultipartFile file, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        if (!FileUtils.isDocFile(file)) {
            return new CommonResult(201, "success", "非文档格式文件，不能上传", null);
        }
        Map<String, Object> pathMap = new HashMap<String, Object>();
        pathMap.put("path", FileUtils.fileUpload(file, sysToken.getUsername()));
        return new CommonResult(200, "success", "上传成功!", pathMap);
    }

    /**
     * 批量上传视频
     *
     * @param file
     * @return
     * @
     */
    @Override
    public CommonResult batchVideoUpLoad(MultipartFile[] file, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        return new CommonResult(200, "success", "上传成功!", FileUtils.fileBatchUpload(file, sysToken.getUsername(), ""));
    }

    /**
     * 批量上传图片
     *
     * @param file
     * @return
     * @
     */
    @Override
    public CommonResult batchPicUpLoad(MultipartFile[] file, HttpServletRequest httpRequest) {
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        return new CommonResult(200, "success", "上传成功!", FileUtils.fileBatchUpload(file, sysToken.getUsername(), ""));
    }

    /**
     * 批量上传文档
     *
     * @param file
     * @return
     * @
     */
    @Override
    public CommonResult batchDocUpLoad(MultipartFile[] file, HttpServletRequest httpRequest) {
//        String url = "http://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort();
        SysToken sysToken = shiroService.findByToken(TokenUtil.getRequestToken(httpRequest));
        return new CommonResult(200, "success", "上传成功!", FileUtils.fileBatchUpload(file, sysToken.getUsername(), ""));
    }
}