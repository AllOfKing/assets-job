package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 折旧范围	
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_depreciae_range")
public class DepreciaeRange{


	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 有关资产评估的折旧表 */
	@TableField("AFAPL")
	private String afapl;	

	/** 实际的或派生的折旧范围 */
	@TableField("AFABER")
	private String afaber;
	
	/** 折旧范围的目的 */
	@TableField("AREA_USAGE_IND")
	private String areaUsageIND;
}
