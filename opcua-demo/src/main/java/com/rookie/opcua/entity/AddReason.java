package com.rookie.opcua.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 增加原因
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_add_reason")
public class AddReason implements Serializable, Cloneable {

	@TableField("ID")
	private String id;
	
	/** 表id */
	@TableField("TID")
	private String tid;

	/** 增加原因 */
	@TableField("ZZ_ZJYY")
	private String zzzjyy;

	/** 增加原因描述 */
	@TableField("ZJTEXT")
	private String zjtext;
}
