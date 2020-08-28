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
 * 盘点项目
 * @author Gu
 *
 */
@Data
@TableName("t_invent_project")
public class InventProject{

	@TableField("ID")
	private String id;
	
	/** 项目名称 */
	@TableField("projectName")
	private String projectName ;
	
	/** 项目状态 ,0创建中，1创建完成*/
	@TableField("projectStatus")
	private String projectStatus = "0";
	
	/** 账套*/
	@TableField("companyCode")
	private String companyCode;
	
	/** 资产分类 */
	@TableField("assetsType")
	private String assetsType ;
	
	/**
	 * 项目类别
	 *    0：普通项目
	 *    1：年末项目
	 */
	@TableField("ItemCategory")
	private String ItemCategory = "0";
	
	/** ABC类 */
	@TableField("assetsAbc")
	private String assetsAbc ;
	
	/** 已创建批次数*/
	@TableField("batchNumber")
	private String batchNumber = "0";
	
	/** 批次总数*/
	@TableField("batchSum")
	private String batchSum = "0";
	
	/** 开始时间 */
	@TableField("startDate")
	private Timestamp startDate ;
	
	/** 结束时间 */
	@TableField("endDate")
	private Timestamp endDate ;
	
	/** 创建人 身份id*/
	@TableField("createByIde")
	private String createByIde;
	
	/** 创建时间 */
	@TableField("createTime")
	private Timestamp createTime = new Timestamp(System.currentTimeMillis()) ;
	
	/** 备注 */
	@TableField("remark")
	private String remark;
	
	/** 基准日 */
	@TableField("criterionDate")
	private Timestamp criterionDate ;
}
