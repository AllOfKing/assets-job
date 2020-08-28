package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_reqular_and_special_year_count")
public class ReqularAndSpecialYearCount {

    @TableField("id")
    private String id;

    //公司代码
    @TableField("company_code")
    private String companyCode;

    //利润中心组
    @TableField("profit_group_code")
    private String profitGroupCode;

    //部门id
    @TableField("dept_id")
    private String deptId;

    // 部门名称
    @TableField("dept_name")
    private String deptName;

    //父id
    @TableField("p_id")
    private String pId;

    @TableField("region_id")
    private String regionId;

    @TableField("is_leaf")
    private String isLeaf;

    @TableField("invent_year")
    private String inventYear;

    @TableField("regular_total_count")
    private String regularTotalCount;

    @TableField("regular_no_invent")
    private String regularNoInvent;

    @TableField("regular_invent")
    private String regularInvent;

    @TableField("regular_worth_value")
    private String regularWorthValue;

    @TableField("special_total_count")
    private String specialTotalCount;

    @TableField("special_no_invent")
    private String specialNoInvent;

    @TableField("special_invent")
    private String specialInvent;

    @TableField("special_worth_value")
    private String specialWorthValue;

    @TableField("create_time")
    private Date createTime;

}
