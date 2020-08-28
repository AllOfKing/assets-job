package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 实物管理部门
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_matter_deptment")
public class MatterDeptment{

	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 部门编码 */
	@TableField("GLDEPTCODE")
	private String GLDEPTCODE;

	/** 部门名称 */
	@TableField("GLDEPTNAME")
	private String GLDEPTNAME;
	
	/** 省份 */
	@TableField("PROVINCE_NAME")
	private String PROVINCE_NAME;
	
	/** 公司代码 */
	@TableField("BUKRS")
	private String BUKRS;
	
	/** 利润中心 */
	@TableField("PRCTR")
	private String PRCTR;
	
	/** 利润中心描述 */
	@TableField("PTEXT")
	private String PTEXT;
}
