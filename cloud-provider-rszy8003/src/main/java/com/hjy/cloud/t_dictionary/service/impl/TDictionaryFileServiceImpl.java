package com.hjy.cloud.t_dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_dictionary.dao.TDictionaryFileMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;
import com.hjy.cloud.t_dictionary.service.TDictionaryFileService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.file.FileUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * (TDictionaryFile)表服务实现类
 *
 * @author makejava
 * @since 2021-03-02 15:16:30
 */
@Service("tDictionaryFileService")
public class TDictionaryFileServiceImpl implements TDictionaryFileService {

    @Resource
    private TDictionaryFileMapper tDictionaryFileMapper;
    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.cloud.application.ip}")
    private String webIp;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", null);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tDictionaryFile
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryFile tDictionaryFile) {
        tDictionaryFile.setPkFileId(IDUtils.getUUID());
        tDictionaryFile.setCreateTime(new Date());
        tDictionaryFile.setUpdateTime(new Date());
        tDictionaryFile.setFileType(1);
        String filePath = null;
        if(tDictionaryFile.getFilePath() != null){
            filePath = tDictionaryFile.getFilePath().replace("http://"+webIp+":"+serverPort+"/img/","");
        }
        tDictionaryFile.setFilePath(filePath);
        int i = this.tDictionaryFileMapper.insertSelective(tDictionaryFile);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryFile
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryFile tDictionaryFile) {
        int i = this.tDictionaryFileMapper.updateByPkId(tDictionaryFile);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryFile
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryFile tDictionaryFile) {
        int i = this.tDictionaryFileMapper.deleteById(tDictionaryFile);
        if (i > 0) {
            return new CommonResult(200, "success", "删除数据成功", null);
        } else {
            return new CommonResult(444, "error", "删除数据失败", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult selectAll(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TDictionaryFile entity = new TDictionaryFile();
        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TDictionaryFile> list = this.tDictionaryFileMapper.selectAllPage(entity);
        /**
         * 处理文件路径
         */
        Iterator<TDictionaryFile> it = list.iterator();
        while(it.hasNext()){
            StringBuffer filePath = new StringBuffer();
            TDictionaryFile obj = it.next();
            filePath.append("http://"+webIp+":"+serverPort+"/img/"+obj.getFilePath());
            obj.setFilePath(filePath.toString());
        }
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryFile>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryFile 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryFile tDictionaryFile) {
        String pkId = tDictionaryFile.getPkFileId();
        TDictionaryFile entity = this.tDictionaryFileMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TDictionaryFile entity = new TDictionaryFile();
        List<TDictionaryFile> list = this.tDictionaryFileMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryFile>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
    /**
     * 文件上传
     *
     * @return
     */
    @Override
    public CommonResult iconFileUpload(MultipartFile file, HttpServletRequest httpRequest) {
        if (!FileUtil.isPicFile(file)) {
            return new CommonResult(201, "success", "非图片格式文件，不能上传", null);
        }
        Map<String, Object> pathMap = new HashMap<>();
        //时间戳
        String shijiancuo = String.valueOf(System.currentTimeMillis());
        String fenlei = "icon";
        String filePath = FileUtil.universalFileUpload(file,shijiancuo,fenlei);
        //文件显示路径src
        StringBuffer iconPath = new StringBuffer();
        iconPath.append("http://"+webIp+":"+serverPort+"/img/"+filePath);
        pathMap.put("path", iconPath.toString());
        return new CommonResult(200, "success", "上传成功!", pathMap);
    }
    /**
     * 文件上传
     *
     * @return
     */
    @Override
    public CommonResult otherFileUpload(MultipartFile file, HttpServletRequest httpRequest) {
        if (!FileUtil.isPicFile(file)) {
            return new CommonResult(201, "success", "非图片格式文件，不能上传", null);
        }
        Map<String, Object> pathMap = new HashMap<>();
        //时间戳
        String shijiancuo = String.valueOf(System.currentTimeMillis());
        String fenlei = "other";
        String filePath = FileUtil.universalFileUpload(file,shijiancuo,fenlei);
        //文件显示路径src
        StringBuffer otherPath = new StringBuffer();
        otherPath.append("http://"+webIp+":"+serverPort+"/img/"+filePath);
        pathMap.put("path", otherPath.toString());
        return new CommonResult(200, "success", "上传成功!", pathMap);
    }
}
    
