package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 作业成本类别
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_zycblb")
public class ZYCBLB{

	@TableField("ID")
	private String id;
	

	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 作业资产成本类别 */
	@TableField("ZZ_ZCLB")
	private String ZZ_ZCLB;

	/** 资产类别描述 */
	@TableField("ZZCLB_DEC")
	private String ZZCLB_DEC;
}
