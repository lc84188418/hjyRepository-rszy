package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.User;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.dao.DApvRecordMapper;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.enums.ApprovaltypeEnum;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.dao.TStaffQuitMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffQuit;
import com.hjy.cloud.t_staff.service.TStaffQuitService;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private DApvRecordMapper dApvRecordMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage(HttpServletRequest request) {
//        String token = TokenUtil.getRequestToken(request);
//        if (StringUtils.isEmpty(token)){
//            return new CommonResult(444, "error", "请在请求头中传入token", null);
//
//        }
//        SysToken sysToken = tSysTokenMapper.findByToken(token);
//        if(sysToken == null){
//            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
//
//        }
//        //查询是否提交过离职申请
//        TStaffQuit tStaffQuit = tStaffQuitMapper.selectByStaffId(sysToken.getFkUserId());
//        if(tStaffQuit != null && tStaffQuit.getQuitStatus() != 2){
//            //0代表正在审批中，1通过2拒绝，拒绝的可以再次提交申请
//            return new CommonResult().ErrorResult("已提交过离职申请，无需再次提交！",null);
//        }
//        //离职审批流程信息
//        JSONObject resultJson = ObjectAsyncTask.sponsorApprovalPage(sysToken,sysToken.getFkUserId(),"离职申请",1);
//        String msg = (String) resultJson.get("msg");
//        resultJson.remove("msg");
//        return new CommonResult(200, "success", msg, resultJson);
        SysToken sysToken = tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));

        //离职人基本信息
        TStaffInfo queryInfo = new TStaffInfo();
        queryInfo.setPkStaffId(sysToken.getFkUserId());
        TStaffInfo staffInfo =tStaffInfoMapper.selectByPkId2(queryInfo);
        if(staffInfo == null){
            return new CommonResult().ErrorResult("当前你的档案已不存在，请联系管理员!",null);
        }
        //审批类型
        String approvalType = ApprovaltypeEnum.Type_11.getCode();
        //查询是否发起了离职申请审批记录
        DApvRecord select = new DApvRecord();
        select.setApprovalType(approvalType);
        select.setApplyPeopleId(staffInfo.getPkStaffId());
        select.setIsStart(1);
        List<DApvRecord> havaRecord = dApvRecordMapper.selectAllEntity(select);
        if(havaRecord != null && havaRecord.size() > 0){
            if(0 == havaRecord.get(0).getApvStatus()){
                return new CommonResult().ErrorResult("该员工已存在离职申请记录，正在审批，无需再次发起",null);
            }else if(1 == havaRecord.get(0).getApvStatus()){
                //离职审批已通过
                return new CommonResult().ErrorResult("你的离职申请已通过，已离职",null);
            }
        }
        //离职审批流程信息
        JSONObject resultJson = ObjectAsyncTask.sponsorApprovalPage(sysToken,sysToken.getFkUserId(),"离职申请",1);
        String msg = (String) resultJson.get("msg");
        resultJson.remove("msg");
        return new CommonResult(200, "success", msg, resultJson);
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
        SysToken sysToken = tSysTokenMapper.findByToken(TokenUtil.getRequestToken(request));
        String newPkId = IDUtils.getUUID();
        //离职人基本信息
        TStaffInfo queryInfo = new TStaffInfo();
        queryInfo.setPkStaffId(sysToken.getFkUserId());
        TStaffInfo staffInfo =tStaffInfoMapper.selectByPkId2(queryInfo);
        if(staffInfo == null){
            return new CommonResult().ErrorResult("当前你的档案已不存在，请联系管理员!",null);
        }
        //审批类型
        String approvalType = ApprovaltypeEnum.Type_11.getCode();
        int sponsorNum = 1;
        //查询是否发起了离职申请审批记录
        DApvRecord select = new DApvRecord();
        select.setApprovalType(approvalType);
        select.setApplyPeopleId(staffInfo.getPkStaffId());
        select.setIsStart(1);
        List<DApvRecord> havaRecord = dApvRecordMapper.selectAllEntity(select);
        if(havaRecord != null && havaRecord.size() > 0){
            if(0 == havaRecord.get(0).getApvStatus()){
                return new CommonResult().ErrorResult("该员工已存在离职申请记录，正在审批，无需再次发起",null);
            }else if(1 == havaRecord.get(0).getApvStatus()){
                //离职审批已通过
                return new CommonResult().ErrorResult("你的离职申请已通过，已离职",null);
            }else if(2 == havaRecord.get(0).getApvStatus()){
                //说明之前被拒绝，可以重复发起
                sponsorNum = havaRecord.get(0).getSponsorNum();
            }
        }
        //是否存在离职数据，以及审批流程中是否发起过
        boolean haveQuitdata = false;
        //查询是否提交过离职申请
        TStaffQuit tStaffQuit = tStaffQuitMapper.selectByStaffId(sysToken.getFkUserId());
        if(tStaffQuit != null){
            haveQuitdata = true;
        }
        /**
         * 查询该审批类型是否有审批流，如果没有，则直接通过，且无需添加审批记录
         */

        int apvStatus = 0;
        TApvApproval queryApproval = new TApvApproval();
        queryApproval.setApprovalType(approvalType);
        List<TApvApproval> apvApprovalList = this.tApvApprovalMapper.selectAllPage(queryApproval);
        if(apvApprovalList != null && apvApprovalList.size() >0){
            //判断是否为有效的审批,当未有审批人只有抄送人时看是否需要添加记录
        }else {
            //说明没有审批流直接通过，且不添加记录
            //直接通过， apvStatus = 1
            apvStatus = 1;
            newPkId = null;
        }
        JSONObject json = JSON.parseObject(param);
        //传的参数
        String quitType = JsonUtil.getStringParam(json, "quitType");
        String quitReason = JsonUtil.getStringParam(json, "quitReason");
        String quitTime = JsonUtil.getStringParam(json, "quitTime");
        String remarks = JsonUtil.getStringParam(json, "remarks");
        if(!haveQuitdata){
            tStaffQuit = new TStaffQuit();
            tStaffQuit.setPkQuitId(IDUtils.getUUID());
            tStaffQuit.setFkStaffId(staffInfo.getPkStaffId());
            tStaffQuit.setStaffName(staffInfo.getStaffName());
            tStaffQuit.setFkDeptId(staffInfo.getFkDeptId());
            tStaffQuit.setPosition(staffInfo.getFkPositionId());
            tStaffQuit.setOperatedPeople(sysToken.getFullName());
            tStaffQuit.setApplyTime(new Date());
            tStaffQuit.setQuitStatus(apvStatus);
            tStaffQuit.setQuitType(quitType);
            tStaffQuit.setQuitReason(quitReason);
            tStaffQuit.setQuitTime(DateUtil.formatTime(quitTime,"yyyy-MM-dd"));
            tStaffQuit.setRemarks(remarks);
            tStaffQuit.setApvId(newPkId);
            int i = this.tStaffQuitMapper.insertSelective(tStaffQuit);
        }else {
            tStaffQuit.setApplyTime(new Date());
            tStaffQuit.setQuitStatus(apvStatus);
            tStaffQuit.setQuitType(quitType);
            tStaffQuit.setQuitReason(quitReason);
            tStaffQuit.setQuitTime(DateUtil.formatTime(quitTime,"yyyy-MM-dd"));
            tStaffQuit.setRemarks(remarks);
            tStaffQuit.setApvId(newPkId);
            int i = this.tStaffQuitMapper.updateByPkId(tStaffQuit);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if(apvStatus == 1){
            stringBuffer.append("未有离职申请审批，已直接通过！");
            /**
             * 通过后处理员工档案，也就是修改员工的状态
             */
            ObjectAsyncTask.updateQuitData(tStaffQuit);
        }else {
            User user = new User();
            user.setUserId(staffInfo.getPkStaffId());
            user.setFullName(staffInfo.getStaffName());
            stringBuffer.append("已发起离职申请!");
            stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,json,sysToken,approvalType,tStaffQuit.getPkQuitId(),user,newPkId,sponsorNum);
        }
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
            return new CommonResult(444, "error", "数据已被删除，无需再次请求！", null);
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
        String staffName = JsonUtil.getStringParam(json, "staffName");
        int quitStatus = JsonUtil.getIntegerParam(json, "quitStatus");
        TStaffQuit entity = new TStaffQuit();
        //quitStatus = 1 代表已离职 quitStatus = 0 代表离职中
        entity.setQuitStatus(quitStatus);
        entity.setStaffName(staffName);
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
        List<TStaffQuit> list = this.tStaffQuitMapper.selectAllByEntity(entity);
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

    @Override
    public List<TStaffQuit> selectAllPage(TStaffQuit query) {
        return this.tStaffQuitMapper.selectAllPage(query);
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
    
