package com.hjy.cloud.dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.dictionary.dao.TDictionaryCityMapper;
import com.hjy.cloud.dictionary.dao.TDictionaryProvinceMapper;
import com.hjy.cloud.dictionary.entity.TDictionaryCity;
import com.hjy.cloud.dictionary.entity.TDictionaryProvince;
import com.hjy.cloud.dictionary.service.TDictionaryCityService;
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
 * (TDictionaryCity)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:51
 */
@Service("tDictionaryCityService")
public class TDictionaryCityServiceImpl implements TDictionaryCityService {

    @Resource
    private TDictionaryCityMapper tDictionaryCityMapper;
    @Resource
    private TDictionaryProvinceMapper tDictionaryProvinceMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        List<TDictionaryProvince> list = tDictionaryProvinceMapper.selectAllId_Name();
        jsonObject.put("entity", list);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tDictionaryCity
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryCity tDictionaryCity) {
        tDictionaryCity.setPkCityId(IDUtils.getUUID());
        tDictionaryCity.setCreateTime(new Date());
        tDictionaryCity.setUpdateTime(new Date());
        int i = this.tDictionaryCityMapper.insertSelective(tDictionaryCity);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryCity
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryCity tDictionaryCity) {
        int i = this.tDictionaryCityMapper.updateByPkId(tDictionaryCity);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryCity
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryCity tDictionaryCity) {
        int i = this.tDictionaryCityMapper.deleteById(tDictionaryCity);
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
        TDictionaryCity entity = new TDictionaryCity();

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
        List<TDictionaryCity> list = this.tDictionaryCityMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryCity>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryCity 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryCity tDictionaryCity) {
        String pkId = tDictionaryCity.getPkCityId();
        TDictionaryCity entity = this.tDictionaryCityMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
}
    
