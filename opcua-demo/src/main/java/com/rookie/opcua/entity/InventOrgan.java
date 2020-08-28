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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
/**
 * 部门组织
 * @author Gu
 *
 */
@Data
@TableName("sys_invent_organ")
public class InventOrgan {

	@TableField("ID")
	private String id;

	/** 父ID */
	@TableField("PARENT_ID")
	private String parentId;
	
	/** 部门名称 */
	@TableField("DEPARTMENT_NAME")
	private String departmentName;
	
	/** 区域ID */
	@TableField("REGION_ID")
	private String regionId;
	
	/** 排序 */
	@TableField("sort_code")
	private Integer sortCode = 0;
	
	/** 描述 */
	@TableField("DESCRIPTION")
	private String description;

	/** 是否有效 */
	@TableField("IS_VALID")
	private String isValid = "1";

	/** 注销时间 */
	@TableField("CANCEL_TIME")
	private Timestamp cancelTime;

	/** 注销原因 */
	@TableField("CANCEL_REASON")
	private String cancelReason;

	/** 备注 */
	@TableField("REMARKS")
	private String remarks;

	/** 创建人 */
	@TableField("CREATE_BY")
	protected String createBy = "admin";

	/** 创建时间  */
	@TableField("CREATE_TIME")
	protected Timestamp createTime = new Timestamp(System.currentTimeMillis());

	/** 最后更新人 */
	@TableField("LAST_UPDATE_BY")
	protected String lastUpdateBy = "admin";

	/** 最后更新时间 */
	@TableField("LAST_UPDATE_TIME")
	protected Timestamp lastUpdateTime  = new Timestamp(System.currentTimeMillis());

}
