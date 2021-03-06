package com.hjy.cloud.t_staff.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_dictionary.dao.TDictionaryEducationMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryHtlxMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryNationMapper;
import com.hjy.cloud.t_dictionary.dao.TDictionaryPositionMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryEducation;
import com.hjy.cloud.t_dictionary.entity.TDictionaryHtlx;
import com.hjy.cloud.t_dictionary.entity.TDictionaryNation;
import com.hjy.cloud.t_dictionary.entity.TDictionaryPosition;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.t_staff.service.TStaffInfoService;
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
 * (TStaffInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 17:06:47
 */
@Service("tStaffInfoService")
public class TStaffInfoServiceImpl implements TStaffInfoService {

    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;
    @Resource
    private TDictionaryPositionMapper tDictionaryPositionMapper;
    @Resource
    private TDictionaryHtlxMapper tDictionaryHtlxMapper;
    @Resource
    private TDictionaryNationMapper tDictionaryNationMapper;
    @Resource
    private TDictionaryEducationMapper tDictionaryEducationMapper;
//    /**
//     * 添加前获取数据
//     *
//     * @return
//     */
//    @Override
//    public CommonResult insertPage() {
//        JSONObject jsonObject = new JSONObject();
//        //部门
//        List<TOutfitDept> depts = tOutfitDeptMapper.selectAllIdAndName();
//        //职位
//        List<TDictionaryPosition> positions = tDictionaryPositionMapper.selectAllId_Name();
//        //合同类型
//        List<TDictionaryHtlx> htlxes = tDictionaryHtlxMapper.selectAllId_Name();
//        //民族
//        List<TDictionaryNation> nations = tDictionaryNationMapper.selectAllId_Name();
//        //学历
//        List<TDictionaryEducation> educations = tDictionaryEducationMapper.selectAllId_Name();
//        //员工
//        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
//        jsonObject.put("depts", depts);
//        jsonObject.put("positions", positions);
//        jsonObject.put("htlxes", htlxes);
//        jsonObject.put("nations", nations);
//        jsonObject.put("educations", educations);
//        jsonObject.put("staffInfos", staffInfos);
//        return new CommonResult(200, "success", "获取数据成功", jsonObject);
//    }

//    /**
//     * 添加数据
//     *
//     * @param tStaffInfo
//     * @return
//     */
//    @Transactional()
//    @Override
//    public CommonResult insert(TStaffInfo tStaffInfo) {
//        tStaffInfo.setPkStaffId(IDUtils.getUUID());
//        int i = this.tStaffInfoMapper.insertSelective(tStaffInfo);
//        if (i > 0) {
//            JSONObject jsonObject = this.getListInfo();
//            return new CommonResult(200, "success", "添加数据成功", jsonObject);
//        } else {
//            return new CommonResult(444, "error", "添加数据失败", null);
//        }
//    }



    /**
     * 修改数据
     *
     * @param tStaffInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TStaffInfo tStaffInfo) {
        int i = this.tStaffInfoMapper.updateByPkId(tStaffInfo);
        if (i > 0) {
            JSONObject jsonObject = this.getListInfo();
            return new CommonResult(200, "success", "修改数据成功", jsonObject);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tStaffInfo
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TStaffInfo tStaffInfo) {
        int i = this.tStaffInfoMapper.deleteById(tStaffInfo);
        if (i > 0) {
            JSONObject jsonObject = this.getListInfo();
            return new CommonResult(200, "success", "删除数据成功", jsonObject);
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
        TStaffInfo entity = new TStaffInfo();
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
        List<TStaffInfo> list = this.tStaffInfoMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffInfo>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public List<TStaffInfo> selectAll() {
        return tStaffInfoMapper.selectAll();
    }

    /**
     * 获取单个数据
     *
     * @param tStaffInfo 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TStaffInfo tStaffInfo) {
        String pkId = tStaffInfo.getPkStaffId();
        TStaffInfo entity = this.tStaffInfoMapper.selectByPkId2(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("staffInfo", entity);
        //合同类型
        List<TDictionaryHtlx> htlxList = tDictionaryHtlxMapper.selectAllId_Name();
        resultJson.put("htlxList", htlxList);
        //民族
        List<TDictionaryNation> nationList = tDictionaryNationMapper.selectAllId_Name();
        resultJson.put("nationList", nationList);
        //学历
        List<TDictionaryEducation> educationList = tDictionaryEducationMapper.selectAllId_Name();
        resultJson.put("educationList", educationList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public String selectDeptIdByPkId(String pkStaffId) {
        return tStaffInfoMapper.selectDeptIdByPkId(pkStaffId);
    }

    @Override
    public TStaffInfo selectDeptLeader(String fkUserId) {
        return tStaffInfoMapper.selectDeptLeader(fkUserId);
    }

    @Override
    public TStaffInfo selectLeaderByPosition(String s) {
        return tStaffInfoMapper.selectLeaderByPosition(s);
    }

    @Override
    public TStaffInfo selectByPkId2(String currentSourceId) {
        return tStaffInfoMapper.selectByPkId2(currentSourceId);
    }

    private JSONObject getListInfo() {
        TStaffInfo entity = new TStaffInfo();

        PageHelper.startPage(1, 10);
        List<TStaffInfo> list = this.tStaffInfoMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TStaffInfo>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
