package com.hjy.cloud.t_index.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.config.rabbitmq.Sender;
import com.hjy.cloud.t_index.dao.TIndexBwlMapper;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.t_index.service.TIndexBwlService;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (TIndexBwl)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:50:54
 */
@Service("tIndexBwlService")
public class TIndexBwlServiceImpl implements TIndexBwlService {

    @Resource
    private TIndexBwlMapper tIndexBwlMapper;
    @Resource
    private Sender sender;
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
     * @param tIndexBwl
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(HttpSession session, TIndexBwl tIndexBwl) {
        tIndexBwl.setPkBwlId(IDUtils.getUUID());
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        /**
         * 提醒日期不可早与当前日期
         * 做判断
         */
        tIndexBwl.setRemindTime(new Date());
        tIndexBwl.setIsSend(0);
        tIndexBwl.setFkUserId(activeUser.getUserId());
        int i = this.tIndexBwlMapper.insertSelective(tIndexBwl);
        if (i > 0) {
            /**
             * 发送消息
             */
            Map<String,Object> rabbitMap = new HashMap<>();
            rabbitMap.put("data",tIndexBwl);
//            sender.send("liuchun",rabbitMap);
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tIndexBwl
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TIndexBwl tIndexBwl) {
        int i = this.tIndexBwlMapper.updateByPkId(tIndexBwl);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tIndexBwl
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TIndexBwl tIndexBwl) {
        int i = this.tIndexBwlMapper.deleteById(tIndexBwl);
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
        TIndexBwl entity = new TIndexBwl();

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
        List<TIndexBwl> list = this.tIndexBwlMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TIndexBwl>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tIndexBwl 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TIndexBwl tIndexBwl) {
        String pkId = tIndexBwl.getPkBwlId();
        TIndexBwl entity = this.tIndexBwlMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TIndexBwl entity = new TIndexBwl();
        List<TIndexBwl> list = this.tIndexBwlMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TIndexBwl>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
