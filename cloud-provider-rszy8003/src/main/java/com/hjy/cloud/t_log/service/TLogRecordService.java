package com.hjy.cloud.t_log.service;

import com.hjy.cloud.t_log.entity.TLogRecord;
import com.hjy.cloud.domin.CommonResult;

/**
 * (TLogRecord)表服务接口
 *
 * @author makejava
 * @since 2021-03-02 10:02:01
 */
public interface TLogRecordService {
    /**
     * 添加前获取数据
     *
     * @return
     */
    CommonResult insertPage();

    /**
     * 添加数据
     *
     * @param tLogRecord
     * @return
     */
    CommonResult insert(TLogRecord tLogRecord);

    /**
     * 修改数据
     *
     * @param tLogRecord
     * @return
     */
    CommonResult updateByPkId(TLogRecord tLogRecord);

    /**
     * 删除数据
     *
     * @param tLogRecord
     * @return
     */
    CommonResult delete(TLogRecord tLogRecord);

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
     * @param tLogRecord 实体对象
     * @return
     */
    CommonResult selectById(TLogRecord tLogRecord);
}
