package com.hjy.cloud.common.entity;

import lombok.Data;

/**
 * (DTrainRecord)表实体类
 *
 * @author makejava
 * @since 2021-03-01 15:46:29
 */
@Data
public class DTrainRecord {

    private String pkTrainrecordId;
    //培训活动参与者ID
    private String fkUserId;
    //培训记录ID
    private String fkTrainId;

}
