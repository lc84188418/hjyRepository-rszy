package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_staff.dao.TStaffRenewalMapper;
import com.hjy.cloud.t_staff.entity.TStaffRenewal;
import com.hjy.cloud.t_staff.service.TStaffRenewalService;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffRenewal)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:17
 */
@Service("tStaffRenewalService")
public class TStaffRenewalServiceImpl implements TStaffRenewalService {

    @Resource
    private TStaffRenewalMapper tStaffRenewalMapper;

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
     * @param tStaffRenewal
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffRenewal tStaffRenewal) {
        int i = this.tStaffRenewalMapper.insertSelective(tStaffRenewal);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffRenewal
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffRenewal tStaffRenewal) {
        int i = this.tStaffRenewalMapper.updateByPkId(tStaffRenewal);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffRenewal
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffRenewal tStaffRenewal) {
        int i = this.tStaffRenewalMapper.deleteById(tStaffRenewal);
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
        TStaffRenewal entity = new TStaffRenewal();

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
        List<TStaffRenewal> list = this.tStaffRenewalMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffRenewal>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffRenewal 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffRenewal tStaffRenewal) {
        String pkId = tStaffRenewal.getPkRenewalId();
        TStaffRenewal entity = this.tStaffRenewalMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffRenewal entity = new TStaffRenewal();
        List<TStaffRenewal> list = this.tStaffRenewalMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffRenewal>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
