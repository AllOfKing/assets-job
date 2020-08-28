package com.rookie.opcua.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.util.Date;

@Data
@ToString(callSuper = true)
@TableName("RM_ASSET_NEW")
public class RmAssetNew {


    @TableField("ID")
    private String id;

    /** 规格ID */
    @TableField("SPEC_ID")
    private String specId;

    /** 创建人ID */
    @TableField("creator_id")
    private String creatorId;

    /** 资产ID */
    @TableField("create_date")
    private String createDate ;

    /** 创建时间 */
    @TableField("modifier_id")
    private String modifierId;

    /** 修改时间 */
    @TableField("modify_date")
    private String modifyDate;

    /** 版本 */
    @TableField("version")
    private String version;

    /** 分片id */
    @TableField("sharding_id")
    private String shardingId;

    /** 公司代码 */
    @TableField("bukrs")
    private String bukrs;

    /** OSS转固资产序号 */
    @TableField("osszseq")
    private String osszseq;

    /** 卡片编号 */
    @TableField("assetscardcode")
    private String assetscardcode;

    /** 资产次级编号 */
    @TableField("secondaryassetscardcode")
    private String secondaryassetscardcode;

    /** 是否暂估 */
    @TableField("isestimate")
    private String isestimate;

    /** 资产目录 */
    @TableField("asset_catalogue")
    private String assetCatalogue;

    /** 资产类别 */
    @TableField("assetstype")
    private String assetstype;

    /** 资产描述 */
    @TableField("description")
    private String description;

    /** 作业成本类别 */
    @TableField("workcosttype")
    private String workcosttype;

    /** 单位 */
    @TableField("namberunit")
    private String namberunit;

    /** 数量 */
    @TableField("PNUMBER")
    private String PNUMBER;

    /** 制造商 */
    @TableField("manufacturer")
    private String manufacturer;

    /** 结构规格程式 */
    @TableField("standard")
    private String standard;

    /** 地址 */
    @TableField("address")
    private String address;

    /** 实物管理部门  */
    @TableField("managedepartment")
    private String managedepartment;

    /** 使用部门 */
    @TableField("usedepartment")
    private String usedepartment;

    /** 成本中心 */
    @TableField("costcenter")
    private String costcenter;

    /** 责任人 */
    @TableField("supervisor")
    private String supervisor;

    /** 资产管理员 */
    @TableField("assetcustodian")
    private String assetcustodian;

    /** 保管员 */
    @TableField("assetkeeper")
    private String assetkeeper;
    /*19-10-15增加资产管理员账号和保管员账号用来保存资产管理员和保管员
     * 原来的资产管理员和保管员保存主数据推送过来的资产管理员账号和保管员账号*/
    /** 资产管理员账号 */
    @TableField("assetcustodian_account")
    private String assetcustodianAccount;

    /** 保管员账号 */
    @TableField("assetkeeper_account")
    private String assetkeeperAccount;

    /** 资本化日期 */
    @TableField("capitaldate")
    private String capitaldate;

    /** wbs */
    @TableField("wbs")
    private String wbs;

    /** 资产归属 */
    @TableField("assetsrelegation")
    private String assetsrelegation;

    /** 是否客户端资产 */
    @TableField("isclientasset")
    private String isclientasset;

    /** 客户名称 */
    @TableField("clientname")
    private String clientname;

    /** 资产性质 */
    @TableField("assetsnature")
    private String assetsnature;

    /** 备注 */
    @TableField("notes")
    private String notes;

    /** 资产ID */
    @TableField("abctype")
    private String abctype;

    /** 父资产ID*/
    @TableField("parent_asset_id")
    private String parentAssetId;

    /** 增加原因 */
    @TableField("addtionreason")
    private String addtionreason;

    /** 附属设备及附件 */
    @TableField("accessory")
    private String accessory;

    /** 评估前原值 */
    @TableField("pgyz")
    private String pgyz;

    /** 评估累计折旧 */
    @TableField("pgljzj")
    private String pgljzj;

    /** 评估累计减值*/
    @TableField("pgljjz")
    private String pgljjz;

    /** 是否持有待售资产 */
    @TableField("isheldforsale")
    private String isheldforsale;

    /** 是否逾龄资产 */
    @TableField("isoverage")
    private String isoverage;

    /** 是否待报废 */
    @TableField("isforsrcap")
    private String isforsrcap;

    /** 是否闲置资产 */
    @TableField("isidle")
    private String isidle;

    /** 是否研发产生资产 */
    @TableField("isdevelopasset")
    private String isdevelopasset;

