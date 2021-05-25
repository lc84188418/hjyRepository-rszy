package com.hjy.cloud.t_dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_dictionary.dao.TDictionaryCityMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryProvinceMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryCity;
import com.hjy.cloud.t_dictionary.entity.TDictionaryProvince;
import com.hjy.cloud.t_dictionary.service.TDictionaryProvinceService;
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
 * (TDictionaryProvince)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:54
 */
@Service("tDictionaryProvinceService")
public class TDictionaryProvinceServiceImpl implements TDictionaryProvinceService {

    @Resource
    private TDictionaryProvinceMapper tDictionaryProvinceMapper;
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
        jsonObject.put("entity", null);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tDictionaryProvince
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryProvince tDictionaryProvince) {
        tDictionaryProvince.setPkProvinceId(IDUtils.getUUID());
        tDictionaryProvince.setCreateTime(new Date());
        tDictionaryProvince.setUpdateTime(new Date());
        int i = this.tDictionaryProvinceMapper.insertSelective(tDictionaryProvince);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryProvince
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryProvince tDictionaryProvince) {
        int i = this.tDictionaryProvinceMapper.updateByPkId(tDictionaryProvince);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryProvince
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryProvince tDictionaryProvince) {
        int i = this.tDictionaryProvinceMapper.deleteById(tDictionaryProvince);
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
        TDictionaryProvince entity = new TDictionaryProvince();

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
        List<TDictionaryProvince> list = this.tDictionaryProvinceMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryProvince>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryProvince 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryProvince tDictionaryProvince) {
        String pkId = tDictionaryProvince.getPkProvinceId();
        TDictionaryProvince entity = this.tDictionaryProvinceMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 给该省份添加城市信息
     *
     * @param city 实体对象
     * @return
     */
    @Override
    public CommonResult addCity(TDictionaryCity city) {
        city.setPkCityId(IDUtils.getUUID());
        city.setTurnOn(1);
        city.setCreateTime(new Date());
        city.setUpdateTime(new Date());
        int i = tDictionaryCityMapper.insertSelective(city);
        if (i > 0) {
            return new CommonResult(200, "success", "添加城市数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加城市数据失败", null);
        }
    }
}
    
