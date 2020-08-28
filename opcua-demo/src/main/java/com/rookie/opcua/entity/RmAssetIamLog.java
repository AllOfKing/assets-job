package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("RM_ASSET_IAM_LOG")
public class RmAssetIamLog{


	@TableField("id")
	private String id;
	
	/** 资产ID */
	@TableField("busi_Table_Name")
	private String busiTableName;
	
	/** 资产ID */
	@TableField("key_Column")
	private String keyColumn;
	
	/** 资产ID */
	@TableField("operation")
	private String operation;
	
	/** 资产ID */
	@TableField("row_Key")
	private String rowKey;
	
	/** 资产ID */
	@TableField("uptime")
	private String uptime;
	
	
}
