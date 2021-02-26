package com.hjy.cloud.dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.dictionary.dao.TDictionaryAreaMapper;
import com.hjy.cloud.dictionary.dao.TDictionaryCityMapper;
import com.hjy.cloud.dictionary.entity.TDictionaryArea;
import com.hjy.cloud.dictionary.entity.TDictionaryCity;
import com.hjy.cloud.dictionary.service.TDictionaryAreaService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (TDictionaryArea)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:50
 */
@Service("tDictionaryAreaService")
public class TDictionaryAreaServiceImpl implements TDictionaryAreaService {

    @Resource
    private TDictionaryAreaMapper tDictionaryAreaMapper;
    @Resource
    private TDictionaryCityMapper tDictionaryCityMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        List<TDictionaryCity> tDictionaryCities = tDictionaryCityMapper.selectAllId_Name();
        jsonObject.put("entity", tDictionaryCities);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tDictionaryArea
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryArea tDictionaryArea) {
        tDictionaryArea.setPkAreaId(IDUtils.getUUID());
        tDictionaryArea.setCreateTime(new Date());
        tDictionaryArea.setUpdateTime(new Date());
        int i = this.tDictionaryAreaMapper.insertSelective(tDictionaryArea);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryArea
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryArea tDictionaryArea) {
        int i = this.tDictionaryAreaMapper.updateByPkId(tDictionaryArea);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryArea
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryArea tDictionaryArea) {
        int i = this.tDictionaryAreaMapper.deleteById(tDictionaryArea);
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
        TDictionaryArea entity = new TDictionaryArea();

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
        List<TDictionaryArea> list = this.tDictionaryAreaMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryArea>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryArea 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryArea tDictionaryArea) {
        String pkId = tDictionaryArea.getPkAreaId();
        TDictionaryArea entity = this.tDictionaryAreaMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
}
    
