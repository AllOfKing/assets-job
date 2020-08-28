package com.rookie.opcua.entity;

import java.io.Serializable;

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
 * 
 * @Author lsw
 * @Date 2018年1月11日 上午11:33:25
 * Company:广州磐信计算机科技有限公司
 * Description: 实物管理部门 基础数据
 */
@Data
@TableName("t_matter_department")
public class MatterDepartment{

	@TableField("id")
	private String id;
	
	/** 组织ID */
	@TableField("organ_id")
	private String organId;
	
	/** 管理部门ID */
	@TableField("dept_id")
	private String deptId;
	
	/** 资产实物管理部门编号 */
	@TableField("mater_dept_code")
	private String materDeptCode;
	
	/** 资产实物管理部门描述 */
	@TableField("mater_dept_name")
	private String materDeptName;
	
	/** 对应公司实体 */
	@TableField("company_entity")
	private String companyEntity = "";
	
	/** 利润中心编号 */
	@TableField("profit_center_code")
	private String profitCenterCode;
	
	/** 利润中心名称 */
	@TableField("profit_center_name")
	private String profitCenterName;
	
	/** 地市 */
	@TableField("city_code")
	private String cityCode;
	
}
