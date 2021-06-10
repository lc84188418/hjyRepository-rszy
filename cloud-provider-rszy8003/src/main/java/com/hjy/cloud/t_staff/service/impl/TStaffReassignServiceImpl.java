package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.enums.ApprovaltypeEnum;
import com.hjy.cloud.t_dictionary.dao.TDictionaryPositionMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.t_outfit.dao.TOutfitCompanyMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitWorkaddressMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.dao.TStaffReassignMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.result.ReassignApprovalResult;
import com.hjy.cloud.t_staff.service.TStaffReassignService;
import com.hjy.cloud.t_system.entity.SysToken;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * (TStaffReassign)表服务实现类
 *
 * @author makejava
 * @since 2021-03-01 15:53:16
 */
@Service("tStaffReassignService")
public class TStaffReassignServiceImpl implements TStaffReassignService {

    @Resource
    private TStaffReassignMapper tStaffReassignMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    @Resource
    private TOutfitCompanyMapper tOutfitCompanyMapper;
    @Resource
    private TOutfitWorkaddressMapper tOutfitWorkaddressMapper;
    @Resource
    private TDictionaryPositionMapper tDictionaryPositionMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage(TStaffReassign tStaffReassign) {
        String fkStaffId = tStaffReassign.getFkStaffId();
        if(StringUtils.isEmpty(fkStaffId)){
            return new CommonResult().ErrorResult("员工id fkStaffId不能为空！",null);
        }
        /**
         * 查询员工是否已存在调动申请，即apvStatus=3
         */
        TStaffReassign queryReassign = new TStaffReassign();
        queryReassign.setApvStatus(3);
        queryReassign.setFkStaffId(fkStaffId);
        int i1 = tStaffReassignMapper.selectCountByEntity(queryReassign);
        if(i1 > 0){
            return new CommonResult().ErrorResult("该员工已存在调动申请，未发起审批！",null);
        }
        /**
         * 查询员工是否还有在审批中的调动申请
         */
        queryReassign.setApvStatus(0);
        int i2 = tStaffReassignMapper.selectCountByEntity(queryReassign);
        if(i2 > 0){
            return new CommonResult().ErrorResult("当前还有审批中的调动申请，请等待审批，无需再次提交!",null);
        }
        JSONObject jsonObject = new JSONObject();
        //部门
        List<TOutfitDept> depts = tOutfitDeptMapper.selectAllIdAndName();
        //职位
        List<TDictionaryPosition> positions = tDictionaryPositionMapper.selectAllId_Name();
        //工作地
        List<TOutfitWorkaddress> workaddresses = tOutfitWorkaddressMapper.selectAllId_Name();
        //合同公司
        List<TOutfitCompany> companies = tOutfitCompanyMapper.select_PkId_name();
        jsonObject.put("depts", depts);
        jsonObject.put("positions", positions);
        jsonObject.put("workaddresses", workaddresses);
        jsonObject.put("companies", companies);
        /**
         * 当前员工调动前信息
         */
        TStaffReassign resultEntity = tStaffReassignMapper.selectStaffOldInfoByStaffId(fkStaffId);
        jsonObject.put("staffInfo", resultEntity);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tStaffReassign
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TStaffReassign tStaffReassign) {
        String fkStaffId = tStaffReassign.getFkStaffId();
        if(StringUtils.isEmpty(tStaffReassign.getReassignDeptId())
                || StringUtils.isEmpty(tStaffReassign.getReassignPosition())
                || StringUtils.isEmpty(tStaffReassign.getReassignAddress())
        ){
            return new CommonResult().ErrorResult("调动部门、职位、工作地不能为空！",null);
        }
        /**
         * 查询员工是否已存在调动申请，即apvStatus=3
         */
        TStaffReassign queryReassign = new TStaffReassign();
        queryReassign.setApvStatus(3);
        queryReassign.setFkStaffId(fkStaffId);
        int i1 = tStaffReassignMapper.selectCountByEntity(queryReassign);
        if(i1 > 0){
            return new CommonResult().ErrorResult("该员工已存在调动申请，未发起审批！",null);
        }
        /**
         * 查询员工是否还有在审批中的调动申请
         */
        queryReassign.setApvStatus(0);
        int i2 = tStaffReassignMapper.selectCountByEntity(queryReassign);
        if(i2 > 0){
            return new CommonResult().ErrorResult("当前还有审批中的调动申请，请等待审批，无需再次提交!",null);
        }
        String pkReassignId = IDUtils.getUUID();
        tStaffReassign.setPkReassignId(pkReassignId);
        tStaffReassign.setStartTime(new Date());
        tStaffReassign.setApvStatus(3);
        //处理合同公司
        //员工档案信息
        TStaffInfo queryInfo = new TStaffInfo();
        queryInfo.setPkStaffId(fkStaffId);
        TStaffInfo staffInfo = this.tStaffInfoMapper.selectByPkId2(queryInfo);
        if(staffInfo == null){
            return new CommonResult().ErrorResult("系统已不存在该员工档案，请检查！",null);
        }
        tStaffReassign.setOldDeptId(staffInfo.getFkDeptId());
        tStaffReassign.setOldPosition(staffInfo.getFkPositionId());
        tStaffReassign.setOldAddress(staffInfo.getFkWorkaddressId());
        //处理合同公司
        TOutfitDept dept = this.tOutfitDeptMapper.selectByPkId(staffInfo.getFkDeptId());
        if(dept == null){
            return new CommonResult().ErrorResult("原部门信息不存在，请检查！",null);
        }
        tStaffReassign.setOldCompany(dept.getSuperiorDept());

        TOutfitDept dept2 = this.tOutfitDeptMapper.selectByPkId(tStaffReassign.getReassignDeptId());
        if(dept2 == null){
            return new CommonResult().ErrorResult("调动后部门信息不存在，请检查！",null);
        }
        tStaffReassign.setReassignCompany(dept2.getSuperiorDept());
        int i = this.tStaffReassignMapper.insertSelective(tStaffReassign);
        return new CommonResult(200, "success", "员工调动数据添加成功，待发起审批", pkReassignId);

    }

