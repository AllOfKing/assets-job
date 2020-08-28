package com.rookie.opcua.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 作业成本类型
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_cost_type")
public class CostType{


	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 作业资产成本类别 */
	@TableField("zzzclb")
	private String zzzclb;

	/** 资产类别描述 */
	@TableField("zzclbdec")
	private String zzclbdec;
}
