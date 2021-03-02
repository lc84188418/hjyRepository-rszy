package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.common.entity.DApvRecord;
import com.hjy.cloud.common.task.ObjectAsyncTask;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.dao.TApvApvtypeMapper;
import com.hjy.cloud.t_apv.dao.TApvGroupMapper;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.entity.TApvGroup;
import com.hjy.cloud.t_apv.service.TApvApprovalService;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.page.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApvApproval)表服务实现类
 *
 * @author makejava
 * @since 2021-02-26 14:50:48
 */
@Service("tApvApprovalService")
public class TApvApprovalServiceImpl implements TApvApprovalService {

    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private TApvApvtypeMapper tApvApvtypeMapper;
    @Resource
    private TApvGroupMapper tApvGroupMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        //审批类型
        List<TApvApvtype> apvtypes = tApvApvtypeMapper.selectAllId_Name();
        jsonObject.put("apvtypes", apvtypes);
        //员工ID
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
        jsonObject.put("staffInfos", staffInfos);
        //所有末尾的审批流程,
        List<TApvApproval> approvals = tApvApprovalMapper.selectAllEnding();
        jsonObject.put("approvals", approvals);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tApvApproval
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TApvApproval tApvApproval) {
        String newPkId = IDUtils.getUUID();
        //判断是否为添加末尾审批流程
        String nextApproval = tApvApproval.getNextApproval();
        int isEnding = tApvApproval.getIsEnding();
        int i = 0;
        int j = 0;
        if(isEnding == 1){
            //先将末尾的审批流程isEnding改为0
            j = ObjectAsyncTask.updateAPV_isending(tApvApproval.getPkApprovalId());
            //再添加末尾审批流程
            TApvApproval lastTypeData = tApvApprovalMapper.selectByPkId(tApvApproval.getPkApprovalId());
            tApvApproval.setPkApprovalId(newPkId);
            tApvApproval.setApprovalType(lastTypeData.getApprovalType());
            tApvApproval.setNextApproval("0");
            tApvApproval.setDataType(lastTypeData.getDataType());
            tApvApproval.setIsEnding(1);
            tApvApproval.setIsStart(0);
            i = tApvApprovalMapper.insertSelective(tApvApproval);
        }else {
            //添加新的审批流程
            //审批类型
            String apvType = tApvApproval.getApprovalType();
            //查询该审批类型是否已添加过数据
            TApvApproval lastTypeData = tApvApprovalMapper.selectByType(apvType);
            tApvApproval.setIsStart(1);
            tApvApproval.setIsEnding(1);
            tApvApproval.setNextApproval("0");
            if(lastTypeData != null){
                //说明之前已经添加过该数据，则添加新的该审批流程
                tApvApproval.setDataType(lastTypeData.getDataType()+1);
            }else {
                tApvApproval.setDataType(1);
            }
            i = this.tApvApprovalMapper.insertSelective(tApvApproval);
        }
        if (i > 0 && j > 0) {
            return new CommonResult(200, "success", "该审批流程已增加新审批人！", null);
        } else if(i > 0){
            return new CommonResult(201, "success", "新审批流程添加成功！", null);
        }else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvApproval
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TApvApproval tApvApproval) {
        int i = this.tApvApprovalMapper.updateByPkId(tApvApproval);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApproval
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TApvApproval tApvApproval) {
        int i = this.tApvApprovalMapper.deleteById(tApvApproval);
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
        TApvApproval entity = new TApvApproval();

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
        List<TApvApproval> list = this.tApvApprovalMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApproval>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tApvApproval 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TApvApproval tApvApproval) {
        String pkId = tApvApproval.getPkApprovalId();
        TApvApproval entity = this.tApvApprovalMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    @Override
    public DApvRecord selectApvRecordById(String pkRecordId) {
        return this.tApvApprovalMapper.selectApvRecordById(pkRecordId);
    }
    @Override
    public int updateAPV_isending(TApvApproval entity) {
        return tApvApprovalMapper.updateByPkId(entity);
    }

    /**
     * 删除与一级审批相关联的审批数据
     * @param sourceId
     * @return
     */
    @Override
    public int deleteApvRecordBySourceId(String sourceId) {
        return tApvApprovalMapper.deleteApvRecordBySourceId(sourceId);

    }
    /**
     * 待审批,是指所有没有审批完成的记录，非操作用户自己的
     *
     */
    @Override
    public CommonResult waitApv() {
        /**
         *
         */
        List<DApvRecord> waitApvRecords = tApvApprovalMapper.waitApv();
        return null;
    }
    /**
     * 审批设置
     *
     * @return 修改结果
     */
    @Override
    public CommonResult approvalSet() {
        /**
         * 审批分组
         */
        TApvGroup group = new TApvGroup();
        List<TApvGroup> groupList = tApvGroupMapper.selectAllPage(group);
        /**
         * 审批类型
         */
        TApvApvtype tApvApvtype = new TApvApvtype();
        List<TApvApvtype> tApvTypeList = tApvApvtypeMapper.selectAllPage(tApvApvtype);
        JSONObject resultJson = new JSONObject();
        resultJson.put("groupList", groupList);
        resultJson.put("typeList", tApvTypeList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TApvApproval entity = new TApvApproval();
        List<TApvApproval> list = this.tApvApprovalMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApproval>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    