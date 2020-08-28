package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 计量单位
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_jldw")
public class JLDW{

	@TableField("ID")
	private String id;
	

	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 计量单位id */
	@TableField("UNITID")
	private String UNITID;
	
	/** 计量单位中文 */
	@TableField("CHUNIT")
	private String CHUNIT;

	/** 计量单位编码 */
	@TableField("F_UNITID")
	private String F_UNITID;
	
	/** 时间戳 */
	@TableField("LASTUPDATETIME")
	private String LASTUPDATETIME;
}
