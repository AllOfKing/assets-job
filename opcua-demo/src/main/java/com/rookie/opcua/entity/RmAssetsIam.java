package com.rookie.opcua.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("RM_ASSET_IAM")
public class RmAssetsIam{
	
	@TableField("ID")
	private String id;
	
	@TableField("ASSET_ID")
	private String ASSET_ID;
	
	@TableField("SPEC_ID")
	private String SPEC_ID;
	
	@TableField("SHARDING_ID")
	private String SHARDING_ID;
	
	@TableField("BUKRS")
	private String BUKRS;
	
	@TableField("OSSZSEQ")
	private String OSSZSEQ;
	
	@TableField("ASSETSCARDCODE")
	private String ASSETSCARDCODE;
	
	@TableField("SECONDARYASSETSCARDCODE")
	private String SECONDARYASSETSCARDCODE;
	
	@TableField("ISESTIMATE")
	private String ISESTIMATE;
	
	@TableField("ASSET_CATALOGUE")
	private String ASSET_CATALOGUE;
	
	@TableField("ASSETSTYPE")
	private String ASSETSTYPE;
	
	@TableField("DESCRIPTION")
	private String DESCRIPTION;
	
	@TableField("WORKCOSTTYPE")
	private String WORKCOSTTYPE;
	
	@TableField("NAMBERUNIT")
	private String NAMBERUNIT;
	
	@TableField("PNUMBER")
	private String PNUMBER;
	
	@TableField("MANUFACTURER")
	private String MANUFACTURER;
	
	@TableField("STANDARD")
	private String STANDARD;
	
	@TableField("ADDRESS")
	private String ADDRESS;
	
	@TableField("MANAGEDEPARTMENT")
	private String MANAGEDEPARTMENT;
	
	@TableField("USEDEPARTMENT")
	private String USEDEPARTMENT;
	
	@TableField("COSTCENTER")
	private String COSTCENTER;
	
	@TableField("SUPERVISOR")
	private String SUPERVISOR;
	
	@TableField("ASSETCUSTODIAN")
	private String ASSETCUSTODIAN;
	
	@TableField("ASSETKEEPER")
	private String ASSETKEEPER;
	
	@TableField("CAPITALDATE")
	private String CAPITALDATE;
	
	@TableField("WBS")
	private String WBS;
	
	@TableField("ASSETSRELEGATION")
	private String ASSETSRELEGATION;
	
	@TableField("ISCLIENTASSET")
	private String ISCLIENTASSET;
	
	@TableField("CLIENTNAME")
	private String CLIENTNAME;
	
	@TableField("ASSETSNATURE")
	private String ASSETSNATURE;
	
	@TableField("NOTES")
	private String NOTES;
	
	@TableField("ABCTYPE")
	private String ABCTYPE;
	
	@TableField("ADDTIONREASON")
	private String ADDTIONREASON;
	
	@TableField("PARENT_ASSET_ID")
	private String PARENT_ASSET_ID;
	
	@TableField("ACCESSORY")
	private String ACCESSORY;
	
	@TableField("PGYZ")
	private String PGYZ;
	
	@TableField("PGLJZJ")
	private String PGLJZJ;
	
	@TableField("PGLJJZ")
	private String PGLJJZ;
	
	@TableField("ISHELDFORSALE")
	private String ISHELDFORSALE;
	
	@TableField("ISOVERAGE")
	private String ISOVERAGE;
	
	@TableField("ISFORSRCAP")
	private String ISFORSRCAP;
	
	@TableField("ISIDLE")
	private String ISIDLE;
	
	@TableField("ISDEVELOPASSET")
	private String ISDEVELOPASSET;
	
	@TableField("AREACODE")
	private String AREACODE;
	
	@TableField("COUNTYOFFICES")
	private String COUNTYOFFICES;
	
	@TableField("BRANCH")
	private String BRANCH;
	
	@TableField("BUSINESSOFFICEID")
	private String BUSINESSOFFICEID;
	
	@TableField("BTSCODE")
	private String BTSCODE;
	
	@TableField("TEAM")
	private String TEAM;
	
	@TableField("ISEXPANSION")
	private String ISEXPANSION;
	
	@TableField("ISIMPAIRMENT")
	private String ISIMPAIRMENT;
	
	@TableField("DEPRECIAERANGE1")
	private String DEPRECIAERANGE1;
	
	@TableField("DEPRECIAECODE1")
	private String DEPRECIAECODE1;
	
	@TableField("DEPRECIAERANGE2")
	private String DEPRECIAERANGE2;
	
	@TableField("DEPRECIAECODE2")
	private String DEPRECIAECODE2;
	
	@TableField("USEFULLIFE1")
	private String USEFULLIFE1;
	
	@TableField("PERIOD1")
	private String PERIOD1;
	
	@TableField("USEFULLIFE2")
	private String USEFULLIFE2;
	
	@TableField("PERIOD2")
	private String PERIOD2;
	
	@TableField("COSTVALUE")
	private String COSTVALUE;
	
	@TableField("DEPRECIATION")
	private String DEPRECIATION;
	
	@TableField("IMPAIRMENTSUM")
	private String IMPAIRMENTSUM;
	
	@TableField("NETVALUE")
	private String NETVALUE;
	
	@TableField("POSITIONCODE")
	private String POSITIONCODE;
	
	@TableField("INACTIVEDATE")
	private String INACTIVEDATE;
	
	@TableField("PURCHASEDATE")
	private String PURCHASEDATE;
	
	@TableField("PURCHASEYEAR")
	private String PURCHASEYEAR;
	
	@TableField("FIRSTBILLING")
	private String FIRSTBILLING;
	
	@TableField("ISDISABLE")
	private String ISDISABLE;
	
	@TableField("ORIGINALASSET")
	private String ORIGINALASSET;
	
	@TableField("DEVELOPASSET")
	private String DEVELOPASSET;
	
	@TableField("ISRENT")
	private String ISRENT;
	
	@TableField("ISLEASE")
	private String ISLEASE;
	
	@TableField("CREDENTIALS")
	private String CREDENTIALS;
	
	@TableField("DEMOLITIONSTATU")
	private String DEMOLITIONSTATU;
	
	@TableField("WBSELEMENT")
	private String WBSELEMENT;
	
	@TableField("USEFULMONTHS")
	private String USEFULMONTHS;
	
	@TableField("SOURCEASSETNO")
	private String SOURCEASSETNO;
	
	@TableField("ISPARENT")
	private String ISPARENT;
	
	@TableField("DURABLELIFE")
	private String DURABLELIFE;
	
	@TableField("IAM_STATUS")
	private String IAM_STATUS;
	
	@TableField("IAM_DATE")
	private String IAM_DATE;
	
	@TableField("TIME_STAMP")
	private String TIME_STAMP;
	
	@TableField("BATCH_NO")
	private String BATCH_NO;
	
	@TableField("IS_ARTI_DIST")
	private String IS_ARTI_DIST;
	
	@TableField("GROUP_PROV_RULE")
	private String GROUP_PROV_RULE;
	
	@TableField("ASSETCUSTODIAN_ACCOUNT")
	private String ASSETCUSTODIAN_ACCOUNT;
	
	@TableField("ASSETKEEPER_ACCOUNT")
	private String ASSETKEEPER_ACCOUNT;
	
	@TableField("zzfpzc")
	private String zzfpzc;
	
	/** 更新时间 */
	@TableField("update_time")
	private Date updateTime;
	
	/** 操作类型*/
	@TableField("operation")
	private String operation;


}
