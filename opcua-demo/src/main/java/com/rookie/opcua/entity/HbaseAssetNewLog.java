package com.rookie.opcua.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString(callSuper = true)
@TableName("hbase_asset_new_log")
public class HbaseAssetNewLog {

    @TableField("id")
    private String id;

    @TableField("pull_count")
    private String pullCount;

    @TableField("pull_day")
    private String pullDay;

    @TableField("pull_time")
    private Date pullTime;

    @TableField("create_time")
    private Date createTime;
}
