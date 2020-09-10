package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * (ProfiandLossCount)实体类
 *
 * @author makejava
 * @since 2020-09-08 16:07:26
 */
@Data
@ToString(callSuper = true)
@TableName("t_profit_and_loss_count")
public class ProfitAndLossCount  {
    @TableField("id")
    private String id;
    /**
     * 公司代码
     */
    @TableField("company_code")
    private String companyCode;
    /**
     * 部门id
     */
    @TableField("dept_id")
    private String deptId;
    /**
     * 批次id
     */
    @TableField("batch_id")
    private String batchId;
    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;
   /**
     * 部门描述
     */
    @TableField("dept_desc")
    private String deptDesc;
    /**
     * 部门父ID
     */
    @TableField("p_id")
    private String pId;
    /**
     * 区域部门
     */
    @TableField("region_id")
    private String regionId;
    /**
     * 是否存在子部门
     */
    @TableField("is_leaf")
    private String isLeaf;
    /**
     * 盘点年度
     */
    @TableField("invent_year")
    private String inventYear;
    /**
     * 盘亏资产数
     */
    @TableField("lose_money_count")
    private Integer loseMoneyCount;
    /**
     * 盘亏净值
     */
    @TableField("lose_money_net_worth")
    private Double loseMoneyNetWorth;
    /**
     * 逾龄资产数
     */
    @TableField("pre_age_count")
    private Integer preAgeCount;
    /**
     * 逾龄净值
     */
    @TableField("pre_age_net_worth")
    private Double preAgeNetWorth;
    /**
     * 报废资产数
     */
    @TableField("scrapped_count")
    private Integer scrappedCount;
    /**
     * 报废净值
     */
    @TableField("scrapped_worth")
    private Double scrappedWorth;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}