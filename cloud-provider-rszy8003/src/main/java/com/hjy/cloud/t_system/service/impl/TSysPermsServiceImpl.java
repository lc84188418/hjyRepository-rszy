package com.hjy.cloud.t_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_system.entity.ActiveUser;
import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.JsonUtil;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtils;
import com.hjy.cloud.t_system.entity.TSysPerms;
import com.hjy.cloud.t_system.dao.TSysPermsMapper;
import com.hjy.cloud.t_system.service.TSysPermsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (TSysPerms)表服务实现类
 *
 * @author liuchun
 * @since 2020-07-24 09:55:41
 */
@Service
public class TSysPermsServiceImpl implements TSysPermsService {
    @Autowired
    private TSysPermsMapper tSysPermsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param pkPermsId 主键
     * @return 实例对象
     */
    @Override
    public TSysPerms selectById(Object pkPermsId) {
        return this.tSysPermsMapper.selectById(pkPermsId);
    }

    /**
     * 新增数据
     *
     * @param tSysPerms 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int insert(TSysPerms tSysPerms) {
        tSysPerms.setPkPermsId(IDUtils.getUUID());
        tSysPerms.setCreateTime(new Date());
        tSysPerms.setModifyTime(new Date());
        return tSysPermsMapper.insertSelective(tSysPerms);
    }

    /**
     * 修改数据
     *
     * @param tSysPerms 实例对象
     * @return 实例对象
     */
    @Transactional()
    @Override
    public int updateById(TSysPerms tSysPerms, ActiveUser activeUser) {
        tSysPerms.setModifyTime(new Date());
        //修改人
        tSysPerms.setModifyUsername(activeUser.getFullName());
        tSysPerms.setFkUserId(activeUser.getUserId());
        return tSysPermsMapper.updateById(tSysPerms);
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Transactional()
    @Override
    public int deleteById(String pk_perms_id) {
        return tSysPermsMapper.deleteById(pk_perms_id);
    }

    /**
     * 查询多条数据
     * @return 对象列表
     */
    @Override
    public List<TSysPerms> selectAll() {
        return this.tSysPermsMapper.selectAll();
    }
    @Override
    public List<TSysPerms> selectAllIdAndName() {
        return this.tSysPermsMapper.selectAllIdAndName();
    }
    /**
     * 通过实体查询多条数据
     * @return 对象列表
     */
    @Override
    public List<TSysPerms> selectAllByEntity(TSysPerms tSysPerms) {
        return this.tSysPermsMapper.selectAllByEntity(tSysPerms);
    }

    @Override
    public List<String> selectDistributeByrole_id(String roleIdStr) {
        return tSysPermsMapper.selectDistributeByrole_id(roleIdStr);
    }

    @Override
    public PageResult selectAllPage(String param) {
        JSONObject json = JSON.parseObject(param);
        //实体数据
        String pageNumStr = JsonUtil.getStringParam(json,"pageNum");
        String pageSizeStr = JsonUtil.getStringParam(json,"pageSize");
        String menuName = JsonUtil.getStringParam(json,"menuName");
        String path = JsonUtil.getStringParam(json,"path");
        String permsCode = JsonUtil.getStringParam(json,"permsCode");
        String menuType = JsonUtil.getStringParam(json,"menuType");
        TSysPerms tSysPerms = new TSysPerms();
        tSysPerms.setMenuName(menuName);
        tSysPerms.setPath(path);
        tSysPerms.setPermsCode(permsCode);
        tSysPerms.setMenuType(menuType);
        //分页记录条数
        int pageNum = 1;
        int pageSize = 10;
        if(pageNumStr != null){
            pageNum = Integer.parseInt(pageNumStr);
        }
        if(pageSizeStr != null){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TSysPerms> list = tSysPermsMapper.selectAllPage(tSysPerms);
        return PageUtils.getPageResult(new PageInfo<TSysPerms>(list));
    }
}