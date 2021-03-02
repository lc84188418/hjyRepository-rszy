package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_apv.dao.TApvApvtypeMapper;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.service.TApvApvtypeService;
import com.hjy.cloud.t_dictionary.dao.TDictionaryFileMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * (TApvApvtype)表服务实现类
 *
 * @author makejava
 * @since 2021-02-26 14:50:49
 */
@Service("tApvApvtypeService")
public class TApvApvtypeServiceImpl implements TApvApvtypeService {

    @Resource
    private TApvApvtypeMapper tApvApvtypeMapper;
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
        List<TDictionaryFile> list = this.tDictionaryFileMapper.selectAll();
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
        jsonObject.put("iconList", list);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tApvApvtype
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TApvApvtype tApvApvtype) {
        int i = this.tApvApvtypeMapper.insertSelective(tApvApvtype);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvApvtype
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TApvApvtype tApvApvtype) {

        int i = this.tApvApvtypeMapper.updateByPkId(tApvApvtype);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApvtype
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TApvApvtype tApvApvtype) {
//        int i = this.tApvApvtypeMapper.deleteById(tApvApvtype);
        int i = 0;
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
        String pkId = JsonUtil.getStringParam(json, "pk_id");
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TApvApvtype entity = new TApvApvtype();

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
        List<TApvApvtype> list = this.tApvApvtypeMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApvtype>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tApvApvtype 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TApvApvtype tApvApvtype) {
        String pkId = tApvApvtype.getPkApvtypeId();
        TApvApvtype entity = this.tApvApvtypeMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        /**
         *
         */
        List<TDictionaryFile> list = this.tDictionaryFileMapper.selectAll();
        resultJson.put("iconList", list);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TApvApvtype entity = new TApvApvtype();
        List<TApvApvtype> list = this.tApvApvtypeMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApvtype>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
