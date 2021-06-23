package com.hjy.cloud.t_index.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.utils.UserShiroUtil;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_index.dao.TIndexBwlMapper;
import com.hjy.cloud.t_index.entity.TIndexBwl;
import com.hjy.cloud.t_index.service.TIndexBwlService;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    private TSysTokenMapper tSysTokenMapper;
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
    public CommonResult insert(HttpSession session,HttpServletRequest request, TIndexBwl tIndexBwl) {
        tIndexBwl.setPkBwlId(IDUtils.getUUID());
        SysToken token = this.tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));
        if(token == null){
            return new CommonResult(444, "error", "无法验证当前用户身份！请刷新或重新登录后再试", null);
        }
        /**
         * 提醒日期不可早与当前日期
         * 做判断
         */
        tIndexBwl.setRemindTime(tIndexBwl.getRemindTime());
        tIndexBwl.setIsSend(0);
        tIndexBwl.setFkUserId(token.getFkUserId());
        int i = this.tIndexBwlMapper.insertSelective(tIndexBwl);
        if (i > 0) {
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
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(String pkBwlId) {
        int i = this.tIndexBwlMapper.deleteById(pkBwlId);
        if (i > 0) {
            return new CommonResult(200, "success", "删除数据成功", null);
        } else {
            return new CommonResult(444, "error", "删除数据失败", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public CommonResult<PageResult<TIndexBwl>> selectAll(PageRequest<TIndexBwl> pageRequest) {
        if (pageRequest.getPageNum() == 0) {
            pageRequest.setPageNum(1);
        }
        if (pageRequest.getPageSize() == 0) {
            pageRequest.setPageSize(10);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<TIndexBwl> list = this.tIndexBwlMapper.selectAllPage(pageRequest.getParam());
        PageResult<TIndexBwl> result = PageUtil.getPageResult(new PageInfo<TIndexBwl>(list));
        return new CommonResult(200, "success", "获取数据成功", result);
    }
    /**
     * 查询所有数据,用户个人
     *
     * @return
     */
    @Override
    public CommonResult<PageResult<TIndexBwl>> selectAll(HttpSession session, HttpServletRequest request,PageRequest<TIndexBwl> pageRequest) {
        String userId = UserShiroUtil.getCurrentUserId(session,request);
        if(StringUtils.isEmpty(userId)){
            return new CommonResult(444, "error", "无法验证当前用户身份！请刷新或重新登录后再试", null);
        }
        TIndexBwl select = new TIndexBwl();
        select.setFkUserId(userId);
        if (pageRequest.getPageNum() == 0) {
            pageRequest.setPageNum(1);
        }
        if (pageRequest.getPageSize() == 0) {
            pageRequest.setPageSize(10);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<TIndexBwl> list = this.tIndexBwlMapper.selectAllPage(select);
        PageResult<TIndexBwl> result = PageUtil.getPageResult(new PageInfo<TIndexBwl>(list));
        return new CommonResult(200, "success", "获取数据成功", result);
    }
    /**
     * 获取单个数据
     *
     * @return
     */
    @Override
    public CommonResult<TIndexBwl> selectById(String pkBwlId) {
        TIndexBwl entity = this.tIndexBwlMapper.selectByPkId(pkBwlId);
        return new CommonResult(200, "success", "获取数据成功", entity);
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
    
