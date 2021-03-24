package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.dao.TStaffQuitMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffQuit;
import com.hjy.cloud.t_staff.entity.TStaffZz;
import com.hjy.cloud.t_staff.service.TStaffQuitService;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * (TStaffQuit)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@Service("tStaffQuitService")
public class TStaffQuitServiceImpl implements TStaffQuitService {

    @Resource
    private TStaffQuitMapper tStaffQuitMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage(HttpServletRequest request) {
        String token = TokenUtil.getRequestToken(request);
        if (StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头中传入token", null);

        }
        SysToken sysToken = tSysTokenMapper.findByToken(token);
        if(sysToken == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);

        }
        //查询是否提交过离职申请
        TStaffQuit tStaffQuit = tStaffQuitMapper.selectByStaffId(sysToken.getFkUserId());
        if(tStaffQuit == null){
            //离职审批流程信息
            JSONObject resultJson = ObjectAsyncTask.sponsorApprovalPage(sysToken,sysToken.getFkUserId(),"离职申请",1);
            String msg = (String) resultJson.get("msg");
            resultJson.remove("msg");
            return new CommonResult(200, "success", msg, resultJson);
        }else {
            return new CommonResult(444, "error", "该员工已提交过离职申请，无需再次申请，请等待审批！", null);

        }
    }

    /**
     * 添加数据
     *
     * @param request param
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(HttpServletRequest request,String param) throws ParseException {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        //离职人基本信息
        TStaffInfo staffInfo =tStaffInfoMapper.selectByPkId(sysToken.getFkUserId());
        //查询是否已添加过离职申请
        TStaffQuit selectEntity = new TStaffQuit();
        selectEntity.setFkStaffId(staffInfo.getPkStaffId());
        List<TStaffQuit> tStaffQuits = tStaffQuitMapper.selectAllPage(selectEntity);
        if(tStaffQuits != null && tStaffQuits.size() > 0){
            return new CommonResult(445, "error", "已提交过离职申请，不可再次提交", null);
        }
        TStaffQuit staffQuit = new TStaffQuit();
        String pkQuitId = IDUtils.getUUID();
        staffQuit.setPkQuitId(pkQuitId);
        staffQuit.setFkStaffId(staffInfo.getPkStaffId());
        staffQuit.setFkDeptId(staffInfo.getFkDeptId());
        staffQuit.setPosition(staffInfo.getFkPositionId());
        staffQuit.setOperatedPeople(sysToken.getFullName());
        staffQuit.setApplyTime(new Date());
        staffQuit.setQuitStatus(0);
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String quitType = JsonUtil.getStringParam(json, "quitType");
        String quitTime = JsonUtil.getStringParam(json, "quitTime");
        String remarks = JsonUtil.getStringParam(json, "remarks");
        staffQuit.setQuitType(quitType);
        staffQuit.setQuitTime(DateUtil.formatTime(quitTime));
        staffQuit.setRemarks(remarks);
        //离职类型/离职日期/备注  需要前端传
        int i = this.tStaffQuitMapper.insertSelective(staffQuit);
        StringBuffer stringBuffer = new StringBuffer();
        if (i > 0) {
            stringBuffer.append("离职数据添加成功！");
        } else {
            stringBuffer.append("离职数据添加失败！");
            return new CommonResult(445, "error", stringBuffer.toString(), null);
        }
        //审批类型
        String approvalType = "11";
        stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,json,sysToken,approvalType,pkQuitId,staffInfo.getStaffName(),IDUtils.getUUID());

        return new CommonResult(200, "success", stringBuffer.toString(), null);
    }

    /**
     * 修改数据
     *
     * @param tStaffQuit
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffQuit tStaffQuit) {
        int i = this.tStaffQuitMapper.updateByPkId(tStaffQuit);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffQuit
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffQuit tStaffQuit) {
        int i = this.tStaffQuitMapper.deleteById(tStaffQuit);
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
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TStaffQuit entity = new TStaffQuit();

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
        List<TStaffQuit> list = this.tStaffQuitMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffQuit>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffQuit 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffQuit tStaffQuit) {
        String pkId = tStaffQuit.getPkQuitId();
        TStaffQuit entity = this.tStaffQuitMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffQuit entity = new TStaffQuit();
        List<TStaffQuit> list = this.tStaffQuitMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffQuit>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
