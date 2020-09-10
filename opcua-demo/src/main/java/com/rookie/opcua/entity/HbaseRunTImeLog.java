package com.rookie.opcua.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("hbase_run_time_log")
public class HbaseRunTImeLog {

    @TableField("id")
    private String id;

    @TableField("last_run_time")
    private String lastRunTime;

    @TableField("run_cycle")
    private String runCycle;

}
