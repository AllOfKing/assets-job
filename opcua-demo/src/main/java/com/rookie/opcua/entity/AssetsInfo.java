package com.rookie.opcua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.sql.Timestamp;

@Data
@ToString(callSuper = true)
@TableName("t_assets_info")
public class AssetsInfo{

	@TableField("ID")
	private String id;
	
	/** 标记类型 */
	@TableField("tag_type")
	private String tagType;
	
	/** 批次ID */
	@TableField("batch_id")
	private String batchId;
	
	//新增字段 start
	/** 规格ID */
	@TableField("spec_id")
	private String specId;
	
	/** 版本 */
	@TableField("version")
	private String version;
	
	/** 分片 */
	@TableField("sharing_id")
	private String sharingId;
	
	/** OSS转固资产序号 */
	@TableField("osszseq")
	private String osszseq;
	
	/** 资产次级编号 */
	@TableField("card_secondCode")
	private String cardSecondCode;
	
	/** WBS号 */
	@TableField("wbs")
	private String wbs; 
	
	/** 客户名称 */
	@TableField("client_name")
	private String clientName; 
	
	/** 描述*/
	@TableField("description")
	private String description; 
	
	/** 增加原因   盘盈：实物管理员*/
	@TableField("addtion_reason")
	private String addtionReason; 
	
	/** 评估前原值  盘盈：重置价值 */
	@TableField("pgyz")
	private String pgyz; 
	
	/** 评估累计折旧  盘盈：估计折旧*/
	@TableField("pgljzj")
	private String pgljzj; 
	
	/** 评估累计减值*/
	@TableField("pgljjz")
	private String pgljjz; 
	
	/** 是否持有待售资产*/
	@TableField("is_stay_sale")
	private String isStaySale = "0";
	
	/** 是否待报废(是:"1",否："0")*/
	@TableField("is_for_srcap")
	private String isForSrcap = "0";
	
	/** 是否闲置(是:"1",否："0")*/
	@TableField("is_idle")
	private String isIdle = "0";
	
	/** 是否研发产生资产*/
	@TableField("is_developasset")
	private String isDevelopasset = "0";
	
	/** 区域编码*/
	@TableField("area_code")
	private String areaCode; 
	
	/** 区县分公司*/
	@TableField("county_offices")
	private String countyOffices; 
	
	/** 支局*/
	@TableField("branch")
	private String branch; 
	
	/** 营业厅机构ID */
	@TableField("business_offices_id")
	private String businessOfficesId; 
	
	/** 班组 */
	@TableField("team")
	private String team; 
	
	/** 是否扩容 */
	@TableField("is_expansion")
	private String isExpansion = ""; 
	
	/** 是否减值资产(是:"1",否："0") */
	@TableField("is_impairment")
	private String isImpairment = "0";
	
	/** 折旧范围1 */
	@TableField("depreciae_range1")
	private String depreciaeRange1; 
	
	/** 折旧码1 */
	@TableField("depreciae_code1")
	private String depreciaeCode1; 
	
	/** 折旧范围2 */
	@TableField("depreciae_range2")
	private String depreciaeRange2; 
	
	/** 折旧码2 */
	@TableField("depreciae_code2")
	private String depreciaeCode2; 
	
	/** 使用年限2 */
	@TableField("durable_years2")
	private String durableYears2;
	
	/** 期间2 */
	@TableField("period2")
	private String period2;
	
	/** 科目定位码 */
	@TableField("position_code")
	private String positionCode;
	
	/** 不活动日期 */
	@TableField("unactive_date")
	private String unactiveDate;
	
	/** 首次购置日期 */
	@TableField("purchased_date")
	private String purchasedDate;
	
	/** 购置年度 */
	@TableField("purchased_year")
	private String purchasedYear;
	
	/** 首次购置记账的期间 */
	@TableField("first_billing")
	private String firstBilling;
	
	/** 是否停用(是:"1",否："0")*/
	@TableField("is_disable")
	private String isDisable = "0";
	
	/** 研发用资产*/
	@TableField("develop_asset")
	private String developAsset;
	
	/** 是否出租(是:"1",否："0")*/
	@TableField("is_rent")
	private String isRent = "0";
	
	/** 是否融资租入*/
	@TableField("is_lease")
	private String isLease = "0";
	
	/** 产权凭证*/
	@TableField("card_entials")
	private String cardEntials;
	
	/** 折旧状况*/
	@TableField("depreciae_statu")
	private String depreciaeStatu;
	
	/** WBS元素*/
	@TableField("wbs_element")
	private String wbsElement;
	
	/** 评估尚可使用月份*/
	@TableField("usefulmonths")
	private String usefulmonths;
	
	/** 原资产号码*/
	@TableField("original_code")
	private String originalCode;
	
	/** 是否父卡*/
	@TableField("is_parent")
	private String isParent = "";
	
