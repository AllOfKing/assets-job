package com.rookie.opcua.entity;

import java.io.Serializable;

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

@Data
@TableName("t_assets_invent_count")
public class AssetsInventCount implements Serializable, Cloneable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5311231213921845294L;

	/** ID */
	@TableField("id")
	private String id;

	/*公司账套*/
	@TableField("company_code")
	private String companyCode;

	/** 实物管理部门编码 */
	@TableField("mater_dept_code")
	private String materDeptCode;

	/** 实物管理部门名称*/
	@TableField("mater_dept_name")
	private String materDeptName;

	/** 部门id */
	@TableField("dept_id")
	private String deptId;

	/** 部门名称 */
	@TableField("dept_name")
	private String deptName;

	/** 父部门id */
	@TableField("p_id")
	private String pId = "0";
	
	/** 区域编号 */
	@TableField("region_id")
	private String regionId;
	
	/** 是否存在子部门 */
	@TableField("is_leaf")
	private Integer isLeaf = 0;
	
	/** 卡片数量 */
	@TableField("card_num")
	private Integer cardNum = 0;
	
	/** 未盘点*/
	@TableField("not_invent")
	private Integer notInvent = 0;
	
	/** 已盘点 */
	@TableField("invent")
	private Integer invent = 0;
	
	/** app扫码盘点 */
	@TableField("app_scan_num")
	private Integer appScanNum = 0;

	/** app未扫码盘点 */
	@TableField("app_not_scan_num")
	private Integer appNotScanNum = 0;
	
	/** PC盘点 */
	@TableField("pc_num")
	private Integer pcNum = 0;
	
	/** 盘点无误 */
	@TableField("inerrant_num")
	private Integer inerrantNum = 0;
	
	/** 卡片信息有误 */
	@TableField("wrong_num")
	private Integer wrongNum = 0;
	
	/** 盘盈 */
	@TableField("add_num")
	private Integer addNum = 0;
	
	/** 盘亏 */
	@TableField("losses_num")
	private Integer lossesNum = 0;
	
	/*净值*/
	@TableField("worth_value")
    private double worthValue;

	private String assetsType;

	@Override
	public String toString() {
		return "AssetsInventCount [id=" + id + ", companyCode=" + companyCode
				+ ", materDeptCode=" + materDeptCode + ", materDeptName="
				+ materDeptName + ", deptId=" + deptId + ", deptName="
				+ deptName + ", pId=" + pId + ", regionId=" + regionId
				+ ", isLeaf=" + isLeaf + ", cardNum=" + cardNum
				+ ", notInvent=" + notInvent + ", invent=" + invent
				+ ", appScanNum=" + appScanNum + ", appNotScanNum="
				+ appNotScanNum + ", pcNum=" + pcNum + ", inerrantNum="
				+ inerrantNum + ", wrongNum=" + wrongNum + ", addNum=" + addNum
				+ ", lossesNum=" + lossesNum + ", worthValue=" + worthValue
				+ "]";
	}

	
	
}
