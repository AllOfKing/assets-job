package com.rookie.opcua.job;

import com.rookie.opcua.dto.AssetInfoForInventCountDTO;
import com.rookie.opcua.dto.AssetsInfoCountMssDTO;
import com.rookie.opcua.dto.AssetsInventCountDTO;
import com.rookie.opcua.dto.RegularInventCountDTO;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Component
public class RegularInventCountJob implements ApplicationRunner {

    @Autowired
    private RegularInventCountMapper regularInventCountMapper;

    @Autowired
    private InventProjectMapper inventProjectMapper;

    @Autowired
    private AssetsInfoMapper assetsInfoMapper;

    @Autowired
    private InventOrganMapper inventOrganMapper;

    @Autowired
    private MatterDepartmentMapper matterDepartmentMapper;

    @Autowired
    private SyncAssetsCardMapper syncAssetsCardMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private RegularMssCountMapper regularMssCountMapper;


    /**资产分类*/
    private String[] assetsTypes = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","18",
            "00002001","00002002","00002003","00002004","00002005","00002999"};
    public void countAssetsByType(){
        for (String assetsType : assetsTypes) {
            //创建表
            regularInventCountMapper.createRegularInventCount(assetsType);
            //获取广东公司下区域集合，固定传09
            List<Region> regionList = regionMapper.findRegionByParentId("09");
            int n_count = 0;
            List<RegularInventCount> reCounts = new ArrayList<>();
            //省份需要汇总所有资产
            RegularInventCount province = new RegularInventCount();
            RegularInventCountDTO regularInventCountDTO = new RegularInventCountDTO();

            province.setId(new ObjectId().toHexString());
            province.setCompanyCode("");
            province.setDeptId("09");
            province.setDeptName("广东公司");
            province.setPId("0");
            province.setRegionId("09");
            province.setIsLeaf(1);
            //遍历区域分别统计
            for (Region region : regionList) {
                if("09".equals(region.getId())){
                    continue;
                }
                List<InventOrgan> inventOrgans = inventOrganMapper.findInventOrganListByReginId(region.getId());
                //获取区域下所有实物管理部门及盘点组织对应的资产卡片
                List<AssetInfoForInventCountDTO> dataList = assetsInfoMapper.regularInfoDataSummanry(region.getId(),assetsType);
                List<MatterDepartment> mds =matterDepartmentMapper.findMatterDepartmentBycityCode(region.getId());
                //遍历盘点组织统计资产盘点情况
                for (InventOrgan inventOrgan : inventOrgans) {
                    List<AssetInfoForInventCountDTO> rList = getAssetsCountList(dataList, inventOrgan);
                    if(rList.size()<=0){
                        for (MatterDepartment md: mds) {
                            n_count++;
                            RegularInventCount reCount = new RegularInventCount();
                            reCount.setId(new ObjectId().toHexString());
                            reCount.setCompanyCode(StringUtils.isBlank(md.getCompanyEntity()) ? "" : md.getCompanyEntity());
                            reCount.setMaterDeptCode(md.getMaterDeptCode());
                            reCount.setMaterDeptName(md.getMaterDeptName());
                            reCount.setDeptId(inventOrgan.getId());
                            reCount.setDeptName(inventOrgan.getDescription());
                            reCount.setPId(inventOrgan.getParentId());
                            reCount.setRegionId(inventOrgan.getRegionId());
                            reCount.setCardNum(0);
                            reCount.setNotInvent(0);
                            reCount.setInvent(0);
                            reCount.setAppScanNum(0);
                            reCount.setAppNotScanNum(0);
                            reCount.setPcNum(0);
                            reCount.setInerrantNum(0);
                            reCount.setWrongNum(0);
                            reCount.setLossesNum(0);
                            reCount.setAddNum(0);
                            reCount.setWorthValue(0);
                            //判断盘点组织是否存在子组织
                            reCount.setIsLeaf(isLeaf(inventOrgan.getId(), inventOrgans));
                            reCounts.add(reCount);
                        }
                    }else {
                        for (AssetInfoForInventCountDTO assetInfoForInventCountDTO : rList) {
                            n_count++;
                            RegularInventCount reCount = new RegularInventCount();
                            reCount.setId(new ObjectId().toHexString());
                            reCount.setCompanyCode(assetInfoForInventCountDTO.getCompanyCode());
                            reCount.setMaterDeptCode(assetInfoForInventCountDTO.getManageDeptCode());
                            reCount.setMaterDeptName(assetInfoForInventCountDTO.getManageDeptName());
                            reCount.setDeptId(inventOrgan.getId());
                            reCount.setDeptName(inventOrgan.getDescription());
                            reCount.setPId(inventOrgan.getParentId());
                            reCount.setRegionId(inventOrgan.getRegionId());
                            reCount.setCardNum(assetInfoForInventCountDTO.getCardNum());
                            reCount.setNotInvent(assetInfoForInventCountDTO.getNotInvent());
                            reCount.setInvent(assetInfoForInventCountDTO.getInvent());
                            reCount.setAppScanNum(assetInfoForInventCountDTO.getAppScanNum());
                            reCount.setAppNotScanNum(assetInfoForInventCountDTO.getAppNotScanNum());
                            reCount.setPcNum(assetInfoForInventCountDTO.getPcNum());
                            reCount.setInerrantNum(assetInfoForInventCountDTO.getInerrantNum());
                            reCount.setWrongNum(assetInfoForInventCountDTO.getWrongNum());
                            reCount.setLossesNum(assetInfoForInventCountDTO.getLossesNum());
                            reCount.setAddNum(assetInfoForInventCountDTO.getAddNum());
                            reCount.setWorthValue(assetInfoForInventCountDTO.getWorthValue());
                            //判断盘点组织是否存在子组织
                            reCount.setIsLeaf(isLeaf(inventOrgan.getId(), inventOrgans));

                            //总公司汇总
                            province.setCardNum(province.getCardNum() + assetInfoForInventCountDTO.getCardNum());
                            province.setNotInvent(province.getNotInvent() + assetInfoForInventCountDTO.getNotInvent());
                            province.setInvent(province.getInvent() + assetInfoForInventCountDTO.getInvent());
                            province.setAppScanNum(province.getAppScanNum() + assetInfoForInventCountDTO.getAppScanNum());
                            province.setAppNotScanNum(province.getAppNotScanNum() + assetInfoForInventCountDTO.getAppNotScanNum());
                            province.setPcNum(province.getPcNum() + assetInfoForInventCountDTO.getPcNum());
                            province.setInerrantNum(province.getInerrantNum() + assetInfoForInventCountDTO.getInerrantNum());
                            province.setWrongNum(province.getWrongNum() + assetInfoForInventCountDTO.getWrongNum());
                            province.setLossesNum(province.getLossesNum() + assetInfoForInventCountDTO.getLossesNum());
                            province.setAddNum(province.getAddNum() + assetInfoForInventCountDTO.getAddNum());
                            province.setWorthValue(province.getWorthValue() + assetInfoForInventCountDTO.getWorthValue());
                            reCounts.add(reCount);
                        }
                    }
                }
            }
            //清空数据
            regularInventCountMapper.truncateRegularInventCount(assetsType);
            if(reCounts.size()<=0){
                continue;
            }
            reCounts.add(province);
            regularInventCountDTO.setAssetsType(assetsType);
            regularInventCountDTO.setList(reCounts);
            if(reCounts.size()>500){
                List<RegularInventCount> regularInventCounts = new ArrayList<RegularInventCount>();
                int runSize =reCounts.size()/500;
                runSize = (int) Math.ceil(runSize);
                for(int i=0;i<runSize;i++){
                    if(i+1 == runSize){//如果是最后一次
                        regularInventCounts = reCounts.subList(i*500,reCounts.size());
                    }else{
                        regularInventCounts = reCounts.subList(i*500,(i+1)*500);
                    }
                    regularInventCountDTO.setList(regularInventCounts);
                    regularInventCountMapper.insertInventCount(regularInventCountDTO);
                }
            }else{
                //批量插入
                regularInventCountMapper.insertInventCount(regularInventCountDTO);
            }

        }
    }



    public void countAssets() {
        //汇总各分类的资产到总表
        StringBuffer sql = new StringBuffer();
        for(int i=0;i<assetsTypes.length;i++){
            String assetsType = assetsTypes[i];
            sql.append(" select * from t_regular_invent_count_"+assetsType);
            if(i < assetsTypes.length-1){
                sql.append(" union all ");
            }
        }
        List<AssetsInventCountDTO> countList = regularInventCountMapper.findAssetsInventCountForSummary(sql.toString());
        List<RegularInventCount> reCounts = new ArrayList<RegularInventCount>();
        for(AssetsInventCountDTO assetsInventCountDTO :countList) {
            RegularInventCount reCount = new RegularInventCount();
            reCount.setId(new ObjectId().toHexString());
            reCount.setCompanyCode(assetsInventCountDTO.getCompanyCode());
            reCount.setMaterDeptCode(assetsInventCountDTO.getMaterDeptCode());
            reCount.setMaterDeptName(assetsInventCountDTO.getMaterDeptName());
            reCount.setDeptId(assetsInventCountDTO.getDeptId());
            reCount.setDeptName(assetsInventCountDTO.getDeptName());
            reCount.setPId(assetsInventCountDTO.getPId());
            reCount.setRegionId(assetsInventCountDTO.getRegionId());
            reCount.setCardNum(Integer.parseInt(assetsInventCountDTO.getCardNum()));
            reCount.setNotInvent(Integer.parseInt(assetsInventCountDTO.getNotInvent()));
            reCount.setInvent(Integer.parseInt(assetsInventCountDTO.getInvent()));
            reCount.setAppScanNum(Integer.parseInt(assetsInventCountDTO.getAppScanNum()));
            reCount.setAppNotScanNum(Integer.parseInt(assetsInventCountDTO.getAppNotScanNum()));
            reCount.setPcNum(Integer.parseInt(assetsInventCountDTO.getPcNum()));
            reCount.setInerrantNum(Integer.parseInt(assetsInventCountDTO.getInerrantNum()));
            reCount.setWrongNum(Integer.parseInt(assetsInventCountDTO.getWrongNum()));
            reCount.setLossesNum(Integer.parseInt(assetsInventCountDTO.getLossesNum()));
            reCount.setAddNum(Integer.parseInt(assetsInventCountDTO.getAddNum()));
            reCount.setIsLeaf(Integer.parseInt(assetsInventCountDTO.getIsLeaf()));
            reCount.setWorthValue(Double.parseDouble(assetsInventCountDTO.getWorthValue()));
            reCounts.add(reCount);
        }
        //清空主表
        regularInventCountMapper.truncateRegularInventCountSummary();
        List<RegularInventCount> list = new ArrayList<RegularInventCount>();
        if(reCounts.size()>500){
            int runSize =reCounts.size()/500;
            runSize = (int) Math.ceil(runSize);
            for(int i=0;i<runSize;i++){
                if(i+1 == runSize){//如果是最后一次
                    list = reCounts.subList(i*500,reCounts.size());
                }else{
                    list = reCounts.subList(i*500,(i+1)*500);
                }
                regularInventCountMapper.insertInventCountForSummary(list);
            }
        }else{
            regularInventCountMapper.insertInventCountForSummary(reCounts);
        }
    }

    public void countMssHandle(){
        //获取广东公司下区域集合
        List<Region> regionList = regionMapper.findRegionByParentId("09");
        int n_count = 0;
        List<RegularMssCount> reCounts = new ArrayList<>();
        //省份需要汇总所有资产
        RegularMssCount province = new RegularMssCount();
        province.setId(new ObjectId().toHexString());
        province.setDeptId("09");
        province.setDeptName("广东公司");
        province.setPId("0");
        province.setRegionId("09");
        province.setIsLeaf(1);
        //遍历区域分别统计
        for (Region region : regionList) {
            //广东公司跳过
            if("09".equals(region.getId())){
                continue;
            }
            syncAssetsCardMapper.craeteSynAssetsCard(region.getId());
            assetsInfoMapper.createAssetsInfoTmp(region.getId());
            //获取区域下所有盘点组织
            List<InventOrgan> inventOrgans = inventOrganMapper.findInventOrganListByReginId(region.getId());
            n_count += inventOrgans.size();
            //获取区域下所有实物管理部门及盘点组织对应的资产卡片
            List<AssetsInfoCountMssDTO> dataList = assetsInfoMapper.findCountMssHandleByOrgan(region.getId());
            //遍历盘点组织统计资产盘点情况
            for (InventOrgan inventOrgan : inventOrgans) {
                AssetsInfoCountMssDTO assetsInfoCountMssDTO = filterAssets(dataList,inventOrgan);
                if(assetsInfoCountMssDTO != null){
                    RegularMssCount rmCount = new RegularMssCount();
                    rmCount.setId(new ObjectId().toHexString());
                    rmCount.setDeptId(inventOrgan.getId());
                    rmCount.setDeptName(inventOrgan.getDescription());
                    rmCount.setPId(inventOrgan.getParentId());
                    rmCount.setRegionId(inventOrgan.getRegionId());
                    rmCount.setCardNum(assetsInfoCountMssDTO.getCardNum());
                    rmCount.setGxwcf(assetsInfoCountMssDTO.getGxwcf());
                    rmCount.setGxycf(assetsInfoCountMssDTO.getGxycf());
                    rmCount.setDbwcf(assetsInfoCountMssDTO.getDbwcf());
                    rmCount.setDbycf(assetsInfoCountMssDTO.getDbycf());
                    rmCount.setPkwcf(assetsInfoCountMssDTO.getPkwcf());
                    rmCount.setPkycf(assetsInfoCountMssDTO.getPkycf());
                    rmCount.setPywcf(assetsInfoCountMssDTO.getPywcf());
                    rmCount.setPyycf(assetsInfoCountMssDTO.getPyycf());
                    rmCount.setCflwcf(assetsInfoCountMssDTO.getCflwcf());
                    rmCount.setCflycf(assetsInfoCountMssDTO.getCflycf());
                    //判断盘点组织是否存在子组织
                    rmCount.setIsLeaf(isLeaf(inventOrgan.getId(), inventOrgans));
                    //判断盘点组织是否存在子组织
                    rmCount.setIsLeaf(isLeaf(inventOrgan.getId(), inventOrgans));
                    //总公司汇总
                    province.setCardNum(province.getCardNum() + rmCount.getCardNum());
                    province.setGxwcf(province.getGxwcf() + rmCount.getGxwcf());
                    province.setGxycf(province.getGxycf() + rmCount.getGxycf());
                    province.setDbwcf(province.getDbwcf() + rmCount.getDbwcf());
                    province.setDbycf(province.getDbycf() + rmCount.getDbycf());
                    province.setPkwcf(province.getPkwcf() + rmCount.getPkwcf());
                    province.setPkycf(province.getPkycf() + rmCount.getPkycf());
                    province.setPywcf(province.getPywcf() + rmCount.getPywcf());
                    province.setPyycf(province.getPyycf() + rmCount.getPyycf());
                    province.setCflwcf(province.getCflwcf() + rmCount.getCflwcf());
                    province.setCflycf(province.getCflycf() + rmCount.getCflycf());

                    reCounts.add(rmCount);
                }
            }
        }
        //清空原有的统计数据
        regularMssCountMapper.truncateRegularMssCount();
        //生成批量插入语句
        if(reCounts.size()>0){
            reCounts.add(province);
            List<RegularMssCount> list = new ArrayList<RegularMssCount>();
            if(reCounts.size()>500){

                int runSize =reCounts.size()/500;
                runSize = (int) Math.ceil(runSize);
                for(int i=0;i<runSize;i++){
                    if(i+1 == runSize){//如果是最后一次
                        list = reCounts.subList(i*500,reCounts.size());
                    }else{
                        list = reCounts.subList(i*500,(i+1)*500);
                    }
                    regularMssCountMapper.insertRegularMssCountList(list);
                }
            }else{
                regularMssCountMapper.insertRegularMssCountList(reCounts);
            }
        }
    }

    public AssetsInfoCountMssDTO filterAssets(List<AssetsInfoCountMssDTO> dataList,InventOrgan inventOrgan){
        for(AssetsInfoCountMssDTO assetsInfoCountMssDTO :dataList){
            if(inventOrgan.getId().equals(assetsInfoCountMssDTO.getOrganId())){
                return  assetsInfoCountMssDTO;
            }
        }
        return  null;
    }



    public List<AssetInfoForInventCountDTO> getAssetsCountList(List<AssetInfoForInventCountDTO> dataList, InventOrgan inventOrgan) {
        List<AssetInfoForInventCountDTO> rList = new ArrayList<AssetInfoForInventCountDTO>();
        for (AssetInfoForInventCountDTO assetInfoForInventCountDTO : dataList) {
            if(inventOrgan.getId().equals(assetInfoForInventCountDTO.getOrganId())){
                rList.add(assetInfoForInventCountDTO);
            }/*else{
				//如果不是本地市的盘点组织就置为空
				map.put("materDeptCode", "");
				map.put("materDeptName", "");
				map.put("organId", "");
				rList.add(map);
			}*/
        }
        return rList;
    }


    public int isLeaf(String organId, List<InventOrgan> inventOrgans){
        for (InventOrgan inventOrgan : inventOrgans) {
            //if(StringUtils.isNotBlank(inventOrgan.getParentId())){
            if(organId.equals(inventOrgan.getParentId())){
                return 1;
            }
            //}
        }
        return 0;
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始");
        countAssetsByType();
        countAssets();
        countMssHandle();
        log.info("结束");
    }
}
