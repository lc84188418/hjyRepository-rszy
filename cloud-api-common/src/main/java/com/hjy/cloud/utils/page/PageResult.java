package com.hjy.cloud.utils.page;

import lombok.Data;

import java.util.List;

/**
 * 分页返回结果
 */
@Data
public class PageResult<T> {
    //当前页码
    private int pageNum;
    //每页数量
    private int pageSize;
    //记录总数
    private long total;
    //页码总数
    private int pages;
    //数据模型
    private List<T> content;
    //查询开始行数
    private int startRow;
    //查询结束行数
    private int endRow;
}