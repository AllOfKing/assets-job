package com.rookie.opcua.job;

import com.rookie.opcua.dto.ProfitCenterDTO;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

@Slf4j
//@Component
public class TestJob implements Runnable {


    private int startIndex;

    private List<CompanyCode> cpList ;//公司代码
    private List<AssetZCLB> amList;//资产配别
    private List<ZYCBLB> zyList;//作业成本类别
    private List<JLDW> jldwList ;//计量单位
    private List<MatterDeptment> swglbmList;// 实物管理部门
    private List<UseDeptment> sybmList ; // 使用部门
    private List<AssetsAsc> aaList ;// 资产归属
    private List<AssetsNature> anList ; // 资产性质
    private List<AddReason> addRList ;// 增加原因
    private List<BoroughCompany> bcList ;// 区县分公司
    private List<Branch> bList ; // 支局清单
    private List<BusinessHall> bhList ; // 营业厅
    private List<Station> stList ; //基站
    private List<Team> tList ; // 班组
    private List<DepreciaeRange> drList ; // 折旧范围
    private List<DepreciaeCode> dcList ; // 折旧码
    private List<PositionCode> pcList ;  // 科目定位码
    private List<HBCostCenter> cbzxList ; // 成本中心
    private List<ProfitCenterDTO> pList ;
    private RmAssetNewMapper rmAssetNewMapper;
    private DayMapper dayMapper;
    private AssetsInfoMapper assetsInfoMapper;
    private HbaseAssetNewLogMapper hbaseAssetNewLogMapper;
    private RmAssetsLogMapper rmAssetsLogMapper;
    private HbaseRunTImeLogMapper hbaseRunTImeLogMapper;


    public TestJob(int startIndex,List<CompanyCode> cpList,List<AssetZCLB> amList,List<ZYCBLB> zyList,List<JLDW> jldwList,
    List<MatterDeptment> swglbmList, List<UseDeptment> sybmList,List<AssetsAsc> aaList,List<AssetsNature> anList,
                   List<AddReason> addRList,List<BoroughCompany> bcList,List<Branch> bList,List<BusinessHall> bhList,
                   List<Station> stList,List<Team> tList,List<DepreciaeRange> drList,List<DepreciaeCode> dcList,
                   List<PositionCode> pcList,List<HBCostCenter> cbzxList,List<ProfitCenterDTO> pList,RmAssetNewMapper rmAssetNewMapper,
                   DayMapper dayMapper,AssetsInfoMapper assetsInfoMapper,HbaseAssetNewLogMapper hbaseAssetNewLogMapper,
                   RmAssetsLogMapper rmAssetsLogMapper,HbaseRunTImeLogMapper hbaseRunTImeLogMapper){
        this.startIndex = startIndex;
        this.cpList = cpList;
        this.amList = amList;
        this.zyList = zyList;
        this.jldwList = jldwList;
        this.swglbmList = swglbmList;
        this.sybmList = sybmList;
        this.aaList = aaList;
        this.anList = anList;
        this.addRList = addRList;
        this.bcList = bcList;
        this.bList = bList;
        this.bhList = bhList;
        this.stList = stList;
        this.tList = tList;
        this.drList = drList;
        this.dcList = dcList;
        this.pcList = pcList;
        this.cbzxList = cbzxList;
        this.pList = pList;
        this.rmAssetNewMapper = rmAssetNewMapper;
        this.dayMapper = dayMapper;
        this.assetsInfoMapper = assetsInfoMapper;
        this.hbaseAssetNewLogMapper = hbaseAssetNewLogMapper;
        this.rmAssetsLogMapper = rmAssetsLogMapper;
        this.hbaseRunTImeLogMapper = hbaseRunTImeLogMapper;
    }

    @Override
    public void run() {
        processingData();
    }


