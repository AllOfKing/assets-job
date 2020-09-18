package com.rookie.opcua.job;

import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@Component
public class ReqularAndSpeciaYearCountJob implements ApplicationRunner{
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private AssetsInfoMapper assetsInfoMapper;
    @Autowired
    private InventProjectMapper inventProjectMapper;
    @Autowired
    private ReqularAndSpecialYearCountMapper reqularAndSpecialYearCountMapper;
    @Autowired
    private InventOrganMapper inventOrganMapper;

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void run(){
        log.info("进入常态化与专线数据获取任务");
        try{
            //先清空表
            reqularAndSpecialYearCountMapper.truncate();
            //先查询部门
            List<Region> regionList = regionMapper.findRegionByParentId("09");
            // 获取年份
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int years[] = {currentYear-1,currentYear};
            //根据部门和年份去统计所有资产卡片的卡片数量，已盘点，未盘点，净值
            //先去查询常态化
            //遍历所有地市去查询
            for(int year :years){// 循环当前年和前一年
                for (Region region : regionList) {
                    //根据地市编号去查询分表
                    //根据年度区分
                    //获取常态化
                    List<ReqularAndSpecialYearCount> reqularYearCountList = assetsInfoMapper.findReqularYearCount(year+"",region.getId());


                    //获取专项
                    //根据年份和地市获取批次id
                    List<ReqularAndSpecialYearCount> specialYearCountList = new ArrayList<ReqularAndSpecialYearCount>();
                    List<InventProject> inventProjects = inventProjectMapper.findBatchIdByYear(region.getId(),year+"");
                    for(InventProject inventProject :inventProjects){
                        String batchId = inventProject.getId();
                        //根据批次id和地市去查询
                        List<ReqularAndSpecialYearCount> specialYearCountForBatchList = assetsInfoMapper.findSpecialYearCount(region.getId(),batchId);
                        specialYearCountList.addAll(specialYearCountForBatchList);
                    }
                    // 创建一个map，将常态化和专项的部门id和公司代码设为key，value先放一个空的ReqularAndSpecialYearCount
                    // 在后面具体设值的时候再替换
                    // 将常态化的部门id和公司代码设为key
                    Map<String,ReqularAndSpecialYearCount> map = new ConcurrentHashMap<String,ReqularAndSpecialYearCount>();
                    for(ReqularAndSpecialYearCount reqularYearCount: reqularYearCountList){
                        map.put(reqularYearCount.getDeptId()+"-"+reqularYearCount.getCompanyCode(),new ReqularAndSpecialYearCount());
                    }
                    // 将专项的部门id和公司代码设为key
                    for(ReqularAndSpecialYearCount specialYearCount: specialYearCountList){
                        map.put(specialYearCount.getDeptId()+"-"+specialYearCount.getCompanyCode(),new ReqularAndSpecialYearCount());
                    }
                    List<ReqularAndSpecialYearCount> list = new ArrayList<ReqularAndSpecialYearCount>();
                    for(String deptIdAndCompanyCode:map.keySet()){
                        for( ReqularAndSpecialYearCount reqularYearCount : reqularYearCountList){
                            if(deptIdAndCompanyCode.equals(reqularYearCount.getDeptId()+"-"+reqularYearCount.getCompanyCode())){
                                ReqularAndSpecialYearCount reqularYearCountMap = map.get(deptIdAndCompanyCode);
                                reqularYearCountMap.setInventYear(reqularYearCount.getInventYear());
                                reqularYearCountMap.setPId(reqularYearCount.getPId());
                                reqularYearCountMap.setCompanyCode(reqularYearCount.getCompanyCode());
                                reqularYearCountMap.setProfitGroupCode(reqularYearCount.getProfitGroupCode());
                                reqularYearCountMap.setDeptId(reqularYearCount.getDeptId());
                                reqularYearCountMap.setDeptName(reqularYearCount.getDeptName());
                                reqularYearCountMap.setRegionId(region.getId());
                                reqularYearCountMap.setIsLeaf(reqularYearCount.getIsLeaf());

                                reqularYearCountMap.setRegularInvent(reqularYearCount.getRegularInvent());
                                reqularYearCountMap.setRegularNoInvent(reqularYearCount.getRegularNoInvent());
                                reqularYearCountMap.setRegularTotalCount(reqularYearCount.getRegularTotalCount());
                                reqularYearCountMap.setRegularWorthValue(reqularYearCount.getRegularWorthValue());
                                map.put(deptIdAndCompanyCode,reqularYearCountMap);
                            }
                        }
                        for(ReqularAndSpecialYearCount specialYearCount: specialYearCountList){
                            if(deptIdAndCompanyCode.equals(specialYearCount.getDeptId()+"-"+specialYearCount.getCompanyCode())){
                                ReqularAndSpecialYearCount specialYearCountMap = map.get(deptIdAndCompanyCode);
                                specialYearCountMap.setPId(specialYearCount.getPId());
                                specialYearCountMap.setInventYear(year+"");
                                specialYearCountMap.setCompanyCode(specialYearCount.getCompanyCode());
                                specialYearCountMap.setProfitGroupCode(specialYearCount.getProfitGroupCode());
                                specialYearCountMap.setDeptId(specialYearCount.getDeptId());
                                specialYearCountMap.setDeptName(specialYearCount.getDeptName());
                                specialYearCountMap.setRegionId(region.getId());
                                specialYearCountMap.setIsLeaf(specialYearCount.getIsLeaf());

                                specialYearCountMap.setSpecialInvent(specialYearCount.getSpecialInvent());
                                specialYearCountMap.setSpecialNoInvent(specialYearCount.getSpecialNoInvent());
                                specialYearCountMap.setSpecialTotalCount(specialYearCount.getSpecialTotalCount());
                                specialYearCountMap.setSpecialWorthValue(specialYearCount.getSpecialWorthValue());
                                map.put(deptIdAndCompanyCode,specialYearCountMap);

                            }
                        }
                    }
                    for(String key:map.keySet()){
                        log.info("map的key"+key);
                    }
                   for(ReqularAndSpecialYearCount reqularAndSpecialYearCount:map.values()){
                       reqularAndSpecialYearCount.setId(new ObjectId().toHexString());
                       reqularAndSpecialYearCountMapper.insertReqularAndSpecialYearCount(reqularAndSpecialYearCount);
                   }
                   //统计各地市
                    List<ReqularAndSpecialYearCount> ciytList = assetsInfoMapper.findCityReqularYearCount(year+"",region.getId());
                    for(ReqularAndSpecialYearCount reqularAndSpecialYearCount :ciytList){
                        reqularAndSpecialYearCount.setId(new ObjectId().toHexString());
                        reqularAndSpecialYearCount.setDeptId(region.getId());
                        reqularAndSpecialYearCount.setDeptName(region.getRegionName());
                        reqularAndSpecialYearCount.setPId("09");
                        reqularAndSpecialYearCount.setRegionId(region.getId());
                        reqularAndSpecialYearCountMapper.insertReqularAndSpecialYearCount(reqularAndSpecialYearCount);
                    }
                }

                // 统计总公司
                List<ReqularAndSpecialYearCount> headOfficeList = assetsInfoMapper.findHeadOfficeYearCount(year+"");
                for(ReqularAndSpecialYearCount reqularAndSpecialYearCount:headOfficeList){
                    reqularAndSpecialYearCount.setId(new ObjectId().toHexString());
                    reqularAndSpecialYearCount.setDeptId("09");
                    reqularAndSpecialYearCount.setDeptName("广东总公司");
                    reqularAndSpecialYearCount.setIsLeaf("0");
                    reqularAndSpecialYearCountMapper.insertReqularAndSpecialYearCount(reqularAndSpecialYearCount);
                }

            }
            log.info("跑完了");
            log.info("跑完了");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }
}
