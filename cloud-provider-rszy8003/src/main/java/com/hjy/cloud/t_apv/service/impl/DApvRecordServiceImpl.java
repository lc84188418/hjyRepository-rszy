package com.hjy.cloud.t_apv.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_apv.entity.DApvRecord;
import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_apv.dao.DApvRecordMapper;
import com.hjy.cloud.t_apv.service.DApvRecordService;
import com.hjy.cloud.utils.page.PageRequest;
import com.hjy.cloud.utils.page.PageResult;
import com.hjy.cloud.utils.page.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DApvRecord)表服务实现类
 *
 * @author makejava
 * @since 2021-06-08 18:09:44
 */
@Slf4j
@Service
public class DApvRecordServiceImpl implements DApvRecordService {

    @Resource
    private DApvRecordMapper dApvRecordMapper;

    /**
     * 添加前获取数据
     *
     * @return
     */
    @Override
    public CommonResult insertPage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", null);
        return new CommonResult(200, "success", "获取数据成功", jsonObject);
    }

    /**
     * 添加数据
     *
     * @param dApvRecord 实体数据
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(DApvRecord dApvRecord) {
        int i = this.dApvRecordMapper.insertSelective(dApvRecord);
        if (i > 0) {
            return new CommonResult(200, "success", "添加数据成功", null);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }

    /**
     * 修改数据
     *
     * @param dApvRecord 实体数据
     * @return
     */
    @Transactional()
    @Override
    public CommonResult update(DApvRecord dApvRecord) {
        int i = this.dApvRecordMapper.updateByPkId(dApvRecord);
        if (i > 0) {
            return new CommonResult(200, "success", "修改数据成功", null);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param pkId 主键id
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(String pkId) {
        int i = this.dApvRecordMapper.deleteByPkId(pkId);
        if (i > 0) {
            return new CommonResult(200, "success", "删除数据成功", null);
        } else {
            return new CommonResult(201, "success", "该信息已被删除，无需再次请求，请刷新", null);
        }
    }

    /**
     * 查询所有数据
     *
     * @param pageRequest 分页参数
     * @return
     */
    @Override
    public CommonResult selectAll(PageRequest<DApvRecord> pageRequest) {
        if (pageRequest.getPageNum() == 0) {
            pageRequest.setPageNum(1);
        }
        if (pageRequest.getPageSize() == 0) {
            pageRequest.setPageNum(10);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<DApvRecord> list = this.dApvRecordMapper.selectAllEntity(pageRequest.getParam());
        PageResult<DApvRecord> result = PageUtil.getPageResult(new PageInfo<DApvRecord>(list));
        return new CommonResult(200, "success", "获取数据成功", result);
    }

    /**
     * 获取单个数据
     *
     * @param pkId 主键id
     * @return
     */
    @Override
    public DApvRecord selectById(String pkId) {
        return this.dApvRecordMapper.selectByPkId(pkId);
    }

    private JSONObject getListInfo() {
        PageHelper.startPage(1, 10);
        DApvRecord entity = new DApvRecord();
        List<DApvRecord> list = this.dApvRecordMapper.selectAllEntity(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<DApvRecord>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("pageResult", result);
        return resultJson;
    }
}
    
