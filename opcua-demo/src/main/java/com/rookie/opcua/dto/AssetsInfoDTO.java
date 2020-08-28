package com.rookie.opcua.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
public class AssetsInfoDTO {

	private String id;
	
	/** 标记类型 */
	private String tagType;
	
	/** 批次ID */
	private String batchId;
	
	//新增字段 start
	/** 规格ID */
	private String specId;
	
	/** 版本 */
	private String version;
	
	/** 分片 */
	private String sharingId;
	
	/** OSS转固资产序号 */
	private String osszseq;
	
	/** 资产次级编号 */
	private String cardSecondCode;
	
	/** WBS号 */
	private String wbs;
	
	/** 客户名称 */
	private String clientName;
	
	/** 描述*/
	private String description;
	
	/** 增加原因   盘盈：实物管理员*/
	private String addtionReason;
	
	/** 评估前原值  盘盈：重置价值 */
	private String pgyz;
	
	/** 评估累计折旧  盘盈：估计折旧*/
	private String pgljzj;
	
	/** 评估累计减值*/
	private String pgljjz;
	
	/** 是否持有待售资产*/
	private String isStaySale = "0";
	
	/** 是否待报废(是:"1",否："0")*/
	private String isForSrcap = "0";
	
	/** 是否闲置(是:"1",否："0")*/
	private String isIdle = "0";
	
	/** 是否研发产生资产*/
	private String isDevelopasset = "0";
	
	/** 区域编码*/
	private String areaCode;
	
	/** 区县分公司*/
	private String countyOffices;
	
	/** 支局*/
	private String branch;
	
	/** 营业厅机构ID */
	private String businessOfficesId;
	
	/** 班组 */
	private String team;
	
	/** 是否扩容 */
	private String isExpansion = "";
	
	/** 是否减值资产(是:"1",否："0") */
	private String isImpairment = "0";
	
	/** 折旧范围1 */
	private String depreciaeRange1;
	
	/** 折旧码1 */
	private String depreciaeCode1;
	
	/** 折旧范围2 */
	private String depreciaeRange2;
	
	/** 折旧码2 */
	private String depreciaeCode2;
	
	/** 使用年限2 */
	private String durableYears2;
	
	/** 期间2 */
	private String period2;
	
	/** 科目定位码 */
	private String positionCode;
	
	/** 不活动日期 */
	private String unactiveDate;
	
	/** 首次购置日期 */
	private String purchasedDate;
	
	/** 购置年度 */
	private String purchasedYear;
	
	/** 首次购置记账的期间 */
	private String firstBilling;
	
	/** 是否停用(是:"1",否："0")*/
	private String isDisable = "0";
	
	/** 研发用资产*/
	private String developAsset;
	
	/** 是否出租(是:"1",否："0")*/
	private String isRent = "0";
	
	/** 是否融资租入*/
	private String isLease = "0";
	
	/** 产权凭证*/
	private String cardEntials;
	
	/** 折旧状况*/
	private String depreciaeStatu;
	
	/** WBS元素*/
	private String wbsElement;
	
	/** 评估尚可使用月份*/
	private String usefulmonths;
	
	/** 原资产号码*/
	private String originalCode;
	
	/** 是否父卡*/
	private String isParent = "";
	
	/** 耐用年限*/
	private String durableLife;
	//新增字段 end
	
	
	/** 工号 */
	private String userName;
	
	/** 基站（机房）名称 */
	private String stationName;
	
	/** 资产卡片编号 */
	private String cardCode;
	
	/**旧状态： 盘点状态：未盘点（00），盘点中（02），已盘点待签名（03），已签名待提交（001），审核中（002，current_node_id = '07'），审核完成（003）,审核回退（002，current_node_id='6'）*/
	/** 盘点状态：未盘点（00），盘点中（02），已盘点（03），已签名（001），已提交（002）*/
	private String checkStatus = "00";
	
	/** 盘点结果：盘点无误（01），卡片信息有误（02），有卡无物（03），无卡有物（04）*/
	private String checkResult = "";
	
	/** 标签状态：未判断（00），标签无误（01），标签重打（02），标签补打（03） */
	private String labelStatus = "00";
	
	/** 卡实关系：未判断（00），卡实无误（01），卡实有误（02），卡实未关联（03）*/
	private String cardObjectStatus = "00";
	
	/** 盘点位置信息 */
	private String checkLocation;
	
	/** 盘点经度 */
	private String checkLongitude;
	
	/** 盘点纬度 */
	private String checkLatitude;
	
	/** 是否计划外盘点:0否;1是 */
	private String isOutside = "";
	
	/**当前节点 */
	private String currentNodeId = "1";
	
	/** 公司代码 */
	private String companyCode;
	
	/** 公司名称 */
	private String companyName;
	
	/** 使用部门编码 */
	private String useDeptCode;
	
	/** 使用部门 */
	private String useDeptName;
	
	/** 实物管理部门编码 */
	private String manageDeptCode;
	
	/** 实物管理部门名称 */
	private String manageDeptName;
	
