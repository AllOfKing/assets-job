package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
/**
 * 成本中心
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_hb_cost_center")
public class HBCostCenter{

	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 成本中心 */
	@TableField("COSTCENTER")
	private String COSTCENTER;

	/** 成本中心描述 */
	@TableField("COSTCENTERREMARK")
	private String COSTCENTERREMARK;
	
	/** 省份 */
	@TableField("PROVINCE_NAME")
	private String PROVINCE_NAME;
	
	/** 描述 */
	@TableField("LTEXT")
	private String LTEXT;
	
	/** 公司代码 */
	@TableField("BUKRS")
	private String BUKRS;
	
	/** 部门 */
	@TableField("ABTEI")
	private String ABTEI;
	
	/** 成本中心类型 */
	@TableField("KOSAR")
	private String KOSAR;
	
	/** 利润中心 */
	@TableField("PRCTR")
	private String PRCTR;
}
