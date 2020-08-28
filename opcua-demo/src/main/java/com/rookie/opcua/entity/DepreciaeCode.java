package com.rookie.opcua.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 折旧码	
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_depreciae_code")
public class DepreciaeCode{

	@TableField("ID")
	private String id;

	/** 有关资产评估的折旧表 */
	@TableField("AFAPL")
	private String afapl;	

	/** 折旧码 */	
	@TableField("AFASL")
	private String afasl;
		
	/** 外部折旧码名称 */
	@TableField("AFATXT")
	private String afatxt;
	
	/** 表id */
	@TableField("TID")
	private String tid;
}
