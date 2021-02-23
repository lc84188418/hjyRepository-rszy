package com.hjy.cloud.utils.page;

import com.github.pagehelper.PageInfo;

public class PageUtil {

    /**
     * 将分页信息封装到统一的接口
     * @return
     * PageRequest pageRequest,
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        //当前页码
        pageResult.setPageNum(pageInfo.getPageNum());
        //每页数量
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setStartRow(pageInfo.getStartRow());
        pageResult.setEndRow(pageInfo.getEndRow());
        //记录总数
        pageResult.setTotal(pageInfo.getTotal());
        //页码总数
        pageResult.setPages(pageInfo.getPages());
        //数据模型
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}