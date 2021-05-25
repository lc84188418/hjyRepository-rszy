package com.hjy.cloud.t_dictionary.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_dictionary.dao.TDictionarySurveyMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionarySurvey;
import com.hjy.cloud.t_dictionary.service.TDictionarySurveyService;
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
 * (TDictionarySurvey)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:21:55
 */
@Service("tDictionarySurveyService")
public class TDictionarySurveyServiceImpl implements TDictionarySurveyService {

    @Resource
    private TDictionarySurveyMapper tDictionarySurveyMapper;

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
     * @param tDictionarySurvey
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TDictionarySurvey tDictionarySurvey) {
        tDictionarySurvey.setPkSurveyId(IDUtils.getUUID());
        tDictionarySurvey.setCreateTime(new Date());
        tDictionarySurvey.setUpdateTime(new Date());
        int i = this.tDictionarySurveyMapper.insertSelective(tDictionarySurvey);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tDictionarySurvey
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TDictionarySurvey tDictionarySurvey) {
        int i = this.tDictionarySurveyMapper.updateByPkId(tDictionarySurvey);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tDictionarySurvey
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TDictionarySurvey tDictionarySurvey) {
        int i = this.tDictionarySurveyMapper.deleteById(tDictionarySurvey);
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
        TDictionarySurvey entity = new TDictionarySurvey();

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
        List<TDictionarySurvey> list = this.tDictionarySurveyMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TDictionarySurvey>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tDictionarySurvey 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TDictionarySurvey tDictionarySurvey) {
        String pkId = tDictionarySurvey.getPkSurveyId();
        TDictionarySurvey entity = this.tDictionarySurveyMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
}
    
