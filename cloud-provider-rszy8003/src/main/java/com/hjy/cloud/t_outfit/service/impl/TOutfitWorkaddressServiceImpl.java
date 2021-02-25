package com.hjy.cloud.t_outfit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.cloud.t_outfit.dao.TOutfitWorkaddressMapper;
import com.hjy.cloud.t_outfit.entity.TOutfitWorkaddress;
import com.hjy.cloud.t_outfit.service.TOutfitWorkaddressService;
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
 * (TOutfitWorkaddress)表服务实现类
 *
 * @author makejava
 * @since 2021-02-24 11:25:32
 */
@Service("tOutfitWorkaddressService")
public class TOutfitWorkaddressServiceImpl implements TOutfitWorkaddressService {

    @Resource
    private TOutfitWorkaddressMapper tOutfitWorkaddressMapper;

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
     * @param tOutfitWorkaddress
     * @return
     */
    @Transactional()
    @Override
    public CommonResult insert(TOutfitWorkaddress tOutfitWorkaddress) {
        tOutfitWorkaddress.setPkWorkadressId(IDUtils.getUUID());
        int i = this.tOutfitWorkaddressMapper.insertSelective(tOutfitWorkaddress);
        if (i > 0) {
            JSONObject resultJson = this.getListInfo();
            return new CommonResult(200, "success", "添加数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "添加数据失败", null);
        }
    }



    /**
     * 修改数据
     *
     * @param tOutfitWorkaddress
     * @return
     */
    @Transactional()
    @Override
    public CommonResult updateByPkId(TOutfitWorkaddress tOutfitWorkaddress) {
        int i = this.tOutfitWorkaddressMapper.updateByPkId(tOutfitWorkaddress);
        if (i > 0) {
            JSONObject resultJson = this.getListInfo();

            return new CommonResult(200, "success", "修改数据成功", resultJson);
        } else {
            return new CommonResult(444, "error", "修改数据失败", null);
        }
    }

    /**
     * 删除数据
     *
     * @param tOutfitWorkaddress
     * @return
     */
    @Transactional()
    @Override
    public CommonResult delete(TOutfitWorkaddress tOutfitWorkaddress) {
        int i = this.tOutfitWorkaddressMapper.deleteById(tOutfitWorkaddress);
        if (i > 0) {
            JSONObject resultJson = this.getListInfo();

            return new CommonResult(200, "success", "删除数据成功", resultJson);
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
        TOutfitWorkaddress entity = new TOutfitWorkaddress();

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
        List<TOutfitWorkaddress> list = this.tOutfitWorkaddressMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TOutfitWorkaddress>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }

    /**
     * 获取单个数据
     *
     * @param tOutfitWorkaddress 实例对象
     * @return
     */
    @Override
    public CommonResult selectById(TOutfitWorkaddress tOutfitWorkaddress) {
        String pkId = tOutfitWorkaddress.getPkWorkadressId();
        TOutfitWorkaddress entity = this.tOutfitWorkaddressMapper.selectByPkId(pkId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("entity", entity);
        return new CommonResult(200, "success", "获取数据成功", resultJson);
    }
    private JSONObject getListInfo() {
        TOutfitWorkaddress entity = new TOutfitWorkaddress();
        //分页记录条数
        PageHelper.startPage(1, 10);
        List<TOutfitWorkaddress> list = this.tOutfitWorkaddressMapper.selectAllPage(entity);
        PageResult result = PageUtil.getPageResult(new PageInfo<TOutfitWorkaddress>(list));
        JSONObject resultJson = new JSONObject();
        resultJson.put("PageResult", result);
        return resultJson;
    }
}
    
