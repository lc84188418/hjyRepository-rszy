package com.hjy.cloud.domin;

import lombok.Data;

/**
 * @author liuchun
 * @Package com.hjy.cloud.domin
 * @date 2021/5/18 11:00
 * description:
 */
@Data
public class ActiveResult<T> {
    private T entity;
}
