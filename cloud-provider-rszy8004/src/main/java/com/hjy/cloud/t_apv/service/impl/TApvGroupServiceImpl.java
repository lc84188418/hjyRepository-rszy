package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_apv.dao.TApvGroupMapper;
import com.hjy.cloud.t_apv.entity.TApvGroup;
import com.hjy.cloud.t_apv.service.TApvGroupService;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApvGroup)表服务实现类
 *
 * @author makejava
 * @since 2021-02-26 14:50:50
 */
@Service("tApvGroupService")
public class TApvGroupServiceImpl implements TApvGroupService {

    @Resource
    private TApvGroupMapper tApvGroupMapper;

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
     * @param tApvGroup
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TApvGroup tApvGroup) {
        int i = this.tApvGroupMapper.insertSelective(tApvGroup);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvGroup
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TApvGroup tApvGroup) {
        int i = this.tApvGroupMapper.updateByPkId(tApvGroup);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvGroup
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TApvGroup tApvGroup) {
        int i = this.tApvGroupMapper.deleteById(tApvGroup);
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
        TApvGroup entity = new TApvGroup();

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
        List<TApvGroup> list = this.tApvGroupMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvGroup>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tApvGroup 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TApvGroup tApvGroup) {
        String pkId = tApvGroup.getPkGroupId();
        TApvGroup entity = this.tApvGroupMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TApvGroup entity = new TApvGroup();
        List<TApvGroup> list = this.tApvGroupMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvGroup>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
