package com.hjy.cloud.t_apv.service;

import com.hjy.cloud.t_apv.entity.DCcRecord;
import com.hjy.cloud.domin.CommonResult;

import java.util.List;

/**
 * (DCcRecord)表服务接口
 *
 * @author makejava
 * @since 2021-03-08 18:11:21
 */
public interface DCcRecordService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param dCcRecord
     * @return
     */
    CommonResult insert(DCcRecord dCcRecord);

    /**
     * 修改数据
     *
     * @param dCcRecord
     * @return
     */
    CommonResult updateByPkId(DCcRecord dCcRecord);

    /**
     * 删除数据
     *
     * @param dCcRecord
     * @return
     */
    CommonResult delete(DCcRecord dCcRecord);

    /**
     * 查询所有数据
     *
     * @param param
     * @return
     */
    CommonResult selectAll(String param);

    /**
     * 获取单个数据
     *
     * @param dCcRecord 实体对象
     * @return
     */
    CommonResult selectById(DCcRecord dCcRecord);
    /**
     * 批量添加抄送记录
     *
     * @return
     */
    int insertCCRecordBatch(List<DCcRecord> ccRecordList);
}
