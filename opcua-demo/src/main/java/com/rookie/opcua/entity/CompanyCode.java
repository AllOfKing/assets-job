package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 公司代码
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_company_code")
public class CompanyCode{


	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 公司代码描述 */
	@TableField("organizeName")
	private String organizeName;

	/** 省份 */
	@TableField("provinceName")
	private String provinceName;

	/** 公司代码 */
	@TableField("organizeid")
	private String organizeid;
}
