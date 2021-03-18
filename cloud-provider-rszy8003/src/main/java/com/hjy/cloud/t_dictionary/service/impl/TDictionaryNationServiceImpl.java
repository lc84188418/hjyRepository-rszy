package com.hjy.cloud.t_dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_dictionary.dao.TDictionaryNationMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryNation;
import com.hjy.cloud.t_dictionary.service.TDictionaryNationService;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TDictionaryNation)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
@Service("tDictionaryNationService")
public class TDictionaryNationServiceImpl implements TDictionaryNationService {

    @Resource
    private TDictionaryNationMapper tDictionaryNationMapper;

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
     * @param tDictionaryNation
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryNation tDictionaryNation) {
        tDictionaryNation.setPkNationId(IDUtils.getUUID());
        tDictionaryNation.setCreateTime(new Date());
        tDictionaryNation.setUpdateTime(new Date());
        int i = this.tDictionaryNationMapper.insertSelective(tDictionaryNation);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 添加数据
     *
     * @param param
     * @return
     */
    @Transactional()
    @Override
    public CommonResult addBatch(String param) {
        JSONObject json = JSON.parseObject(param);
        String names = JsonUtil.getStringParam(json, "names");
        String[] strings = names.split("、");
        List<TDictionaryNation> list = new ArrayList<>();
        for (String s : strings){
            TDictionaryNation tDictionaryNation = new TDictionaryNation();
            tDictionaryNation.setPkNationId(IDUtils.getUUID());
            tDictionaryNation.setCreateTime(new Date());
            tDictionaryNation.setUpdateTime(new Date());
            tDictionaryNation.setTurnOn(1);
            tDictionaryNation.setNationName(s);
            list.add(tDictionaryNation);
        }
        int i = 0;
        if(list != null && list.size() > 0){
            i = tDictionaryNationMapper.insertBatch(list);
        }
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }
    /**
     * 修改数据
     *
     * @param tDictionaryNation
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryNation tDictionaryNation) {
        int i = this.tDictionaryNationMapper.updateByPkId(tDictionaryNation);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryNation
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryNation tDictionaryNation) {
        int i = this.tDictionaryNationMapper.deleteById(tDictionaryNation);
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
        TDictionaryNation entity = new TDictionaryNation();

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
        List<TDictionaryNation> list = this.tDictionaryNationMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryNation>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryNation 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryNation tDictionaryNation) {
        String pkId = tDictionaryNation.getPkNationId();
        TDictionaryNation entity = this.tDictionaryNationMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
}
    
