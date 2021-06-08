package com.hjy.cloud.t_dictionary.service;

import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;
import com.hjy.cloud.domin.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * (TDictionaryFile)表服务接口
 *
 * @author makejava
 * @since 2021-03-02 15:16:30
 */
public interface TDictionaryFileService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tDictionaryFile
     * @return
     */
    CommonResult insert(TDictionaryFile tDictionaryFile);

    /**
     * 修改数据
     *
     * @param tDictionaryFile
     * @return
     */
    CommonResult updateByPkId(TDictionaryFile tDictionaryFile);

    /**
     * 删除数据
     *
     * @param tDictionaryFile
     * @return
     */
    CommonResult delete(TDictionaryFile tDictionaryFile);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);

    /**
     * 获取单个数据
     *
     * @param tDictionaryFile 实体对象
     * @return
     */
    CommonResult selectById(TDictionaryFile tDictionaryFile);
    /**
     * 文件上传
     *
     * @return
     */
    CommonResult iconFileUpload(MultipartFile file, HttpServletRequest httpRequest);
    CommonResult otherFileUpload(MultipartFile file, HttpServletRequest httpRequest);
}
