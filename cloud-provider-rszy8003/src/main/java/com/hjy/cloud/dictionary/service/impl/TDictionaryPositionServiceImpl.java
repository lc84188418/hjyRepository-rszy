package com.hjy.cloud.dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.dictionary.dao.TDictionaryPositionMapper;
import com.hjy.cloud.dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.dictionary.service.TDictionaryPositionService;
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
 * (TDictionaryPosition)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:53
 */
@Service("tDictionaryPositionService")
public class TDictionaryPositionServiceImpl implements TDictionaryPositionService {

    @Resource
    private TDictionaryPositionMapper tDictionaryPositionMapper;

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
     * @param tDictionaryPosition
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryPosition tDictionaryPosition) {
        tDictionaryPosition.setPkPositionId(IDUtils.getUUID());
        tDictionaryPosition.setCreateTime(new Date());
        tDictionaryPosition.setUpdateTime(new Date());
        int i = this.tDictionaryPositionMapper.insertSelective(tDictionaryPosition);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryPosition
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryPosition tDictionaryPosition) {
        int i = this.tDictionaryPositionMapper.updateByPkId(tDictionaryPosition);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryPosition
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryPosition tDictionaryPosition) {
        int i = this.tDictionaryPositionMapper.deleteById(tDictionaryPosition);
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
        TDictionaryPosition entity = new TDictionaryPosition();

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
        List<TDictionaryPosition> list = this.tDictionaryPositionMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryPosition>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryPosition 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryPosition tDictionaryPosition) {
        String pkId = tDictionaryPosition.getPkPositionId();
        TDictionaryPosition entity = this.tDictionaryPositionMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
}
    
