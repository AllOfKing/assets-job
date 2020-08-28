package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
/**
 * @Author lsw
 * @Date 2018年11月15日 下午3:11:10
 * Company:广州磐信计算机科技有限公司
 * Description:	资产类别
 */

@Data
@ToString(callSuper = true)
@TableName("t_asset_zclb")
public class AssetZCLB{
	
	@TableField("id")
	private String id ;
	
	@TableField("code")
	private String code;
	
	@TableField("name")
	private String name;

	/** 表id */
	@TableField("TID")
	private String tid;
}
