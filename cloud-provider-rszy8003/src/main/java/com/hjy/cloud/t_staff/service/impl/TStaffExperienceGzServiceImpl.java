package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_staff.dao.TStaffExperienceGzMapper;
import com.hjy.cloud.t_staff.entity.TStaffExperienceGz;
import com.hjy.cloud.t_staff.service.TStaffExperienceGzService;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStaffExperienceGz)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:14
 */
@Service("tStaffExperienceGzService")
public class TStaffExperienceGzServiceImpl implements TStaffExperienceGzService {

    @Resource
    private TStaffExperienceGzMapper tStaffExperienceGzMapper;

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
     * @param tStaffExperienceGz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffExperienceGz tStaffExperienceGz) {
        int i = this.tStaffExperienceGzMapper.insertSelective(tStaffExperienceGz);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tStaffExperienceGz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffExperienceGz tStaffExperienceGz) {
        int i = this.tStaffExperienceGzMapper.updateByPkId(tStaffExperienceGz);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffExperienceGz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffExperienceGz tStaffExperienceGz) {
        int i = this.tStaffExperienceGzMapper.deleteById(tStaffExperienceGz);
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
        TStaffExperienceGz entity = new TStaffExperienceGz();

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
        List<TStaffExperienceGz> list = this.tStaffExperienceGzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffExperienceGz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffExperienceGz 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffExperienceGz tStaffExperienceGz) {
        String pkId = tStaffExperienceGz.getPkExperienceId();
        TStaffExperienceGz entity = this.tStaffExperienceGzMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffExperienceGz entity = new TStaffExperienceGz();
        List<TStaffExperienceGz> list = this.tStaffExperienceGzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffExperienceGz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