    /** 区域编码 */
    @TableField("areacode")
    private String areacode;

    /** 区县分公司 */
    @TableField("countyoffices")
    private String countyoffices;

    /** 支局 */
    @TableField("branch")
    private String branch;

    /** 营业厅机构ID  */
    @TableField("businessofficeid")
    private String businessofficeid;

    /** 基站（机房） */
    @TableField("btscode")
    private String btscode;

    /** 班组 */
    @TableField("team")
    private String team;

    /** 是否扩容资产*/
    @TableField("isexpansion")
    private String isexpansion;

    /** 是否减值资产 */
    @TableField("isimpairment")
    private String isimpairment;

    /** 折旧范围1 */
    @TableField("depreciaerange1")
    private String depreciaerange1;

    /** 折旧码1 */
    @TableField("depreciaecode1")
    private String depreciaecode1;

    /** 折旧范围2 */
    @TableField("depreciaerange2")
    private String depreciaerange2;

    /** 折旧码2 */
    @TableField("depreciaecode2")
    private String depreciaecode2;

    /** 使用年限1 */
    @TableField("usefullife1")
    private String usefullife1;

    /** 期间1 */
    @TableField("period1")
    private String period1;

    /** 使用年限2 */
    @TableField("usefullife2")
    private String usefullife2;

    /** 期间2 */
    @TableField("period2")
    private String period2;

    /** 原值 */
    @TableField("costvalue")
    private String costvalue;

    /** 累计折旧 */
    @TableField("depreciation")
    private String depreciation;

    /** 减值金额 */
    @TableField("impairmentsum")
    private String impairmentsum;

    /** 资产净值*/
    @TableField("netvalue")
    private String netvalue;

    /** 科目定位码 */
    @TableField("positioncode")
    private String positioncode;

    /** 不活动日期 */
    @TableField("inactivedate")
    private String inactivedate;

    /**首次购置日期 */
    @TableField("purchasedate")
    private String purchasedate;

    /** 购置年度 */
    @TableField("purchaseyear")
    private String purchaseyear;

    /** 首次购置记帐的期间 */
    @TableField("firstbilling")
    private String firstbilling;

    /** 是否停用 */
    @TableField("isdisable")
    private String isdisable;

    /** 原资产号码 */
    @TableField("originalasset")
    private String originalasset;

    /** 研发用资产 */
    @TableField("developasset")
    private String developasset;

    /** 是否出租 */
    @TableField("isrent")
    private String isrent;

    /** 是否融资租入*/
    @TableField("islease")
    private String islease;

    /** 产权凭证*/
    @TableField("credentials")
    private String credentials;

    /** 折旧状况*/
    @TableField("demolitionstatu")
    private String demolitionstatu;

    /** WBS元素 */
    @TableField("wbselement")
    private String wbselement;

    /** 评估尚可使用月份 */
    @TableField("usefulmonths")
    private String usefulmonths;

    /** 源资产号码*/
    @TableField("sourceassetno")
    private String sourceassetno;

    /** 是否父卡 */
    @TableField("isparent")
    private String isparent;

    /** 耐用年限*/
    @TableField("durablelife")
    private String durablelife;

    /** 资产ID */
    @TableField("data_source_id")
    private String dataSourceId;


    /** 更新时间 */
    @TableField("update_time")
    private Date updateTime;

    /** 操作类型*/
    @TableField("operation")
    private String operation;

    /*2020-4-21 加 状态
     * 1在用，2闲置，3出售，4捐赠，5报废，6报废已处置*/
    @TableField("status")
    private String status;

    /**
     * 20200614 添加字段
     * 是否扶贫资产	ZZFPZC
     * License号	ZZ_LICENSE
     许可方式	ZZ_FZ07
     License到期日期	ZZ_LDQRQ
     软件建设管理部门	ZZ_RJJSGLBM
     软件建设维护部门	ZZ_RJJSWHBM
     *
     */
    /** 是否扶贫资产 */
    @TableField("zzpfzc")
    private String zzpfzc;

    /** License号 */
    @TableField("zz_license")
    private String zzLicense;

    /** 许可方式 */
    @TableField("zz_fz07")
    private String zzFz07;

    /** License到期日期 */
    @TableField("zz_ldqrq")
    private String zzLdqrq;

    /** 软件建设管理部门 */
    @TableField("zz_rjjsglbm")
    private String zzRjjsglbm;

    /** 软件建设维护部门 */
    @TableField("zz_rjjswhbm")
    private String zzRjjswhbm;


}