    /**
     * 修改数据
     *
     * @param tStaffReassign
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffReassign tStaffReassign) {
        int i = this.tStaffReassignMapper.updateByPkId(tStaffReassign);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffReassign
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffReassign tStaffReassign) {
        int i = this.tStaffReassignMapper.deleteByPkId(tStaffReassign.getPkReassignId());
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
        String staffName = JsonUtil.getStringParam(json, "staffName");
        String reassignType = JsonUtil.getStringParam(json, "reassignType");
        String oldDeptName = JsonUtil.getStringParam(json, "oldDeptName");
        String oldPositionName = JsonUtil.getStringParam(json, "oldPositionName");
        TStaffReassign entity = new TStaffReassign();
        entity.setStaffName(staffName);
        entity.setReassignType(reassignType);
        entity.setOldDeptName(oldDeptName);
        entity.setOldPositionName(oldPositionName);
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
        List<TStaffReassign> list = this.tStaffReassignMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffReassign>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tStaffReassign 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffReassign tStaffReassign) {
        List<TStaffReassign> list = this.tStaffReassignMapper.selectAllPage(tStaffReassign);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", list.get(0));
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    /**
     * 管理员发起转正审批页面
     *
     * @return 修改结果
     */
    @Override
    public CommonResult initiateApvPage(HttpServletRequest request,TStaffReassign tStaffReassign) {
        TStaffReassign i1 = tStaffReassignMapper.selectByPkId(tStaffReassign.getPkReassignId());
        if(i1 != null && !StringUtils.isEmpty(i1.getFirstApvrecordId())){
            return new CommonResult().ErrorResult("当前调动申请已发起审批，无需再次发起！",null);
        }
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        if(sysToken == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
        }
        JSONObject resultJson = ObjectAsyncTask.sponsorApprovalPage(sysToken,tStaffReassign.getPkReassignId(),"调动申请",1);
        String msg = (String) resultJson.get("msg");
        resultJson.remove("msg");
        return new CommonResult(200, "success", msg, resultJson);
    }
    /**
     * 管理员发起调动审批
     *
     * @return 修改结果
     */
    @Transactional()
    @Override
    public CommonResult initiateApv(HttpServletRequest request,String param) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        if(sysToken == null){
            return new CommonResult().ErrorResult("token已失效！",null);
        }
        String newPkId = IDUtils.getUUID();
        JSONObject jsonObject = JSON.parseObject(param);
        //调动申请信息主键
        String pkReassignId = JsonUtil.getStringParam(jsonObject, "pkReassignId");
        if(StringUtils.isEmpty(pkReassignId)){
            return new CommonResult().ErrorResult("请传入调动信息！",null);
        }
        TStaffReassign query = new TStaffReassign();
        query.setPkReassignId(pkReassignId);
        List<TStaffReassign> reassigns = this.tStaffReassignMapper.selectAllPage(query);
        if(reassigns == null || reassigns.size() <= 0){
            throw new RuntimeException("当前调动信息已不存在，或被异常删除，请刷新后重试！");
        }else {
            query = reassigns.get(0);
        }
        //审批类型
        String approvalType = ApprovaltypeEnum.Type_8.getCode();
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
        /**
         * 修改调动信息表中的apvId
         */
        TStaffReassign reassign = new TStaffReassign();
        reassign.setPkReassignId(pkReassignId);
        reassign.setFirstApvrecordId(newPkId);
        //审批状态,0审批中，1审批通过，2审批被拒绝,3未发起审批
        reassign.setApvStatus(apvStatus);
        int i = tStaffReassignMapper.updateByPkId(reassign);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            if(apvStatus == 1){
                stringBuffer.append("未有调动申请审批，已直接通过！");
                /**
                 * 通过后处理员工档案，也就是修改调动后的信息
                 */
                ObjectAsyncTask.updateReassignData(query);
                return new CommonResult(201, "success", stringBuffer.toString(), null);
            }else {
                stringBuffer.append("已发起调动申请成功!");
                stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,jsonObject,sysToken,approvalType,pkReassignId,query.getStaffName(),newPkId);
            }
        }
        return new CommonResult(200, "success", stringBuffer.toString(), null);
    }

    @Override
    public CommonResult<ReassignApprovalResult> userInitiateApvPage(HttpServletRequest request) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        /**
         * 查询员工是否已存在调动申请，即apvStatus=3
         */
        int i1 = tStaffReassignMapper.selectCountByStaff_ApvStatus(sysToken.getFkUserId());
        if(i1 > 0){
            return new CommonResult().ErrorResult("已有调动申请或正在审批中，无需再次提交！",null);
        }
        //部门
        List<TOutfitDept> depts = tOutfitDeptMapper.selectAllIdAndName();
        //职位
        List<TDictionaryPosition> positions = tDictionaryPositionMapper.selectAllId_Name();
        //工作地
        List<TOutfitWorkaddress> workaddresses = tOutfitWorkaddressMapper.selectAllId_Name();
        //合同公司
        List<TOutfitCompany> companies = tOutfitCompanyMapper.select_PkId_name();
        /**
         * 当前员工调动前信息
         */
        TStaffReassign resultEntity = tStaffReassignMapper.selectStaffOldInfoByStaffId(sysToken.getFkUserId());
        //审批流程的数据
        JSONObject resultJson = ObjectAsyncTask.sponsorApprovalPage(sysToken,null,"调动申请",1);
        String msg = (String) resultJson.get("msg");
        resultJson.remove("msg");
        resultJson.put("depts", depts);
        resultJson.put("positions", positions);
        resultJson.put("workaddresses", workaddresses);
        resultJson.put("companies", companies);
        resultJson.put("staffInfo", resultEntity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public CommonResult userInitiateApv(HttpServletRequest request, String param) throws ParseException {
        //调动信息
        JSONObject json = JSON.parseObject(param);
        Date reassignTime = JsonUtil.getDateParam(json, "yyy-MM-dd","reassignTime");
        String reassignType = JsonUtil.getStringParam(json, "reassignType");
        String reassignDeptId = JsonUtil.getStringParam(json, "reassignDeptId");
        String reassignPosition = JsonUtil.getStringParam(json, "reassignPosition");
        String reassignAddress = JsonUtil.getStringParam(json, "reassignAddress");
        String reassignReason = JsonUtil.getStringParam(json, "reassignReason");
        if(StringUtils.isEmpty(reassignDeptId)
                || StringUtils.isEmpty(reassignPosition)
                || StringUtils.isEmpty(reassignAddress)
        ){
            return new CommonResult().ErrorResult("调动部门、职位、工作地不能为空！",null);
        }
        //
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        String newPkId = IDUtils.getUUID();
        //离职人基本信息
        TStaffInfo queryInfo = new TStaffInfo();
        queryInfo.setPkStaffId(sysToken.getFkUserId());
        TStaffInfo staffInfo =tStaffInfoMapper.selectByPkId2(queryInfo);
        //查询是否提交过申请
        int i1 = tStaffReassignMapper.selectCountByStaff_ApvStatus(sysToken.getFkUserId());
        if(i1 > 0){
            return new CommonResult().ErrorResult("已有调动申请或正在审批中，无需再次提交！",null);
        }
        /**
         * 查询该审批类型是否有审批流，如果没有，则直接通过，且无需添加审批记录
         */
        //审批类型
        String approvalType = ApprovaltypeEnum.Type_8.getCode();
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

        //添加数据
        TStaffReassign reassign = new TStaffReassign();
        String pkReassignId = IDUtils.getUUID();
        reassign.setPkReassignId(pkReassignId);
        reassign.setFkStaffId(sysToken.getFkUserId());
        reassign.setFirstApvrecordId(newPkId);
        //审批状态,0审批中，1审批通过，2审批被拒绝,3未发起审批
        reassign.setApvStatus(apvStatus);
        reassign.setStartTime(new Date());
        reassign.setOldDeptId(staffInfo.getFkDeptId());
        reassign.setOldPosition(staffInfo.getFkPositionId());
        reassign.setOldAddress(staffInfo.getFkWorkaddressId());
        //公司没有
        //处理合同公司
        TOutfitDept dept = this.tOutfitDeptMapper.selectByPkId(staffInfo.getFkDeptId());
        if(dept == null){
            return new CommonResult().ErrorResult("原部门信息不存在，请检查！",null);
        }
        reassign.setOldCompany(dept.getSuperiorDept());

        TOutfitDept dept2 = this.tOutfitDeptMapper.selectByPkId(reassignDeptId);
        if(dept2 == null){
            return new CommonResult().ErrorResult("调动后部门信息不存在，请检查！",null);
        }
        reassign.setReassignCompany(dept2.getSuperiorDept());
        reassign.setReassignTime(reassignTime);
        reassign.setReassignType(reassignType);
        reassign.setReassignDeptId(reassignDeptId);
        reassign.setReassignPosition(reassignPosition);
        reassign.setReassignAddress(reassignAddress);
        reassign.setReassignReason(reassignReason);
        int i = tStaffReassignMapper.insertSelective(reassign);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            if(apvStatus == 1){
                stringBuffer.append("未有调动申请审批，已直接通过！");
                /**
                 * 通过后处理员工档案，也就是修改调动后的信息
                 */
                ObjectAsyncTask.updateReassignData(reassign);
                return new CommonResult(201, "success", stringBuffer.toString(), null);
            }else {
                stringBuffer.append("已发起调动申请成功!");
                stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,json,sysToken,approvalType,pkReassignId,reassign.getStaffName(),newPkId);
            }
        }
        return new CommonResult(200, "success", stringBuffer.toString(), null);
    }

    @Override
    public List<TStaffReassign> selectAllPage(TStaffReassign selectEntity) {
        return tStaffReassignMapper.selectAllPage(selectEntity);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TStaffReassign entity = new TStaffReassign();
        List<TStaffReassign> list = this.tStaffReassignMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffReassign>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
