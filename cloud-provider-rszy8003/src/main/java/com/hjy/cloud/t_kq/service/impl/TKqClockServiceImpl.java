package com.hjy.cloud.t_kq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_kq.dao.TKqClockMapper;
import com.hjy.cloud.t_kq.entity.TKqClock;
import com.hjy.cloud.t_kq.service.TKqClockService;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TKqClock)表服务实现类
 *
 * @author makejava
 * @since 2021-03-19 17:46:25
 */
@Service("tKqClockService")
public class TKqClockServiceImpl implements TKqClockService {

    @Resource
    private TKqClockMapper tKqClockMapper;

    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage(HttpServletRequest request) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        //获取当前用户打卡相关信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", null);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TKqClock tKqClock) {
        int i = this.tKqClockMapper.insertSelective(tKqClock);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TKqClock tKqClock) {
        int i = this.tKqClockMapper.updateByPkId(tKqClock);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tKqClock
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TKqClock tKqClock) {
        int i = this.tKqClockMapper.deleteById(tKqClock);
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
        //分页参数
        String pkId = JsonUtil.getStringParam(json, "pk_id");
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        //查询条件
        TKqClock entity = new TKqClock();

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
        List<TKqClock> list = this.tKqClockMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqClock>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tKqClock 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TKqClock tKqClock) {
        String pkId = tKqClock.getPkClockId();
        TKqClock entity = this.tKqClockMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TKqClock entity = new TKqClock();
        List<TKqClock> list = this.tKqClockMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TKqClock>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
