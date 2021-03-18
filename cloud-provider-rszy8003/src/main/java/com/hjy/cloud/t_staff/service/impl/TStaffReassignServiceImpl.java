package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_dictionary.dao.TDictionaryPositionMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.t_outfit.dao.TOutfitCompanyMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.dao.TOutfitWorkaddressMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitCompany;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_staff.dao.TStaffReassignMapper;
import com.hjy.cloud.t_staff.entity.TStaffEntry;
import com.hjy.cloud.t_staff.entity.TStaffReassign;
import com.hjy.cloud.t_staff.service.TStaffReassignService;
import com.hjy.cloud.t_system.entity.SysToken;
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
        /**
         * 查询员工是否还有在审批中的调动申请
         */
        TStaffReassign temp = tStaffReassignMapper.selectByStaffId_ApvStatus(fkStaffId);
        if(temp != null){
            return new CommonResult(444, "error", "当前还有审批中的调动申请，请等待审批，无需再次提交", null);
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
        /**
         * 查询员工是否还有在审批中的调动申请
         */
        TStaffReassign temp = tStaffReassignMapper.selectByStaffId_ApvStatus(tStaffReassign.getFkStaffId());
        if(temp != null){
            return new CommonResult(445, "error", "当前还有审批中的调动申请，请等待审批，无需再次提交", null);
        }
        tStaffReassign.setPkReassignId(IDUtils.getUUID());
        tStaffReassign.setStartTime(new Date());
        tStaffReassign.setApvStatus(0);
        //调动时间
        int i = this.tStaffReassignMapper.insertSelective(tStaffReassign);
        if (i > 0) {
            return new CommonResult(200, "success", "员工调动数据添加成功，待发起审批", null);
        } else {
            return new CommonResult(444, "error", "员工调动数据添加失败", null);
        }
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
        int i = this.tStaffReassignMapper.deleteById(tStaffReassign);
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
     * 发起转正审批页面
     *
     * @return 修改结果
     */
    @Override
    public CommonResult initiateApvPage(HttpServletRequest request,TStaffReassign tStaffReassign) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        if(sysToken == null){
            return new CommonResult(444, "error", "token已失效，请重新登录后再试", null);
        }
        JSONObject resultJson = ObjectAsyncTask.handleApproval(sysToken,tStaffReassign.getPkReassignId(),"调动申请",1);
        String msg = (String) resultJson.get("msg");
        resultJson.remove("msg");
        return new CommonResult(200, "success", msg, resultJson);
    }
    /**
     * 发起调动审批
     *
     * @return 修改结果
     */
    @Transactional()
    @Override
    public CommonResult initiateApv(HttpServletRequest request,String param) {
        SysToken sysToken = ObjectAsyncTask.getSysToken(request);
        String newPkId = IDUtils.getUUID();
        JSONObject jsonObject = JSON.parseObject(param);
        //调动申请信息主键
        String pkReassignId = JsonUtil.getStringParam(jsonObject, "pkReassignId");
        //审批类型
        String approvalType = JsonUtil.getStringParam(jsonObject, "approvalType");
        int apvStatus = 0;
        if(StringUtils.isEmpty(approvalType)){
            //直接通过
            newPkId = null;
            apvStatus = 1;
        }else {
            apvStatus = 2;
        }
        /**
         * 修改调动信息表中的apvId
         */
        TStaffReassign reassign = new TStaffReassign();
        reassign.setPkReassignId(pkReassignId);
        reassign.setFirstApvrecordId(newPkId);
        //状态,0代表刚添加完成调动信息，2代表已发起调动审批，正在审批中，1代表审批完成
        reassign.setApvStatus(apvStatus);
        int i = tStaffReassignMapper.updateByPkId(reassign);
        StringBuffer stringBuffer = new StringBuffer();
        if(i > 0){
            if(apvStatus == 1){
                stringBuffer.append("未有调动申请审批，已直接通过！");
                return new CommonResult(201, "success", stringBuffer.toString(), null);
            }else {
                stringBuffer.append("已发起调动申请成功!");
                String applyPeople = tStaffReassignMapper.selectStaffName(pkReassignId);
                stringBuffer = ObjectAsyncTask.addApprovalRecord(stringBuffer,jsonObject,sysToken,approvalType,pkReassignId,applyPeople);
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
    
