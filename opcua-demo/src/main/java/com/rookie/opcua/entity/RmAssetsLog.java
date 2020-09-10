package com.rookie.opcua.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("rm_asset_log")
public class RmAssetsLog {

    @TableField("id")
    private String id;

    @TableField("row_Key")
    private String rowKey;

    @TableField("uptime")
    private String uptime;

    @TableField("operation")
    private String operation;
    @TableField("busi_Table_Name")
    private String busiTableName;
    @TableField("key_Column")
    private String keyColumn;


    private String subDay;
}
