package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_apv.dao.TApvApprovalMapper;
import com.hjy.cloud.t_apv.dao.TApvApvtypeMapper;
import com.hjy.cloud.t_apv.entity.TApvApproval;
import com.hjy.cloud.t_apv.entity.TApvApvtype;
import com.hjy.cloud.t_apv.service.TApvApvtypeService;
import com.hjy.cloud.t_dictionary.dao.TDictionaryFileMapper;
import com.hjy.cloud.t_dictionary.entity.TDictionaryFile;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_staff.dao.TStaffInfoMapper;
import com.hjy.cloud.t_staff.entity.TStaffInfo;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.StringUtil;
import com.hjy.cloud.utils.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * (TApvApvtype)表服务实现类
 *
 * @author makejava
 * @since 2021-02-26 14:50:49
 */
@Service("tApvApvtypeService")
public class TApvApvtypeServiceImpl implements TApvApvtypeService {

    @Resource
    private TApvApvtypeMapper tApvApvtypeMapper;
    @Resource
    private TApvApprovalMapper tApvApprovalMapper;
    @Resource
    private TStaffInfoMapper tStaffInfoMapper;
    @Resource
    private TDictionaryFileMapper tDictionaryFileMapper;
    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.cloud.application.ip}")
    private String webIp;
    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        List<TDictionaryFile> list = this.tDictionaryFileMapper.selectAll();
        /**
         * 处理文件路径
         */
        Iterator<TDictionaryFile> it = list.iterator();
        while(it.hasNext()){
            StringBuffer filePath = new StringBuffer();
            TDictionaryFile obj = it.next();
            filePath.append("http://"+webIp+":"+serverPort+"/img/"+obj.getFilePath());
            obj.setFilePath(filePath.toString());
        }
        jsonObject.put("iconList", list);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param tApvApvtype
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TApvApvtype tApvApvtype) {
        int i = this.tApvApvtypeMapper.insertSelective(tApvApvtype);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param tApvApvtype
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TApvApvtype tApvApvtype) {
        /**
         * 图标路径去掉ip地址
         */
        if(!StringUtils.isEmpty(tApvApvtype.getIconPath())){
            String iconPath = tApvApvtype.getIconPath().replace("http://"+webIp+":"+serverPort+"/img/","");
            tApvApvtype.setIconPath(iconPath);
        }
        int i = this.tApvApvtypeMapper.updateByPkId(tApvApvtype);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tApvApvtype
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TApvApvtype tApvApvtype) {
//        int i = this.tApvApvtypeMapper.deleteById(tApvApvtype);
        int i = 0;
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
        TApvApvtype entity = new TApvApvtype();

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
        List<TApvApvtype> list = this.tApvApvtypeMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApvtype>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tApvApvtype 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TApvApvtype tApvApvtype) {
        String pkId = tApvApvtype.getPkApvtypeId();
        TApvApvtype entity = this.tApvApvtypeMapper.selectByPkId(pkId);
        /**
         * 处理文件路径
         */
        entity.setIconPath("http://"+webIp+":"+serverPort+"/img/"+entity.getIconPath());
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        /**
         *
         */
        List<TDictionaryFile> list = this.tDictionaryFileMapper.selectAll();
        if(list.size() > 0){
            /**
             * 处理文件路径
             */
            Iterator<TDictionaryFile> it = list.iterator();
            while(it.hasNext()){
                StringBuffer filePath = new StringBuffer();
                TDictionaryFile obj = it.next();
                filePath.append("http://"+webIp+":"+serverPort+"/img/"+obj.getFilePath());
                obj.setFilePath(filePath.toString());
            }
        }
        resultJson.put("iconList", list);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    @Override
    public TApvApvtype selectByName(String apvName) {
        return tApvApvtypeMapper.selectByName(apvName);
    }
    /**
     * 查询审批流程详情
     *
     * @param tApvApvtype 实体对象
     * @return 修改结果
     */
    @Override
    public CommonResult apvProcessDetail(TApvApvtype tApvApvtype) {
        JSONObject resultJson = new JSONObject();
        /**
         *所有员工
         */
        List<TStaffInfo> staffInfos = tStaffInfoMapper.selectAllId_Name();
        resultJson.put("staffInfos",staffInfos);
        /**
         * 所有审批类型
         */
        List<TApvApvtype> apvtypes = tApvApvtypeMapper.selectAllId_Name();
        resultJson.put("apvtypes",apvtypes);

        /**
         * 抄送人
         */
        TApvApproval selectEntity = new TApvApproval();
        selectEntity.setApvStation("csr");
        selectEntity.setApprovalType(tApvApvtype.getPkApvtypeId());
        List<TApvApproval> csrList = tApvApprovalMapper.selectAllHandle(selectEntity);
        resultJson.put("csrList",csrList);
        /**
         * 审批人
         */
        List<TApvApproval> approvals = tApvApprovalMapper.selectApvByType(tApvApvtype.getPkApvtypeId());
        //处理审批流程数据
        List<TApvApproval> apvList = this.handleData(approvals);
        resultJson.put("apvList",apvList);
        return new CommonResult(200, "success", "获取数据成功", resultJson);

    }

    private List<TApvApproval> handleData(List<TApvApproval> approvals) {
        if(approvals != null && approvals.size() > 0){
            List<TApvApproval> resultList = new ArrayList<>();
            String nextApproval = null;
            Iterator<TApvApproval> first = approvals.iterator();
            while (first.hasNext()){
                TApvApproval firstNext = first.next();
                if("zdy".equals(firstNext.getApvStation())){
                    firstNext.setStationName("自定义");
                }else if("deptLeader".equals(firstNext.getApvStation())){
                    firstNext.setStationName("部门主管");
                } else if("financeLeader".equals(firstNext.getApvStation())){
                    firstNext.setStationName("财务主管");
                }else if("humanResources".equals(firstNext.getApvStation())){
                    firstNext.setStationName("人力资源主管");
                }else if("generalManager".equals(firstNext.getApvStation())){
                    firstNext.setStationName("总经理");
                }
                if(firstNext.getIsStart() == 1){
                    resultList.add(firstNext);
                    nextApproval = firstNext.getNextApproval();
                    first.remove();
                }
            }
            if(approvals != null && approvals.size() > 0){
                Iterator<TApvApproval> second = approvals.iterator();
                while (second.hasNext()){
                    TApvApproval secondNext = second.next();
                    if(secondNext.getPkApprovalId().equals(nextApproval)){
                        secondNext.setIsStart(2);
                        resultList.add(secondNext);
                        nextApproval = secondNext.getNextApproval();
                        second.remove();
                        break;
                    }
                }
            }
            if(approvals != null && approvals.size() > 0){
                Iterator<TApvApproval> third = approvals.iterator();
                while (third.hasNext()){
                    TApvApproval thirdNext = third.next();
                    if(thirdNext.getPkApprovalId().equals(nextApproval)){
                        thirdNext.setIsStart(3);
                        resultList.add(thirdNext);
                        nextApproval = thirdNext.getNextApproval();
                        third.remove();
                        break;
                    }
                }
            }
            if(approvals != null && approvals.size() > 0){
                Iterator<TApvApproval> fourth = approvals.iterator();
                while (fourth.hasNext()){
                    TApvApproval fourthNext = fourth.next();
                    if(fourthNext.getPkApprovalId().equals(nextApproval)){
                        fourthNext.setIsStart(4);
                        resultList.add(fourthNext);
                        nextApproval = fourthNext.getNextApproval();
                        fourth.remove();
                        break;
                    }
                }
            }
            if(approvals != null && approvals.size() > 0){
                Iterator<TApvApproval> fifth = approvals.iterator();
                while (fifth.hasNext()){
                    TApvApproval fifthNext = fifth.next();
                    if(fifthNext.getPkApprovalId().equals(nextApproval)){
                        fifthNext.setIsStart(5);
                        resultList.add(fifthNext);
                        nextApproval = fifthNext.getNextApproval();
                        fifth.remove();
                        break;
                    }
                }
            }
            return resultList;
        }else {
            return null;
        }
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        TApvApvtype entity = new TApvApvtype();
        List<TApvApvtype> list = this.tApvApvtypeMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TApvApvtype>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
