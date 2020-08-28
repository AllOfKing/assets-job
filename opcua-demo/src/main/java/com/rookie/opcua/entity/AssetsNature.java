package com.rookie.opcua.entity;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 资产性质
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_assets_nature")
public class AssetsNature{

	@TableField("ID")
	private String id;
	

	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 评审小组 1 - 4 */
	@TableField("ORD4X")
	private String ord4x;

	/** 评审小组:短描述 */
	@TableField("ORDTX")
	private String ordtx;
}
