package com.hjy.cloud.utils.page;

import lombok.Data;

/**
 * 分页请求
 */
@Data
public class PageRequest<T> {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 其他查询参数
     */
    private T param;
}