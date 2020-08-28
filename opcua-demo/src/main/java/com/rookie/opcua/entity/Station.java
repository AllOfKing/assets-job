package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("t_station")
public class Station{

	@TableField("ID")
	private String id;

	/** 表id */
	@TableField("TID")
	private String tid;
	
	/** 基站名称*/
	@TableField("station_name")
	private String stationName;
	
	/** 基站编码*/
	@TableField("station_code")
	private String stationCode;
	
	/** 状态*/
	@TableField("state")
	private String state;
	
	/** 地区属性*/
	@TableField("area_attr")
	private String areaAttr;
	
	/** 省份编码*/
	@TableField("province")
	private String province;
	
	/** 是否冻结*/
	@TableField("is_frozeen")
	private String isFrozeen;
	
	/** 备注*/
	@TableField("remark")
	private String remark;
}