	/** 耐用年限*/
	@TableField("durable_life")
	private String durableLife;
	//新增字段 end
	
	
	/** 工号 */
	@TableField("user_name")
	private String userName;
	
	/** 基站（机房）名称 */
	@TableField("station_name")
	private String stationName;
	
	/** 资产卡片编号 */
	@TableField("card_code")
	private String cardCode;
	
	/**旧状态： 盘点状态：未盘点（00），盘点中（02），已盘点待签名（03），已签名待提交（001），审核中（002，current_node_id = '07'），审核完成（003）,审核回退（002，current_node_id='6'）*/
	/** 盘点状态：未盘点（00），盘点中（02），已盘点（03），已签名（001），已提交（002）*/
	@TableField("check_status")
	private String checkStatus = "00";
	
	/** 盘点结果：盘点无误（01），卡片信息有误（02），有卡无物（03），无卡有物（04）*/
	@TableField("check_result")
	private String checkResult = "";
	
	/** 标签状态：未判断（00），标签无误（01），标签重打（02），标签补打（03） */
	@TableField("label_status")
	private String labelStatus = "00";
	
	/** 卡实关系：未判断（00），卡实无误（01），卡实有误（02），卡实未关联（03）*/
	@TableField("card_object_status")
	private String cardObjectStatus = "00";
	
	/** 盘点位置信息 */
	@TableField("check_location")
	private String checkLocation;
	
	/** 盘点经度 */
	@TableField("check_longitude")
	private String checkLongitude;
	
	/** 盘点纬度 */
	@TableField("check_latitude")
	private String checkLatitude;
	
	/** 是否计划外盘点:0否;1是 */
	@TableField("is_outside")
	private String isOutside = "";
	
	/**当前节点 */
	@TableField("current_node_id")
	private String currentNodeId = "1";
	
	/** 公司代码 */
	@TableField("company_code")
	private String companyCode;
	
	/** 公司名称 */
	@TableField("company_name")
	private String companyName;
	
	/** 使用部门编码 */
	@TableField("use_dept_code")
	private String useDeptCode;
	
	/** 使用部门 */
	@TableField("use_dept_name")
	private String useDeptName;
	
	/** 实物管理部门编码 */
	@TableField("manage_dept_code")
	private String manageDeptCode;
	
	/** 实物管理部门名称 */
	@TableField("manage_dept_name")
	private String manageDeptName;
	
	/** 成本中心编号 */
	@TableField("cost_code")
	private String costCode;
	
	/** 成本中心 */
	@TableField("cost_name")
	private String costName;
	
	/** 资产名称 */
	@TableField("assets_name")
	private String assetsName;
	
	/** 资产分类（15类） */
	@TableField("assets_type_15")
	private String assetsType15;
	
	/** 资产分类 */
	@TableField("assets_type")
	private String assetsType;
	
	/** 资产性质 */
	@TableField("assets_nature")
	private String assetsNature;
	
	/** 资产归属 */
	@TableField("assets_ascri")
	private String assetsAscri;
	
	/** 资产目录 */
	@TableField("assets_catalog")
	private String assetsCatalog;
	
	/** ABC类 */
	@TableField("abc_class")
	private String abcClass;
	
	/** 作业成本类别 */
	@TableField("cost_type")
	private String costType;
	
	/** 是否客户端资产(是:"1",否："0") */
	@TableField("is_client_assets")
	private String isClientAssets = "0";
	
	/** 资产管理员账号 */
	@TableField("assets_mamage_person")
	private String assetsMamagePerson;
	
	/** 资产保管员账号 */
	@TableField("assets_keep_person")
	private String assetsKeepPerson;

	/** 资产管理员姓名 */
	@TableField("assets_mamage_name")
	private String assetsMamageName;

	/** 资产保管员姓名 */
	@TableField("assets_keep_name")
	private String assetsKeepName;
	
	/** 责任人 */
	@TableField("liable_penson")
	private String liablePenson;
	
	/** 是否暂估(是:"1",否："0") */
	@TableField("is_stop_estimate")
	private String isStopEstimate = "0";
	
	/** 制造商 */
	@TableField("manufacturer")
	private String manufacturer;
	
	/** 资本化日期 */
	@TableField("capital_date")
	private String capitalDate;
	
	/** 是否逾龄(是:"1",否："0") */
	@TableField("is_overdue")
	private String isOverdue = "0";
	
	/** 是否提足折旧(是:"1",否："0") */
	@TableField("is_deprec")
	private String isDeprec = "0";
	
	/** 数量A */
	@TableField("number_A")
	private String numberA;
	
	/** 数量A单位名称 */
	@TableField("number_name")
	private String numberName;
	
	/** 原值 */
	@TableField("original_value")
	private String originalValue;
	
	/** 净值 */
	@TableField("worth_value")
	private String worthValue;
	
	/** 累计折旧 */
	@TableField("accum_deprec")
	private String accumDeprec;
	
