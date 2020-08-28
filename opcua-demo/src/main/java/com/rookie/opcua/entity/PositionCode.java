package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 科目定位码
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_position_code")
public class PositionCode{

	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 科目定位码 */
	@TableField("ktogr")
	private String ktogr;	

	/** 科目组说明 */
	@TableField("ktgrtx")
	private String ktgrtx;
}
