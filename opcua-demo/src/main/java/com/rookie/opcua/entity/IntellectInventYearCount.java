package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
@TableName("t_intellect_invent_year_count")
public class IntellectInventYearCount {

    @TableField("id")
    private String id;

    //公司账套
    @TableField("company_code")
    private String companyCode;

    //利润中心组
    @TableField("profit_group_code")
    private String profitGroupCode;

    //部门id
    @TableField("dept_id")
    private String deptId;

    //部门名称
    @TableField("dept_name")
    private String deptName;

    //上级id
    @TableField("p_id")
    private String pId;

    //区域id
    @TableField("region_id")
    private String regionId;

    //是否有上级
    @TableField("is_leaf")
    private String isLeaf;

    //盘点年份
    @TableField("invent_year")
    private String inventYear;

    //网管盘点资产数
    @TableField("wg_invent_count")
    private String wgInventCount;

    //网管净值
    @TableField("wg_worth_value")
    private String wgWorthValue;

    //巡检盘点资产数
    @TableField("xj_invent_count")
    private String xjInventCount;

    //巡检净值
    @TableField("xj_worth_value")
    private String xjWorthValue;

    //app盘点资产数
    @TableField("app_invent_count")
    private String appInventCount;

    //app净值
    @TableField("app_worth_value")
    private String appWorthValue;


    //app未扫码资产数
    @TableField("app_no_scan_invent_count")
    private String appNoScanInventCount;

    //app未扫码净值
    @TableField("app_no_scan_worth_value")
    private String appNoScanWorthValue;

    // pc端盘点资产数
    @TableField("pc_invent_count")
    private String pcInventCount;

    //pc端净值
    @TableField("pc_worth_value")
    private String pcWorthValue;

    //创建时间
    @TableField("create_time")
    private Date createTime;

    //资产卡片总数
    @TableField("total_count")
    private String totalCount;

    // 已盘点总量
    @TableField("invent_count")
    private String inventCount;

}
