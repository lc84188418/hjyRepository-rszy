package com.hjy.cloud.t_outfit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_outfit.dao.TOutfitDeptMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitDept;
import com.hjy.cloud.t_outfit.service.TOutfitDeptService;
import com.hjy.cloud.t_system.entity.ReDeptUser;
import com.hjy.cloud.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TOutfitDept)表服务实现类
 *
 * @author makejava
 * @since 2021-02-23 00:07:06
 */
@Service("tOutfitDeptService")
public class TOutfitDeptServiceImpl implements TOutfitDeptService {

    @Resource
    private TOutfitDeptMapper tOutfitDeptMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param pkDeptId 主键
     * @return 实例对象
     */
    @Override
    public TOutfitDept selectById(String pkDeptId) throws Exception {
        return this.tOutfitDeptMapper.selectById(pkDeptId);
    }

    /**
     * 新增数据
     *
     * @param TOutfitDept 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int insert(TOutfitDept TOutfitDept) throws Exception {
        TOutfitDept.setPkDeptId(IDUtils.getUUID());
        TOutfitDept.setCreateTime(new Date());
        TOutfitDept.setModifyTime(new Date());
        return tOutfitDeptMapper.insertSelective(TOutfitDept);
    }

    /**
     * 修改数据
     *
     * @param TOutfitDept 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int updateById(TOutfitDept TOutfitDept) throws Exception {
        return tOutfitDeptMapper.updateById(TOutfitDept);
    }

    /**
     * 通过主键删除数据
     *
     * @param pkDeptId 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(String pkDeptId){
        return tOutfitDeptMapper.deleteById(pkDeptId);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<TOutfitDept> selectAll() throws Exception {
        return this.tOutfitDeptMapper.selectAll();
    }

    /**
     * 通过实体查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<TOutfitDept> selectAllByEntity(TOutfitDept TOutfitDept) throws Exception {
        return this.tOutfitDeptMapper.selectAllByEntity(TOutfitDept);
    }

    @Override
    public List<String> selectAllDeptName() {
        return tOutfitDeptMapper.selectAllDeptName();
    }

    @Override
    public List<String> selectDeptUser_userIded() {
        return tOutfitDeptMapper.selectDeptUser_userIded();
    }

    @Override
    public List<String> selectDeptUserByDept(String deptIdStr) {
        return tOutfitDeptMapper.selectDeptUserByDept(deptIdStr);
    }
    @Transactional()
    @Override
    public int deleteDeptUserByDeptId(String fk_dept_id) {
        return tOutfitDeptMapper.deleteDeptUserByDeptId(fk_dept_id);
    }

    @Transactional()
    @Override
    public int addDeptUserByList(String fk_dept_id, List<String> idList) {
        List<ReDeptUser> deptUsers = new ArrayList<ReDeptUser>();
        for (String s:idList){
            ReDeptUser deptUser = new ReDeptUser();
            deptUser.setPk_deptUser_id(IDUtils.getUUID());
            deptUser.setFk_user_id(s);
            deptUser.setFk_dept_id(fk_dept_id);
            deptUsers.add(deptUser);
        }
        return tOutfitDeptMapper.addDeptUserByList(deptUsers);
    }

    @Override
    public List<TOutfitDept> selectAllIdAndName() {
        return tOutfitDeptMapper.selectAllIdAndName();
    }

    @Override
    public void addDeptUserByDeptUser(ReDeptUser deptUser) {
        tOutfitDeptMapper.addDeptUserByDeptUser(deptUser);
    }

    @Override
    public int deleteDeptUserByUserId(String pkUserId) {
        return tOutfitDeptMapper.deleteDeptUserByUserId(pkUserId);
    }

    @Override
    public String selectDeptIdByUserId(String idStr) {
        return tOutfitDeptMapper.selectDeptIdByUserId(idStr);
    }

    @Override
    public CommonResult addUser(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String fk_dept_id=String.valueOf(jsonObject.get("fk_dept_id"));
        //删除原有的部门及用户
        tOutfitDeptMapper.deleteDeptUserByDeptId(fk_dept_id);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        if(jsonArray != null){
            String userIdsStr = jsonArray.toString();
            List<String> idList = JSONArray.parseArray(userIdsStr,String.class);
            //添加部门用户
            this.addDeptUserByList(fk_dept_id,idList);
        }
        return new CommonResult(200,"success","部门添加用户成功!",null);
    }
}