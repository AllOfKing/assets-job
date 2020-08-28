package com.rookie.opcua.job.Runner;

import com.rookie.opcua.dto.ProfitCenterDTO;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
@Slf4j
public class HandleAssetsDataRunner implements Runnable{
    private RmAssetNewMapper rmAssetNewMapper;
    private AssetsInfoMapper assetsInfoMapper;

    private List<RmAssetNew> raList;

    private List<CompanyCode> cpList;
    private List<AssetZCLB> amList;
    private List<ZYCBLB> zyList;
    private List<JLDW> jldwList;
    private List<MatterDeptment> swglbmList;
    private List<UseDeptment> sybmList;
    private List<AssetsAsc> aaList;
    private List<AssetsNature> anList;
    private List<AddReason> addRList;
    private List<BoroughCompany> bcList;
    private List<Branch> bList;
    private List<BusinessHall> bhList;
    private List<Station> stList;
    private List<Team> tList;
    private List<DepreciaeRange> drList;
    private List<DepreciaeCode> dcList;
    private List<PositionCode> pcList;
    private List<HBCostCenter> cbzxList;
    private List<ProfitCenterDTO> pList;

    public HandleAssetsDataRunner(RmAssetNewMapper rmAssetNewMapper,
                                  AssetsInfoMapper assetsInfoMapper,
                                  List<RmAssetNew> raList, List<CompanyCode> cpList,
                                  List<AssetZCLB> amList, List<ZYCBLB> zyList,
                                  List<JLDW> jldwList, List<MatterDeptment> swglbmList,
                                  List<UseDeptment> sybmList, List<AssetsAsc> aaList,
                                  List<AssetsNature> anList, List<AddReason> addRList,
                                  List<BoroughCompany> bcList, List<Branch> bList,
                                  List<BusinessHall> bhList, List<Station> stList,
                                  List<Team> tList, List<DepreciaeRange> drList,
                                  List<DepreciaeCode> dcList, List<PositionCode> pcList,
                                  List<HBCostCenter> cbzxList,
                                  List<ProfitCenterDTO> pList) {
        this.rmAssetNewMapper = rmAssetNewMapper;
        this.assetsInfoMapper = assetsInfoMapper;
        this.raList = raList;
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
    }

    @Override
    public void run() {
        List<AssetsInfo> list = new ArrayList<>();
        log.info("进入具体的业务");
        try {
            for(RmAssetNew ra :raList){ //循环处理数据
                AssetsInfo ai = new AssetsInfo();
                // 20200614 添加字段
                ai.setPovertyAlleviat(ra.getZzpfzc());
                ai.setLicense(ra.getZzLicense());
                ai.setLicenseMethod(ra.getZzFz07());
                ai.setDueDate(ra.getZzLdqrq());
                ai.setSoftwareManageDepartCode(ra.getZzRjjsglbm());
                ai.setSoftwareMaintDepartCode(ra.getZzRjjswhbm());
                ai.setStatus(ra.getStatus());
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
                        log.info("city的值"+city);
                        log.info("city的值"+city);

                        if (StringUtils.isNotBlank(city)) { // 如果city有值
                            if (ai.getAssetsType15().equals("19") // 领购
                                    || ai.getAssetsType15().equals("60") //投资性房产
                                    || ai.getAssetsType15().equals("30") //长摊资产
                                    || ai.getAssetsType15().equals("40")// 待摊资产
                                    || StringUtils.isBlank(ai
                                    .getOriginalValue()) // 原值
                                    || Double
                                    .parseDouble(ai.getOriginalValue()) <= 0) {
                                //根据companyCode和cardCord查询一个AssetsInfolist
                                List<AssetsInfo> aiList =assetsInfoMapper.findAssetsInfoTmepByComPanyCodeAndCardCode(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                ai.setAssetsStatus("0".equals(ra.getStatus()) ? "0":"1");
                                if (aiList != null && aiList.size() > 0) { // 如果aiList不为空,则证明数据库有这条数据，则修改
                                    AssetsInfo aiOld = aiList.get(0);
                                    BeanUtils
                                            .copyProperties(ai, aiOld,
                                                    new String[] { "id",
                                                            "currentNodeId",
                                                            "isOutside",
                                                            "isSubmitted" });
                                    assetsInfoMapper.updateAssetsInfo(aiOld);
                                }else{ // 如果没有，则新增
                                    ai.setCity(city);
                                    AssetsInfo assetsInfo = assetsInfoMapper.findAssetsInfoById(ai);
                                    if(assetsInfo == null){
                                        assetsInfoMapper.insertAssetsInfo(ai);
                                    }
                                }
                            }else {
                                // 2020-4-23 加了两个状态码，如果资产状态是0，直接把资产置为无效
                                if ("D".equals(ra.getOperation()) // 操作类型
                                        || "0".equals(ra.getStatus())) {
                                    assetsInfoMapper.updateAssetsStatusAndLastUpdateTime(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                }else{
                                    List<AssetsInfo> aiList =  assetsInfoMapper.findAssetsInfoTmepByComPanyCodeAndCardCode(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                    ai.setAssetsStatus("0".equals(ra.getStatus()) ? "0":"1");
                                    if (aiList != null && aiList.size() > 0) {
                                        AssetsInfo aiOld = aiList.get(0);
                                        BeanUtils.copyProperties(ai, aiOld,
                                                new String[] { "id",
                                                        "currentNodeId",
                                                        "isOutside",
                                                        "isSubmitted",
                                                        "checkTime",
                                                        "checkStatus",
                                                        "checkResult",
                                                        "userName" });
                                        List<Map<String,Object>> raeList = assetsInfoMapper.findAssetEntityNewByAssetId(ai.getId());
                                        if(raeList.size()>0){
                                            aiOld.setRelationId(MapUtils.getString(raeList.get(0),"relationID"));
                                            aiOld.setRelationIdMark("1");
                                        }
                                        aiOld.setCity(city);
                                        assetsInfoMapper.updateAssetsInfo(aiOld);
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
                                        AssetsInfo assetsInfo = assetsInfoMapper.findAssetsInfoById(ai);
                                        if(assetsInfo == null){
                                            assetsInfoMapper.insertAssetsInfo(ai);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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



}