	/** 成本中心编号 */
	private String costCode;
	
	/** 成本中心 */
	private String costName;
	
	/** 资产名称 */
	private String assetsName;
	
	/** 资产分类（15类） */
	private String assetsType15;
	
	/** 资产分类 */
	private String assetsType;
	
	/** 资产性质 */
	private String assetsNature;
	
	/** 资产归属 */
	private String assetsAscri;
	
	/** 资产目录 */
	private String assetsCatalog;
	
	/** ABC类 */
	private String abcClass;
	
	/** 作业成本类别 */
	private String costType;
	
	/** 是否客户端资产(是:"1",否："0") */
	private String isClientAssets = "0";
	
	/** 资产管理员账号 */
	private String assetsMamagePerson;
	
	/** 资产保管员账号 */
	private String assetsKeepPerson;

	/** 资产管理员姓名 */
	private String assetsMamageName;

	/** 资产保管员姓名 */
	private String assetsKeepName;
	
	/** 责任人 */
	private String liablePenson;
	
	/** 是否暂估(是:"1",否："0") */
	private String isStopEstimate = "0";
	
	/** 制造商 */
	private String manufacturer;
	
	/** 资本化日期 */
	private String capitalDate;
	
	/** 是否逾龄(是:"1",否："0") */
	private String isOverdue = "0";
	
	/** 是否提足折旧(是:"1",否："0") */
	private String isDeprec = "0";
	
	/** 数量A */
	private String numberA;
	
	/** 数量A单位名称 */
	private String numberName;
	
	/** 原值 */
	private String originalValue;
	
	/** 净值 */
	private String worthValue;
	
	/** 累计折旧 */
	private String accumDeprec;
	
	/** 固定资产减值金额 */
	private String deprecValue;
	
	/** 工程编号 */
	private String enginCode;
	
	/** 工程名称 */
	private String enginName;
	
	/** 规格程式 */
	private String specProgram;
	
	/** 地点 */
	private String address;
	
	/** 备注 */
	private String enginRemarks;
	
	/** 是否网络类 */
	private String isNetwork = "";
	
	/** 省级编码 */
	private String provinCode;
	
	/** 状态 */
	private String assetsStatus = "1";
	
	/** 创建时间 */
	private Timestamp assetsCreateTime = new Timestamp(System.currentTimeMillis());
	
	/** 更新时间 */
	private Timestamp assetsUpdateTime = new Timestamp(System.currentTimeMillis());
	
	/** 利润中心组 */
	private String profitGroupCode;
	
	/** 利润中心组名称 */
	private String profitGroupName;
	
	/** 市局编码 */
	private String cityCode;
	
	/** 市局名称 */
	private String cityName;
	
	/** 割接时老资产卡片号 */
	private String oldAssetsNo;
	
	/** 附属设备及附件 */
	private String subAttachment;
	
	/** 备注 */
	private String subRemarks;
	
	/** 逾龄日期 */
	private String overdueDate;
	
	/** 源资产编号 */
	private String sourceCode;
	
	/** 关联实物ID */
	private String relationId;
	
	/** 关联实物ID标记 ,0:不标记，1：标记*/
	private String relationIdMark ="0";
	
	/** 使用年限1 */
	private String durableYears;
	
	/** 期间1 */
	private String period;
	
	/** 父资产编码 */
	private String parentAssetsCode;
	
	/** 是否冻结 */
	private String isFrozen = "";
	
	/** 任务包编码*/
	private String packetCode;
	
	/**盘点时间 */
	private Timestamp checkTime;
	
	/** 签名uuid */
	private String signUUID;
	
	/** 签名人 */
	private String signUser;
	
	/** 扫码记录 0:没扫码,>0:已扫码*/
	private Integer scanCodeRecord;
	
	/**是否粘贴资产标签  0:否  1:是 */
	private String hasAssetTag = "1";
	
	/**是否已下载  0:否  1:是 */
	private String isDownload = "0";
	
	/**是否可提交 0不能提交,1可提交 */
	private String isSubmitted = "0";
	
	
	/** 创建时间 */
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());

	/** 更新时间 */
	private Timestamp lastUpdateTime = new Timestamp(System.currentTimeMillis());
	//---------------------------------------------------------------------------------
	/** 残值 */
	private String residualValue;
	
	/** 已核查,0:未核查，1:已核查 */
	private String isCheck = "0";
	//------------------------------
	/** 盘点端(0：后台盘点，1：移动端盘点, 2:巡检, 3:网管比对)*/
	private String isApp;
	
	/*20200614 新增几个字段*/

	/** 是否扶贫 */
	private String povertyAlleviat;

	/** license号 */
	private String license;

	/** 许可方式 */
	private String licenseMethod;

	/** license到期日期 */
	private String dueDate;

	/** 软件建设管理部门编码 */
	private String softwareManageDepartCode ;

	/**软件建设维护部门编码  */
	private String softwareMaintDepartCode;

	/**HBASE传过来的状态  1在用，2闲置，3出售，4捐赠，5报废，6报废已处置 */
	private String status = "1";

	private String city;
}
