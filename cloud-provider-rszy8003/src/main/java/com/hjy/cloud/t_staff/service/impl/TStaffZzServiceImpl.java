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
import com.hjy.cloud.t_staff.dao.TStaffEntryMapper;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.dao.TStaffZzMapper;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffZz;
import com.hjy.cloud.t_staff.service.TStaffZzService;
import com.hjy.cloud.t_system.dao.TSysParamMapper;
import com.hjy.cloud.t_system.dao.TSysTokenMapper;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.DateUtil;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.TokenUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * (TStaffZz)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:18
 */
@Service("tStaffZzService")
public class TStaffZzServiceImpl implements TStaffZzService {

    @Resource
    private TStaffZzMapper tStaffZzMapper;
    @Resource
    private TStaffEntryMapper tStaffEntryMapper;
    @Resource
    private TSysTokenMapper tSysTokenMapper;
    @Resource
    private TSysParamMapper tSysParamMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private DApvRecordMapper dApvRecordMapper;
    /**
     * 修改数据
     *
     * @param tStaffZz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffZz tStaffZz) {
        int i = this.tStaffZzMapper.updateByPkId(tStaffZz);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffZz
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffZz tStaffZz) {
        int i = this.tStaffZzMapper.deleteById(tStaffZz);
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
    public CommonResult selectAllEd(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TStaffZz entity = new TStaffZz();
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
        entity.setApvStatus(1);
        List<TStaffZz> list = this.tStaffZzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffZz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public CommonResult selectAllIng(String param) {
        JSONObject json = JSON.parseObject(param);
        //查询条件
        String pageNumStr = JsonUtil.getStringParam(json, "pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json, "pageSize");
        TStaffZz entity = new TStaffZz();
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
        entity.setApvStatus(0);
        List<TStaffZz> list = this.tStaffZzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffZz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 获取单个数据
     *
     * @param tStaffZz 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffZz tStaffZz) {
        String pkId = tStaffZz.getPkZzId();
        TStaffZz entity = this.tStaffZzMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffZz entity = new TStaffZz();
        List<TStaffZz> list = this.tStaffZzMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffZz>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
    /**
     * 发起转正审批页面
     *
     * @return 修改结果
     */
    @Override
    public CommonResult initiateZzPage(HttpServletRequest request) {
        String token = TokenUtil.getRequestToken(request);
        if (StringUtils.isEmpty(token)){
            return new CommonResult(444, "error", "请在请求头中传入token", null);
        }
        SysToken sysToken = tSysTokenMapper.findByToken(token);
        if(sysToken == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
        }
        //查询自己是否提交过转正申请
        TStaffZz tStaffZz = tStaffZzMapper.selectByStaffId(sysToken.getFkUserId());
        if(tStaffZz != null && tStaffZz.getApvStatus() != 2){
            //0代表正在审批中，1通过2拒绝，拒绝的可以再次提交申请
            return new CommonResult().ErrorResult("已提交过转正申请，无需再次提交！",null);
        }
        StringBuilder stringBuilder = new StringBuilder();
        //查询自己转正时间是否已到
        boolean flag = false;
        TStaffEntry entry = this.tStaffEntryMapper.selectByPkId(sysToken.getFkUserId());
        if(entry == null){
            TStaffInfo queryInfo = new TStaffInfo();
            queryInfo.setPkStaffId(sysToken.getFkUserId());
            TStaffInfo info = this.tStaffInfoMapper.selectByPkId2(queryInfo);
            if(info == null){
                return new CommonResult().ErrorResult("你的入职信息,档案信息已被异常移除，请联系负责人！",null);
            }else {
                //判断时间是否满足转正
                flag = DateUtil.whetherZZ(info.getEntryTime(),new Date());
            }
        }else {
            //判断时间是否满足转正
            flag = DateUtil.whetherZZ(entry.getEntryTime(),new Date());
        }
        if(!flag){
            stringBuilder.append("你还没到转正时间，是否申请！");
        }
        JSONObject resultJson = ObjectAsyncTask.sponsorApprovalPage(sysToken,sysToken.getFkUserId(),"转正申请",1);
        String msg = (String) resultJson.get("msg");
        stringBuilder.append(msg);
        resultJson.remove("msg");
        return new CommonResult(200, "success", stringBuilder.toString(), resultJson);
    }
    /**
     * 发起转正审批页面
     *
     * @return 修改结果
     */
    @Transactional()
    @Override
    public CommonResult initiateZz(HttpServletRequest request,String param)throws Exception {
        String token = TokenUtil.getRequestToken(request);
        SysToken sysToken = tSysTokenMapper.findByToken(token);
        if(sysToken == null){
            return new CommonResult(444, "error", "未传入token或已失效", null);
        }
        /**
         * 查询是否提交过转正申请
         */
        //查询自己是否提交过转正申请
        TStaffZz isProve = tStaffZzMapper.selectByStaffId(sysToken.getFkUserId());
        if(isProve != null){
            return new CommonResult(445, "error", "已提交过转正申请，不可再次提交!", null);
        }
        JSONObject jsonObject = JSON.parseObject(param);
        //入职申请信息主键
        String pkEntryId = sysToken.getFkUserId();
        //审批类型
        String approvalType = ApprovaltypeEnum.Type_13.getCode();
        int sponsorNum = 1;
        /**
         * 先判断是否已发起过入职审批
         */
        DApvRecord select = new DApvRecord();
        select.setApprovalType(approvalType);
        select.setApplyPeopleId(pkEntryId);
        List<DApvRecord> havaRecord = dApvRecordMapper.selectAllEntity(select);
        if(havaRecord != null && havaRecord.size() > 0){
            if(havaRecord.get(0).getApvStatus() != 2){
                //说明之前被拒绝，可以重复发起
                return new CommonResult().ErrorResult("该员工已存在转正申请记录，无需再次发起",null);
            }else {
                sponsorNum = havaRecord.get(0).getSponsorNum();
            }
        }
        /**
         * 查询该审批类型是否有审批流，如果没有，则直接通过，且无需添加审批记录
         */
        String newPkId = IDUtils.getUUID();
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
        /**
         * 添加转正信息到数据库表中
         */
        TStaffEntry entry = tStaffEntryMapper.selectByPkId(pkEntryId);
        TStaffZz staffZz = new TStaffZz();
        staffZz.setPkZzId(pkEntryId);
        staffZz.setFkStaffId(pkEntryId);
        staffZz.setFkWordaddressId(entry.getWorkAddress());
        staffZz.setEntryTime(entry.getEntryTime());
        //试用期到期日
        String syqsj = "3";
        String value = tSysParamMapper.selectParamById("SYQSJ");
        if(!StringUtils.isEmpty(value)){
            syqsj = value;
        }
        Date syqsjdate = DateUtil.addSYQTime(entry.getEntryTime(),syqsj);
        staffZz.setSyqdqTime(syqsjdate);
        //转正日期
        String zzsj = "3";
        String value2 = tSysParamMapper.selectParamById("ZZSJ");
        if(!StringUtils.isEmpty(value2)){
            zzsj = value2;
        }
        Date zzsjdate = DateUtil.addSYQTime(entry.getEntryTime(),zzsj);
        staffZz.setZzTime(zzsjdate);
        //实际转正日期在审批通过后进行修改
        staffZz.setStatus(0);
        //转正审批状态,0代表正在审批中，1通过,2拒绝
        staffZz.setApvStatus(apvStatus);
        staffZz.setFirstApvrecordId(newPkId);
        int i = tStaffZzMapper.insertSelective(staffZz);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            if(apvStatus == 1){
                stringBuffer.append("未有转正申请审批，已直接通过！");
                /**
                 * 通过后处理员工档案，也就是修改调动后的信息
                 */
                ObjectAsyncTask.updateZZData(staffZz);
                return new CommonResult(200, "success", stringBuffer.toString(), null);
            }else {
                stringBuffer.append("转正申请已发起成功!");
                User user = new User(entry.getPkEntryId(),entry.getStaffName());
                stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,jsonObject,sysToken,approvalType,staffZz.getFkStaffId(),user,newPkId,sponsorNum);
            }
        }
        return new CommonResult(200, "success", stringBuffer.toString(), null);
    }

    @Override
    public List<TStaffZz> selectAllPage(TStaffZz query) {
        return this.tStaffZzMapper.selectAllPage(query);
    }
}
    