    private void setValue(String name, String value, RmAssetNew rmAssetNew)  {
        try {
            if(name.equalsIgnoreCase("ID")){
                rmAssetNew.setId(value);
            }
            if(name.equalsIgnoreCase("SPEC_ID")){
                rmAssetNew.setSpecId(value);
            }
            if(name.equalsIgnoreCase("CREATOR_ID")){
                rmAssetNew.setCreatorId(value);
            }
            if(name.equalsIgnoreCase("CREATE_DATE")){
                rmAssetNew.setCreateDate(value);
            }
            if(name.equalsIgnoreCase("modifier_id")){
                rmAssetNew.setModifierId(value);
            }
            if(name.equalsIgnoreCase("modify_date")){
                rmAssetNew.setModifyDate(value);
            }
            if(name.equalsIgnoreCase("version")){
                rmAssetNew.setVersion(value);
            }
            if(name.equalsIgnoreCase("sharding_id")){
                rmAssetNew.setShardingId(value);
            }
            if(name.equalsIgnoreCase("bukrs")){
                rmAssetNew.setBukrs(value);
            }
            if(name.equalsIgnoreCase("osszseq")){
                rmAssetNew.setOsszseq(value);
            }
            if(name.equalsIgnoreCase("assetscardcode")){
                rmAssetNew.setAssetscardcode(value);
            }
            if(name.equalsIgnoreCase("secondaryassetscardcode")){
                rmAssetNew.setSecondaryassetscardcode(value);
            }
            if(name.equalsIgnoreCase("isestimate")){
                rmAssetNew.setIsestimate(value);
            }
            if(name.equalsIgnoreCase("asset_catalogue")){
                rmAssetNew.setAssetCatalogue(value);
            }
            if(name.equalsIgnoreCase("assetstype")){
                rmAssetNew.setAssetstype(value);
            }
            if(name.equalsIgnoreCase("description")){
                rmAssetNew.setDescription(value);
            }
            if(name.equalsIgnoreCase("workcosttype")){
                rmAssetNew.setWorkcosttype(value);
            }
            if(name.equalsIgnoreCase("namberunit")){
                rmAssetNew.setNamberunit(value);
            }
            if(name.equalsIgnoreCase("PNUMBER")){
                rmAssetNew.setPNUMBER(value);
            }
            if(name.equalsIgnoreCase("manufacturer")){
                rmAssetNew.setManufacturer(value);
            }
            if(name.equalsIgnoreCase("standard")){
                rmAssetNew.setStandard(value);
            }
            if(name.equalsIgnoreCase("address")){
                rmAssetNew.setAddress(value);
            }
            if(name.equalsIgnoreCase("managedepartment")){
                rmAssetNew.setManagedepartment(value);
            }
            if(name.equalsIgnoreCase("usedepartment")){
                rmAssetNew.setUsedepartment(value);
            }
            if(name.equalsIgnoreCase("costcenter")){
                rmAssetNew.setCostcenter(value);
            }
            if(name.equalsIgnoreCase("supervisor")){
                rmAssetNew.setSupervisor(value);
            }
            if(name.equalsIgnoreCase("assetcustodian")){
                rmAssetNew.setAssetcustodian(value);
            }
            if(name.equalsIgnoreCase("assetkeeper")){
                rmAssetNew.setAssetkeeper(value);
            }
            if(name.equalsIgnoreCase("assetcustodian_account")){
                rmAssetNew.setAssetcustodianAccount(value);
            }
            if(name.equalsIgnoreCase("assetkeeper_account")){
                rmAssetNew.setAssetkeeperAccount(value);
            }
            if(name.equalsIgnoreCase("capitaldate")){
                rmAssetNew.setCapitaldate(value);
            }
            if(name.equalsIgnoreCase("wbs")){
                rmAssetNew.setWbs(value);
            }
            if(name.equalsIgnoreCase("assetsrelegation")){
                rmAssetNew.setAssetsrelegation(value);
            }
            if(name.equalsIgnoreCase("isclientasset")){
                rmAssetNew.setIsclientasset(value);
            }
            if(name.equalsIgnoreCase("clientname")){
                rmAssetNew.setClientname(value);
            }
            if(name.equalsIgnoreCase("assetsnature")){
                rmAssetNew.setAssetsnature(value);
            }
            if(name.equalsIgnoreCase("notes")){
                rmAssetNew.setNotes(value);
            }
            if(name.equalsIgnoreCase("abctype")){
                rmAssetNew.setAbctype(value);
            }
            if(name.equalsIgnoreCase("parent_asset_id")){
                rmAssetNew.setParentAssetId(value);
            }
            if(name.equalsIgnoreCase("addtionreason")){
                rmAssetNew.setAddtionreason(value);
            }
            if(name.equalsIgnoreCase("accessory")){
                rmAssetNew.setAccessory(value);
            }
            if(name.equalsIgnoreCase("pgyz")){
                rmAssetNew.setPgyz(value);
            }
            if(name.equalsIgnoreCase("pgljzj")){
                rmAssetNew.setPgljzj(value);
            }
            if(name.equalsIgnoreCase("pgljjz")){
                rmAssetNew.setPgljjz(value);
            }
            if(name.equalsIgnoreCase("isheldforsale")){
                rmAssetNew.setIsheldforsale(value);
            }
            if(name.equalsIgnoreCase("isoverage")){
                rmAssetNew.setIsoverage(value);
            }
            if(name.equalsIgnoreCase("isforsrcap")){
                rmAssetNew.setIsforsrcap(value);
            }
            if(name.equalsIgnoreCase("isidle")){
                rmAssetNew.setIsidle(value);
            }
            if(name.equalsIgnoreCase("isdevelopasset")){
                rmAssetNew.setIsdevelopasset(value);
            }
            if(name.equalsIgnoreCase("areacode")){
                rmAssetNew.setAreacode(value);
            }
            if(name.equalsIgnoreCase("countyoffices")){
                rmAssetNew.setCountyoffices(value);
            }
            if(name.equalsIgnoreCase("branch")){
                rmAssetNew.setBranch(value);
            }
            if(name.equalsIgnoreCase("businessofficeid")){
                rmAssetNew.setBusinessofficeid(value);
            }
            if(name.equalsIgnoreCase("btscode")){
                rmAssetNew.setBtscode(value);
            }
            if(name.equalsIgnoreCase("team")){
                rmAssetNew.setTeam(value);
            }
            if(name.equalsIgnoreCase("team")){
                rmAssetNew.setTeam(value);
            }
            if(name.equalsIgnoreCase("isexpansion")){
                rmAssetNew.setIsexpansion(value);
            }
            if(name.equalsIgnoreCase("isimpairment")){
                rmAssetNew.setIsimpairment(value);
            }
            if(name.equalsIgnoreCase("depreciaerange1")){
                rmAssetNew.setDepreciaerange1(value);
            }
            if(name.equalsIgnoreCase("depreciaecode1")){
                rmAssetNew.setDepreciaecode1(value);
            }
            if(name.equalsIgnoreCase("depreciaerange2")){
                rmAssetNew.setDepreciaerange2(value);
            }
            if(name.equalsIgnoreCase("depreciaecode2")){
                rmAssetNew.setDepreciaecode2(value);
            }
            if(name.equalsIgnoreCase("usefullife1")){
                rmAssetNew.setUsefullife1(value);
            }
            if(name.equalsIgnoreCase("period1")){
                rmAssetNew.setPeriod1(value);
            }
            if(name.equalsIgnoreCase("usefullife2")){
                rmAssetNew.setUsefullife2(value);
            }
            if(name.equalsIgnoreCase("period2")){
                rmAssetNew.setPeriod2(value);
            }
            if(name.equalsIgnoreCase("costvalue")){
                rmAssetNew.setCostvalue(value);
            }
            if(name.equalsIgnoreCase("depreciation")){
                rmAssetNew.setDepreciation(value);
            }
            if(name.equalsIgnoreCase("impairmentsum")){
                rmAssetNew.setImpairmentsum(value);
            }
            if(name.equalsIgnoreCase("netvalue")){
                rmAssetNew.setNetvalue(value);
            }
            if(name.equalsIgnoreCase("positioncode")){
                rmAssetNew.setPositioncode(value);
            }
            if(name.equalsIgnoreCase("inactivedate")){
                rmAssetNew.setInactivedate(value);
            }
            if(name.equalsIgnoreCase("purchasedate")){
                rmAssetNew.setPurchasedate(value);
            }
            if(name.equalsIgnoreCase("purchaseyear")){
                rmAssetNew.setPurchaseyear(value);
            }
            if(name.equalsIgnoreCase("firstbilling")){
                rmAssetNew.setFirstbilling(value);
            }
            if(name.equalsIgnoreCase("isdisable")){
                rmAssetNew.setIsdisable(value);
            }
            if(name.equalsIgnoreCase("originalasset")){
                rmAssetNew.setOriginalasset(value);
            }
            if(name.equalsIgnoreCase("developasset")){
                rmAssetNew.setDevelopasset(value);
            }
            if(name.equalsIgnoreCase("isrent")){
                rmAssetNew.setIsrent(value);
            }
            if(name.equalsIgnoreCase("islease")){
                rmAssetNew.setIslease(value);
            }
            if(name.equalsIgnoreCase("credentials")){
                rmAssetNew.setCredentials(value);
            }
            if(name.equalsIgnoreCase("demolitionstatu")){
                rmAssetNew.setDemolitionstatu(value);
            }
            if(name.equalsIgnoreCase("wbselement")){
                rmAssetNew.setWbselement(value);
            }
            if(name.equalsIgnoreCase("usefulmonths")){
                rmAssetNew.setUsefulmonths(value);
            }
            if(name.equalsIgnoreCase("sourceassetno")){
                rmAssetNew.setSourceassetno(value);
            }
            if(name.equalsIgnoreCase("isparent")){
                rmAssetNew.setIsparent(value);
            }
            if(name.equalsIgnoreCase("durablelife")){
                rmAssetNew.setDurablelife(value);
            }
            if(name.equalsIgnoreCase("data_source_id")){
                rmAssetNew.setDataSourceId(value);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(name.equalsIgnoreCase("update_time")){
                rmAssetNew.setUpdateTime(sdf.parse(value));
            }
            if(name.equalsIgnoreCase("operation")){
                rmAssetNew.setOperation(value);
            }
            if(name.equalsIgnoreCase("status")){
                rmAssetNew.setStatus(value);
            }
            if(name.equalsIgnoreCase("zzfpzc")){
                rmAssetNew.setZzpfzc(value);
            }
            if(name.equalsIgnoreCase("zz_license")){
                rmAssetNew.setZzLicense(value);
            }
            if(name.equalsIgnoreCase("zz_fz07")){
                rmAssetNew.setZzFz07(value);
            }
            if(name.equalsIgnoreCase("zz_ldqrq")){
                rmAssetNew.setZzLdqrq(value);
            }
            if(name.equalsIgnoreCase("zz_rjjsglbm")){
                rmAssetNew.setZzRjjsglbm(value);
            }
            if(name.equalsIgnoreCase("zz_rjjswhbm")){
                rmAssetNew.setZzRjjswhbm(value);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    private void processingData(){
        log.info("开始跑任务");
        try {
//            //查询总条数
//            int totalCount = rmAssetNewMapper.findRmAssetByCount();
//            if(totalCount >0){// 如果有数据的话
//                for(int i =0;i < j; i +=500){ //每500条数据循环一次
                    long currentTime = System.currentTimeMillis();
                    log.info("分页查询开始时间：" + currentTime);
                    List<RmAssetNew> list = rmAssetNewMapper.findAssetNewByPage(startIndex,2000);
                    log.info("分页查询结束时间" + (System.currentTimeMillis() - currentTime));
                    for(RmAssetNew ra :list){ //循环处理数据
                        AssetsInfo ai = new AssetsInfo();
                        // 20200614 添加字段
                        ai.setPovertyAlleviat(ra.getZzpfzc());
                        ai.setLicense(ra.getZzLicense());
                        ai.setLicenseMethod(ra.getZzFz07());
                        ai.setDueDate(ra.getZzLdqrq());
                        ai.setSoftwareManageDepartCode(ra.getZzRjjsglbm());
                        ai.setSoftwareMaintDepartCode(ra.getZzRjjswhbm());
                        ai.setStatus(ra.getStatus());
                        if(  StringUtils.isBlank(ra.getStatus())){
                            ra.setStatus(" ");
                        }
                        //  -------添加字段-end-----------------

                        // 2020-4-23 资产状态处理
                        switch (ra.getStatus()) {
                            case "5":
                                ra.setStatus("0");
                                break;
                            case "6":
                                ra.setStatus("0");
                                break;
                        }
                        ai.setId(ra.getId());
                        ai.setSpecId(ra.getSpecId());
                        ai.setVersion(ra.getVersion());
                        ai.setSharingId(ra.getShardingId());
                        for (CompanyCode cp : cpList) { // 公司代码
                            if (ra.getBukrs().equals(cp.getTid())) {
                                ai.setCompanyCode(cp.getOrganizeid());
                                ai.setCompanyName(cp.getOrganizeName());
                                break;
                            }
                        }
                        ai.setOsszseq(ra.getOsszseq());
                        ai.setCardCode(ra.getAssetscardcode());
                        ai.setCardSecondCode(ra.getSecondaryassetscardcode());
                        if ("100383".equals(ra.getIsestimate())) {// 是  getIsestimate是否暂估
                            ai.setIsStopEstimate("1");
                        } else if ("100382".equals(ra.getIsestimate())) {// 否
                            ai.setIsStopEstimate("0");
                        }
                        ai.setAssetsCatalog(ra.getAssetCatalogue());
                        for (AssetZCLB zclb : amList) { //资产类别
                            if (ra.getAssetstype().equals(zclb.getTid())) {
                                // 对于无形资产指向到软件类
                                if ("20".equals(zclb.getCode())) {
                                    ai.setAssetsType("软件");
                                    ai.setAssetsType15("00002001");
                                } else {
                                    ai.setAssetsType(zclb.getName());
                                    ai.setAssetsType15(zclb.getCode());
                                }
                                break;
                            }
                        }
                        // 对于资产分类无法在盘点系统中找到的归类到其他
                        if (StringUtils.isBlank(ai.getAssetsType15())) { // 资产分类 15类
                            ai.setAssetsType("其他");
                            ai.setAssetsType15("00002999");
                        }
                        ai.setAssetsName(ra.getDescription());
                        ai.setDescription(ra.getDescription());
                        for (ZYCBLB zy : zyList) { // 作业成本类别
                            if (ra.getWorkcosttype().equals(zy.getTid())) {
                                ai.setCostType(zy.getZZCLB_DEC());
                                break;
                            }
                        }
                        for (JLDW jldw : jldwList) { // 计量单位
                            if (ra.getNamberunit().equals(jldw.getTid())) {
                                ai.setNumberName(jldw.getCHUNIT());
                                break;
                            }
                        }
                        ai.setNumberA(ra.getPNUMBER());
                        ai.setManufacturer(ra.getManufacturer());
                        ai.setSpecProgram(ra.getStandard().replace(",", "，"));
                        ai.setAddress(ra.getAddress().replace("\\", "/"));
                        for (MatterDeptment swglbm : swglbmList) { // 实物管理部门
                            if (ra.getManagedepartment().equals(swglbm.getTid())) {
                                ai.setManageDeptCode(swglbm.getGLDEPTCODE());
                                ai.setManageDeptName(swglbm.getGLDEPTNAME());
                                break;
                            }
                        }
                        for (UseDeptment sybm : sybmList) { // 使用部门
                            if (ra.getUsedepartment().equals(sybm.getTid())) {
                                ai.setUseDeptCode(sybm.getSYDEPTCODE());
                                ai.setUseDeptName(sybm.getSYDEPTNAME());
                                break;
                            }
                        }
                        ai.setLiablePenson(ra.getSupervisor());
                        if (StringUtils.isNotBlank(ra.getAssetcustodianAccount())) { //判断资产管理员账号
                            // 如果是中文姓名
                            if (hasChineseByReg(ra.getAssetcustodian())) {
                                ai.setAssetsMamagePerson(ra.getAssetcustodianAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetcustodianAccount() : ra
                                        .getAssetcustodianAccount() + "@GD");
                                ai.setAssetsMamageName(ra.getAssetcustodian());
                            } else {
                                ai.setAssetsMamagePerson(ra.getAssetcustodian());
                                ai.setAssetsMamageName(ra.getAssetcustodianAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetcustodianAccount() : ra
                                        .getAssetcustodianAccount() + "@GD");
                            }
                        }
                        if (StringUtils.isNotBlank(ra.getAssetkeeperAccount())) { // 判断保管员账号
                            // 如果是中文姓名
                            if (hasChineseByReg(ra.getAssetkeeper())) {
                                ai.setAssetsKeepPerson(ra.getAssetkeeperAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetkeeperAccount() : ra
                                        .getAssetkeeperAccount() + "@GD");
                                ai.setAssetsKeepName(ra.getAssetkeeper());
                            } else {
                                ai.setAssetsKeepPerson(ra.getAssetkeeper());
                                ai.setAssetsKeepName(ra.getAssetkeeperAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetkeeperAccount() : ra
                                        .getAssetkeeperAccount() + "@GD");
                            }
                        }
                        ai.setCapitalDate(ra.getCapitaldate());
                        ai.setWbs(ra.getWbs());
                        for (AssetsAsc aa : aaList) { // 资产归属
                            if (ra.getAssetsrelegation().equals(aa.getTid())) {
                                ai.setAssetsAscri(aa.getOrdtx());
                                break;
                            }
                        }
                        // 是否是客户端资产
                        if ("100383".equals(ra.getIsclientasset())) {// 是
                            ai.setIsClientAssets("1");
                        } else if ("100382".equals(ra.getIsclientasset())) {// 否
                            ai.setIsClientAssets("0");
                        }
                        ai.setClientName(ra.getClientname());
                        for (AssetsNature an : anList) {
                            if (ra.getAssetsnature().equals(an.getTid())) {
                                ai.setAssetsNature(an.getOrdtx());
                                break;
                            }
                        }
                        ai.setEnginRemarks(ra.getNotes());
                        ai.setAbcClass(ra.getAbctype());
                        for (AddReason add : addRList) { // 增加原因
                            if (ra.getAddtionreason().equals(add.getTid())) {
                                ai.setAddtionReason(add.getZjtext());
                                break;
                            }
                        }
                        ai.setSubAttachment(ra.getAccessory());
                        ai.setPgyz(ra.getPgyz());
                        ai.setPgljzj(ra.getPgljzj());
                        ai.setPgljjz(ra.getPgljjz());
                        //是否持有待售资产
                        if ("100383".equals(ra.getIsheldforsale())) {// 是
                            ai.setIsStaySale("1");
                        } else if ("100382".equals(ra.getIsheldforsale())) {// 否
                            ai.setIsStaySale("0");
                        }
                        // 是否逾龄资产
                        if ("100383".equals(ra.getIsoverage())) {// 是
                            ai.setIsOverdue("1");
                        } else if ("100382".equals(ra.getIsoverage())) {// 否
                            ai.setIsOverdue("0");
                        }
                        // 是否待报废
                        if ("100383".equals(ra.getIsforsrcap())) {// 是
                            ai.setIsForSrcap("1");
                        } else if ("100382".equals(ra.getIsforsrcap())) {// 否
                            ai.setIsForSrcap("0");
                        }
                        // 是否闲置资产
                        if ("100383".equals(ra.getIsidle())) {// 是
                            ai.setIsIdle("1");
                        } else if ("100382".equals(ra.getIsidle())) {// 否
                            ai.setIsIdle("0");
                        }
                        // 是否研发产生资产
                        if ("100383".equals(ra.getIsdevelopasset())) {// 是
                            ai.setIsDevelopasset("1");
                        } else if ("100382".equals(ra.getIsdevelopasset())) {// 否
                            ai.setIsDevelopasset("0");
                        }
                        ai.setAreaCode(ra.getAreacode());
                        for (BoroughCompany bc : bcList) { //区县分公司
                            if (ra.getCountyoffices().equals(bc.getTid())) {
                                ai.setCountyOffices(bc.getDfName());
                                break;
                            }
                        }
                        for (Branch br : bList) { // 支局清单
                            if (ra.getBranch().equals(br.getTid())) {
                                ai.setBranch(br.getDfName());
                                break;
                            }
                        }
                        for (BusinessHall bh : bhList) { //营业厅
                            if (ra.getBusinessofficeid().equals(bh.getTid())) {
                                ai.setBusinessOfficesId(bh.getDfName());
                                break;
                            }
                        }
                        for (Station st : stList) { // 基站
                            if (ra.getBtscode().equals(st.getTid())) {
                                ai.setStationName(st.getStationName());
                                break;
                            }
                        }
                        for (Team t : tList) { // 班组
                            if (ra.getTeam().equals(t.getTid())) {
                                ai.setTeam(t.getZz_banzt());
                                break;
                            }
                        }
                        // 是否扩容资产
                        if ("100383".equals(ra.getIsexpansion())) {// 是
                            ai.setIsExpansion("1");
                        } else if ("100382".equals(ra.getIsexpansion())) {// 否
                            ai.setIsExpansion("0");
                        }
                        // 是否减持资产
                        if ("100383".equals(ra.getIsimpairment())) {// 是
                            ai.setIsImpairment("1");
                        } else if ("100382".equals(ra.getIsimpairment())) {// 否
                            ai.setIsImpairment("0");
                        }
                        for (DepreciaeRange dr : drList) { // 折旧范围
                            if (ra.getDepreciaerange1().equals(dr.getTid())) {
                                ai.setDepreciaeRange1(dr.getAreaUsageIND());
                                break;
                            }
                        }
                        // 折旧码
                        for (DepreciaeCode dc : dcList) {
                            if (ra.getDepreciaecode1().equals(dc.getTid())) {
                                ai.setDepreciaeCode1(dc.getAfasl());
                                break;
                            }
                        }
                        // 折旧范围
                        for (DepreciaeRange dr : drList) {
                            if (ra.getDepreciaerange2().equals(dr.getTid())) {
                                ai.setDepreciaeRange2(dr.getAreaUsageIND());
                                break;
                            }
                        }
                        // 折旧码
                        for (DepreciaeCode dc : dcList) {
                            if (ra.getDepreciaecode2().equals(dc.getTid())) {
                                ai.setDepreciaeCode2(dc.getAfasl());
                                break;
                            }
                        }
                        ai.setDurableYears(ra.getUsefullife1());
                        ai.setPeriod(ra.getPeriod1());
                        ai.setDurableYears2(ra.getUsefullife2());
                        ai.setPeriod2(ra.getPeriod2());
                        ai.setOriginalValue(ra.getCostvalue());
                        ai.setAccumDeprec(ra.getDepreciation());
                        ai.setDeprecValue(ra.getImpairmentsum());
                        ai.setWorthValue(ra.getNetvalue());
                        for (PositionCode pc : pcList) { // 科目定位码
                            if (ra.getPositioncode().equals(pc.getTid())) {
                                ai.setPositionCode(pc.getKtogr());
                                break;
                            }
                        }
                        ai.setUnactiveDate(ra.getInactivedate());
                        ai.setPurchasedDate(ra.getPurchasedate());
                        ai.setPurchasedYear(ra.getPurchaseyear());
                        ai.setPurchasedDate(ra.getFirstbilling());
                        // 是否停用
                        if ("100383".equals(ra.getIsdisable())) {// 是
                            ai.setIsDisable("1");
                        } else if ("100382".equals(ra.getIsdisable())) {// 否
                            ai.setIsDisable("0");
                        }
                        ai.setOriginalCode(ra.getOriginalasset());
                        // 是否出租
                        if ("100383".equals(ra.getIsrent())) {// 是
                            ai.setIsRent("1");
                        } else if ("100382".equals(ra.getIsrent())) {// 否
                            ai.setIsRent("0");
                        }
                        // 是否融资租入
                        if ("100383".equals(ra.getIslease())) {// 是
                            ai.setIsLease("1");
                        } else if ("100382".equals(ra.getIslease())) {// 否
                            ai.setIsLease("0");
                        }
                        ai.setCardEntials(ra.getCredentials());
                        ai.setDepreciaeStatu(ra.getDemolitionstatu());
                        ai.setUsefulmonths(ra.getUsefulmonths());
                        ai.setSourceCode(ra.getSourceassetno());
                        // 是否父卡
                        if ("100383".equals(ra.getIsparent())) {// 是
                            ai.setIsParent("1");
                        } else if ("100382".equals(ra.getIsparent())) {// 否
                            ai.setIsParent("0");
                        }
                        ai.setDurableLife(ra.getDurablelife());
                        ai.setParentAssetsCode(ra.getParentAssetId());
                        String prctr = "";
                        // 成本中心
                        for (HBCostCenter costCenter : cbzxList) {
                            if (ra.getCostcenter().equals(costCenter.getTid())) {
                                ai.setCostCode(costCenter.getCOSTCENTER());
                                ai.setCostName(costCenter.getCOSTCENTERREMARK());
                                prctr = costCenter.getPRCTR();
                                break;
                            }
                        }
                        if (StringUtils.isNotBlank(ai.getCostCode())) { // 成本中心编号
                            if (StringUtils.isNotBlank(prctr)) { // 判断利润中心是否为空
                                String city = "";
                                for (ProfitCenterDTO profitCenterDTO : pList) {
                                    if (prctr.equals(profitCenterDTO.getProfitCode())) { // 如果对象中的利润中心编码有这条数据
                                        String rId = profitCenterDTO.getRId(); // 则把regin_id 赋给rid
                                        if (StringUtils.isNotBlank(rId)) {
                                            city = rId;
                                        } else { // 如果rid为空，则把地市编码赋给city
                                            city = profitCenterDTO.getCityCode();
                                        }
                                        break;
                                    }
                                }
                                if (StringUtils.isNotBlank(city)) { // 如果city有值
                                    //如果资产分类或者原值为空或者为零则存入临时表
                                    if (ai.getAssetsType15().equals("19") // 领购
                                            || ai.getAssetsType15().equals("60") //投资性房产
                                            || ai.getAssetsType15().equals("30") //长摊资产
                                            || ai.getAssetsType15().equals("40")// 待摊资产
                                            || StringUtils.isBlank(ai
                                            .getOriginalValue()) // 原值
                                            || Double
                                            .parseDouble(ai.getOriginalValue()) <= 0) {
//                                        //根据companyCode和cardCord查询一个AssetsInfolist
//                                        Long nowTime3 = System.currentTimeMillis();
//                                        log.info("临时表数据查询开始时间"+System.currentTimeMillis());
//                                        List<AssetsInfo> aiList =assetsInfoMapper.findAssetsInfoTmepByComPanyCodeAndCardCode(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
//                                        log.info("临时表数据查询结束时间"+(System.currentTimeMillis()-nowTime3));
//                                        ai.setAssetsStatus("0".equals(ra.getStatus()) ? "0":"1");
//                                        if (aiList != null && aiList.size() > 0) { // 如果aiList不为空,则证明数据库有这条数据，则修改
//                                            AssetsInfo aiOld = aiList.get(0);
//                                            ai.setCity("tmp_"+city);
//                                            ai.setId(aiOld.getId());
//                                            ai.setCurrentNodeId(aiOld.getCurrentNodeId());
//                                            ai.setIsOutside(aiOld.getIsOutside());
//                                            ai.setIsSubmitted(aiOld.getIsSubmitted());
//                                            Long nowTime2 = System.currentTimeMillis();
//                                            log.info("临时表数据修改开始时间"+System.currentTimeMillis());
//                                            assetsInfoMapper.updateAssetsInfo(ai);
//                                            log.info("临时表数据修改结束时间"+(System.currentTimeMillis()-nowTime2));
//                                        }else{ // 如果没有，则新增
//                                            ai.setCity("tmp_"+city);
//                                            ai.setId(new ObjectId().toHexString());
//                                            Long nowTime = System.currentTimeMillis();
//                                            log.info("临时表数据插入开始时间"+System.currentTimeMillis());
//                                            assetsInfoMapper.insertAssetsInfo(ai);
//                                            log.info("临时表数据插入结束时间"+(System.currentTimeMillis()-nowTime));
//                                        }
                                    }else {
                                        ai.setCity(city);
                                        // 2020-4-23 加了两个状态码，如果资产状态是0，直接把资产置为无效
                                        if ("D".equals(ra.getOperation()) // 操作类型
                                                || "0".equals(ra.getStatus())) {
                                            Long nowTime4 = System.currentTimeMillis();
                                            log.info("资产卡片表数据置为无效开始时间"+System.currentTimeMillis());
                                            assetsInfoMapper.updateAssetsStatusAndLastUpdateTime(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                            log.info("资产卡片表数据置为无效结束时间"+(System.currentTimeMillis()-nowTime4));
                                        }else{
                                            Long nowTime5 = System.currentTimeMillis();
                                            log.info("资产卡片表数据是否重复查询开始时间"+System.currentTimeMillis());
                                            List<AssetsInfo> aiList =  assetsInfoMapper.findAssetsInfoByComPanyCodeAndCardCode(ra.getAssetscardcode(),city);
                                            log.info("资产卡片表数据是否重复查询结束时间"+(System.currentTimeMillis()-nowTime5));
                                            ai.setAssetsStatus("0".equals(ra.getStatus()) ? "0":"1");
                                            if (aiList.size() > 0) {
                                                AssetsInfo aiOld = aiList.get(0);
                                                String newId = ai.getId();
                                                ai.setId(aiOld.getId());
                                                ai.setCurrentNodeId(aiOld.getCurrentNodeId());
                                                ai.setIsOutside(aiOld.getIsOutside());
                                                ai.setIsSubmitted(aiOld.getIsSubmitted());
                                                ai.setCheckTime(aiOld.getCheckTime());
                                                ai.setCheckStatus(aiOld.getCheckStatus());
                                                ai.setCheckResult(aiOld.getCheckResult());
                                                ai.setUserName(aiOld.getUserName());
                                                Long nowTime6 = System.currentTimeMillis();
                                                log.info("卡实表数据查询开始时间"+System.currentTimeMillis());
                                                List<Map<String,Object>> raeList = assetsInfoMapper.findAssetEntityNewByAssetId(newId);
                                                log.info("卡实表数据查询结束时间"+(System.currentTimeMillis()-nowTime6));
                                                if(raeList.size()>0){
                                                    ai.setRelationId(MapUtils.getString(raeList.get(0),"relationID"));
                                                    ai.setRelationIdMark("1");
                                                }
                                                aiOld.setCity(city);
                                                Long nowTime7 = System.currentTimeMillis();
                                                log.info("资产卡片表表数据修改开始时间"+System.currentTimeMillis());
                                                assetsInfoMapper.updateAssetsInfo(ai);
                                                log.info("资产卡片表表数据修改结束时间"+(System.currentTimeMillis()-nowTime7));
                                            }else{
                                                List<Map<String,Object>> raeList = assetsInfoMapper.findAssetEntityNewByAssetId(ai.getId());
                                                if (raeList.size() > 0) {
                                                    ai.setRelationId(MapUtils
                                                            .getString(raeList.get(0),
                                                                    "relationID")
                                                            .substring(1));
                                                    ai.setRelationIdMark("1");
                                                }
                                                ai.setCity(city);
                                                ai.setId(new ObjectId().toHexString());
                                                Long nowTime8 = System.currentTimeMillis();
                                                log.info("资产卡片表表数据插入开始时间"+System.currentTimeMillis());
                                                assetsInfoMapper.insertAssetsInfo(ai);
                                                log.info("资产卡片表表数据插入结束时间"+(System.currentTimeMillis()-System.currentTimeMillis()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Long nowTime = System.currentTimeMillis();
                        log.info("数据删除开始时间"+System.currentTimeMillis());
                        rmAssetNewMapper.deleteById(ra.getId());
                        log.info("数据删除结束时间"+(System.currentTimeMillis()-nowTime));
                    }
//                }
//            }
            log.info("跑完了");
            log.info("跑完了");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 是否包含汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘'”、！￥等符号）<br>
     *
     * @param str
     * @return
     */
    public static boolean hasChineseByReg(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str).find();
    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        int count = 1000; //每条线程去处理1000条数据
//        int totalCount = rmAssetNewMapper.findRmAssetByCount();
//        int runSize = totalCount/1000;
//
//        if(runSize<1){
//            runSize = 1;
//        }
//        //创建两个计数器
//        CountDownLatch begin = new CountDownLatch(1);
//        CountDownLatch end = new CountDownLatch(runSize);
//        for(int i=0;i<runSize;i++){ //循环创建线程
//            if((i+1)==runSize){//如果是最后一个
//                int startIndex = (i*count);
//                int endIndex = totalCount-startIndex;
//            }else{
//                int startIndex = (i*count);
//                               int endIndex = ((i+1)*count);
//            }
//            executorService.execute(processingData());
//        }
//        begin.countDown();
//        end.await();
////        processingData();
//    }
}
