package com.rookie.opcua.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * 更新的资产卡片
 * 
 * @author Gu
 *
 */
@Data
@TableName("t_sync_assets_card")
public class SyncAssetsCard{

	@TableField("ID")
	private String id;

	/** 卡片编码 */
	@TableField("cardCode")
	private String cardCode;

	/** 盘点批次id */
	@TableField("batchId")
	private String batchId;

	/** 更新状态：0，未同步；1，已同步 */
	@TableField("gxStatus")
	private String gxStatus = "0";
	
	/** 调拨状态：0，未同步；1，已同步 */
	@TableField("dbStatus")
	private String dbStatus = "0";
	
	/** 盘盈状态：0，未同步；1，已同步 */
	@TableField("pyStatus")
	private String pyStatus = "0";
	
	/** 盘亏状态：0，未同步；1，已同步 */
	@TableField("pkStatus")
	private String pkStatus = "0";
	
	/** 同步状态：0，未同步；1，已同步 */
	@TableField("syncStatus")
	private String syncStatus = "0";

	/** 创建时间 */
	@TableField("createByTime")
	private Timestamp createByTime = new Timestamp(System.currentTimeMillis());
	
	/** 重分类状态：0，未同步；1，已同步 */
	@TableField("cflStatus")
	private String cflStatus = "0";
	
	/** 资产id*/
	@TableField("assetsId")
	private String assetsId;

}
