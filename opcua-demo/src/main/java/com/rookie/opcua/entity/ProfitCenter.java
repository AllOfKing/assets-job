package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
/**
 * 
 * @Author lsw
 * @Date 2018年1月11日 上午11:33:25
 * Company:广州磐信计算机科技有限公司
 * Description: 利润中心 基础数据
 */
@Data
@ToString(callSuper = true)
@TableName("t_profit_center")
public class ProfitCenter{

	@TableField("ID")
	private String id;
	
	/** 利润中心编号 */
	@TableField("profit_center_code")
	private String profitCenterCode;
	
	/** 编号 */
	@TableField("profit_code")
	private String profitCode;
	
	/** 地市 */
	@TableField("city_code")
	private String cityCode;
	
	/** 对应公司实体 */
	@TableField("company_entity")
	private String companyEntity;
	
	/** 利润中心名称 */
	@TableField("profit_name")
	private String profitName;
	
	/** 描述 */
	@TableField("remark")
	private String remark;
	
	/** 状态 */
	@TableField("profit_status")
	private String profitStatus;
	
	/** 利润中心组名称 */
	@TableField("profit_group_name")
	private String profitGroupName;
	
	/** 所属省份编码 */
	@TableField("province_code")
	private String provinceCode;

}
