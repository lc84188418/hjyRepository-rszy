package com.hjy.cloud.t_dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_dictionary.dao.TDictionaryHtlxMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.service.TDictionaryHtlxService;
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
 * (TDictionaryHtlx)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:52
 */
@Service("tDictionaryHtlxService")
public class TDictionaryHtlxServiceImpl implements TDictionaryHtlxService {

    @Resource
    private TDictionaryHtlxMapper tDictionaryHtlxMapper;

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
     * @param tDictionaryHtlx
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionaryHtlx tDictionaryHtlx) {
        tDictionaryHtlx.setPkHtlxId(IDUtils.getUUID());
        tDictionaryHtlx.setCreateTime(new Date());
        tDictionaryHtlx.setUpdateTime(new Date());
        int i = this.tDictionaryHtlxMapper.insertSelective(tDictionaryHtlx);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionaryHtlx
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionaryHtlx tDictionaryHtlx) {
        int i = this.tDictionaryHtlxMapper.updateByPkId(tDictionaryHtlx);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionaryHtlx
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionaryHtlx tDictionaryHtlx) {
        int i = this.tDictionaryHtlxMapper.deleteById(tDictionaryHtlx);
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
        TDictionaryHtlx entity = new TDictionaryHtlx();

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
        List<TDictionaryHtlx> list = this.tDictionaryHtlxMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionaryHtlx>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionaryHtlx 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionaryHtlx tDictionaryHtlx) {
        String pkId = tDictionaryHtlx.getPkHtlxId();
        TDictionaryHtlx entity = this.tDictionaryHtlxMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
}
    