	/** 固定资产减值金额 */
	@TableField("deprec_value")
	private String deprecValue;
	
	/** 工程编号 */
	@TableField("engin_code")
	private String enginCode;
	
	/** 工程名称 */
	@TableField("engin_name")
	private String enginName;
	
	/** 规格程式 */
	@TableField("spec_program")
	private String specProgram;
	
	/** 地点 */
	@TableField("address")
	private String address;
	
	/** 备注 */
	@TableField("engin_remarks")
	private String enginRemarks;
	
	/** 是否网络类 */
	@TableField("is_network")
	private String isNetwork = "";
	
	/** 省级编码 */
	@TableField("provin_code")
	private String provinCode;
	
	/** 状态 */
	@TableField("assets_status")
	private String assetsStatus = "1";
	
	/** 创建时间 */
	@TableField("assets_create_time")
	private Timestamp assetsCreateTime = new Timestamp(System.currentTimeMillis());
	
	/** 更新时间 */
	@TableField("assets_update_time")
	private Timestamp assetsUpdateTime = new Timestamp(System.currentTimeMillis());
	
	/** 利润中心组 */
	@TableField("profit_group_code")
	private String profitGroupCode;
	
	/** 利润中心组名称 */
	@TableField("profit_group_name")
	private String profitGroupName;
	
	/** 市局编码 */
	@TableField("city_code")
	private String cityCode;
	
	/** 市局名称 */
	@TableField("city_name")
	private String cityName;
	
	/** 割接时老资产卡片号 */
	@TableField("old_assets_no")
	private String oldAssetsNo;
	
	/** 附属设备及附件 */
	@TableField("sub_attachment")
	private String subAttachment;
	
	/** 备注 */
	@TableField("sub_remarks")
	private String subRemarks;
	
	/** 逾龄日期 */
	@TableField("overdue_date")
	private String overdueDate;
	
	/** 源资产编号 */
	@TableField("source_code")
	private String sourceCode;
	
	/** 关联实物ID */
	@TableField("relation_id")
	private String relationId;
	
	/** 关联实物ID标记 ,0:不标记，1：标记*/
	@TableField("relation_id_mark")
	private String relationIdMark ="0";
	
	/** 使用年限1 */
	@TableField("durable_years")
	private String durableYears;
	
	/** 期间1 */
	@TableField("period")
	private String period;
	
	/** 父资产编码 */
	@TableField("parent_assets_code")
	private String parentAssetsCode;
	
	/** 是否冻结 */
	@TableField("is_frozen")
	private String isFrozen = "";
	
	/** 任务包编码*/
	@TableField("packet_code")
	private String packetCode;
	
	/**盘点时间 */
	@TableField("check_Time")
	private Timestamp checkTime;
	
	/** 签名uuid */
	@TableField("sign_uuid")
	private String signUUID;
	
	/** 签名人 */
	@TableField("sign_user")
	private String signUser;
	
	/** 扫码记录 0:没扫码,>0:已扫码*/
	@TableField("scan_code_record")
	private Integer scanCodeRecord;
	
	/**是否粘贴资产标签  0:否  1:是 */
	@TableField("has_assetTag")
	private String hasAssetTag = "1";
	
	/**是否已下载  0:否  1:是 */
	@TableField("is_download")
	private String isDownload = "0";
	
	/**是否可提交 0不能提交,1可提交 */
	@TableField("is_submitted")
	private String isSubmitted = "0";
	
	
	/** 创建时间 */
	@TableField("create_time")
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	
	/** 更新时间 */
	@TableField("last_update_time")
	private Timestamp lastUpdateTime = new Timestamp(System.currentTimeMillis());
	//---------------------------------------------------------------------------------
	/** 残值 */
	@TableField("residual_value")
	private String residualValue;
	
	/** 已核查,0:未核查，1:已核查 */
	@TableField("is_check")
	private String isCheck = "0";
	//------------------------------
	/** 盘点端(0：后台盘点，1：移动端盘点, 2:巡检, 3:网管比对)*/
	@TableField("is_app")
	private String isApp;
	
	/*20200614 新增几个字段*/

	/** 是否扶贫 */
	@TableField("poverty_alleviat")
	private String povertyAlleviat;

	/** license号 */
	@TableField("license")
	private String license;

	/** 许可方式 */
	@TableField("license_method")
	private String licenseMethod;

	/** license到期日期 */
	@TableField("due_date")
	private String dueDate;

	/** 软件建设管理部门编码 */
	@TableField("software_manage_depart_code")
	private String softwareManageDepartCode ;

	/**软件建设维护部门编码  */
	@TableField("software_maint_depart_code")
	private String softwareMaintDepartCode;

	/**HBASE传过来的状态  1在用，2闲置，3出售，4捐赠，5报废，6报废已处置 */
	@TableField("status")
	private String status = "1";

	@TableField("is_rollback")
	private String isRollback;

	private String city;
}
