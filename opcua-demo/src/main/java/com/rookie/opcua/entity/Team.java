package com.rookie.opcua.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 班组
 * 
 * @author Gu
 *
 */
@Data
@ToString(callSuper = true)
@TableName("t_team")
public class Team{


	@TableField("ID")
	private String id;


	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 班组 */
	@TableField("ZZ_BANZ")
	private String zz_banz;	

	/** 班组名称 */
	@TableField("ZZ_BANZT")
	private String zz_banzt;
}
