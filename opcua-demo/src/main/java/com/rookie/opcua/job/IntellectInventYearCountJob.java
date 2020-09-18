package com.rookie.opcua.job;

import com.rookie.opcua.entity.IntellectInventYearCount;
import com.rookie.opcua.entity.InventOrgan;
import com.rookie.opcua.entity.InventProject;
import com.rookie.opcua.entity.Region;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@Component
public class IntellectInventYearCountJob implements ApplicationRunner {

    @Autowired
    private IntellectInventYearCountMapper intellectInventYearCountMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private InventOrganMapper inventOrganMapper;

    @Autowired
    private AssetsInfoMapper assetsInfoMapper;

    @Autowired
    private InventProjectMapper inventProjectMapper;

    public void run(){
        log.info("IntellectInventYearCountJob启动");
        //先清空数据表
        intellectInventYearCountMapper.truncate();
        //先查询部门
        List<Region> regionList = regionMapper.findRegionByParentId("09");
        log.info("共有"+regionList.size()+"个地市");
        // 获取年份
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int years[] = {currentYear-1,currentYear};
        //根据部门和年份去统计所有资产卡片的卡片数量，已盘点，净值
        //先去查询常态化
        //遍历所有地市去查询
        for(int year :years){// 循环当前年和前一年
            for (Region region : regionList) { // 地市
                //根据地市编号去查询分表
                //根据年度区分
                //获取常态化
                log.info("开始跑"+region.getId()+"的数据");
                List<IntellectInventYearCount> regularList = assetsInfoMapper.findIntellectInventYearCountForReqular(year+"",region.getId());
                log.info(region.getId()+"共有"+regularList.size()+"条");
                List<InventOrgan> inventOrganList = inventOrganMapper.findInventOrganRegionByParentId(region.getId());

                List<IntellectInventYearCount> sonList = new ArrayList<IntellectInventYearCount>();

                String[] companyCodes = {"A019","B019","C019"};
                for(String companyCode:companyCodes){
                    for(InventOrgan inventOrgan :inventOrganList){
                        IntellectInventYearCount sonAllInventYearCount = new IntellectInventYearCount();

                        BigDecimal wgInventCount = new BigDecimal("0");
                        BigDecimal wgWorthValue = new BigDecimal("0");

                        BigDecimal xjInventCount = new BigDecimal("0");
                        BigDecimal xjWorthValue = new BigDecimal("0");

                        BigDecimal appInventCount = new BigDecimal("0");
                        BigDecimal appWorthValue = new BigDecimal("0");

                        BigDecimal appNoScanInventCount = new BigDecimal("0");
                        BigDecimal appNoScanWorthValue = new BigDecimal("0");

                        BigDecimal pcInventCount = new BigDecimal("0");
                        BigDecimal pcWorthValue = new BigDecimal("0");

                        BigDecimal inventCount = new BigDecimal("0");
                        BigDecimal totalCount = new BigDecimal("0");
                        for(int i=0;i<regularList.size();i++){
                            IntellectInventYearCount intellectInventYearCount = regularList.get(i);
                            if(intellectInventYearCount.getPId().equals(inventOrgan.getId())&&intellectInventYearCount.getCompanyCode().equals(companyCode)){
                                log.info("在"+companyCode+"下，"+intellectInventYearCount.getDeptId()+","+intellectInventYearCount.getDeptName()+"需要数据整合");
                                //子部门汇总
                                wgInventCount = wgInventCount.add(new BigDecimal(intellectInventYearCount.getWgInventCount()));
                                wgWorthValue = wgWorthValue.add(new BigDecimal(intellectInventYearCount.getWgWorthValue()));

                                xjInventCount = xjInventCount.add(new BigDecimal(intellectInventYearCount.getXjInventCount()));
                                xjWorthValue = xjWorthValue.add(new BigDecimal(intellectInventYearCount.getXjWorthValue()));

                                appInventCount = appInventCount.add(new BigDecimal(intellectInventYearCount.getAppInventCount()));
                                appWorthValue = appWorthValue.add(new BigDecimal(intellectInventYearCount.getAppWorthValue()));

                                appNoScanInventCount = appNoScanInventCount.add(new BigDecimal(intellectInventYearCount.getAppNoScanInventCount()));
                                appNoScanWorthValue = appNoScanWorthValue.add(new BigDecimal(intellectInventYearCount.getAppNoScanWorthValue()));

                                pcInventCount = pcInventCount.add(new BigDecimal(intellectInventYearCount.getPcInventCount()));
                                pcWorthValue = pcWorthValue.add(new BigDecimal(intellectInventYearCount.getPcWorthValue()));

                                inventCount = inventCount.add(new BigDecimal(intellectInventYearCount.getInventCount()));
                                totalCount = totalCount.add(new BigDecimal(intellectInventYearCount.getTotalCount()));

                            }
                        }
                        log.info(totalCount.toPlainString());
                        if(totalCount.intValue() > 0){ // 如果totcalCount有值大于零是所有统计的基础
                            log.info("开始了"+inventOrgan.getDepartmentName()+"部门的数据累加");
                            sonAllInventYearCount.setPId(region.getId());
                            sonAllInventYearCount.setDeptId(inventOrgan.getId());
                            sonAllInventYearCount.setDeptName(inventOrgan.getDescription());
                            sonAllInventYearCount.setCompanyCode(companyCode);
                            sonAllInventYearCount.setRegionId(region.getId());
                            sonAllInventYearCount.setIsLeaf("1");
                            sonAllInventYearCount.setInventYear(year+"");
                            sonAllInventYearCount.setWgInventCount(wgInventCount.toPlainString());
                            sonAllInventYearCount.setWgWorthValue(wgWorthValue.toPlainString());
                            sonAllInventYearCount.setXjInventCount(xjInventCount.toPlainString());
                            sonAllInventYearCount.setXjWorthValue(xjWorthValue.toPlainString());
                            sonAllInventYearCount.setAppInventCount(appInventCount.toPlainString());
                            sonAllInventYearCount.setAppWorthValue(appWorthValue.toPlainString());
                            sonAllInventYearCount.setAppNoScanInventCount(appNoScanInventCount.toPlainString());
                            sonAllInventYearCount.setAppNoScanWorthValue(appNoScanWorthValue.toPlainString());
                            sonAllInventYearCount.setPcInventCount(pcInventCount.toPlainString());
                            sonAllInventYearCount.setPcWorthValue(pcWorthValue.toPlainString());
                            sonAllInventYearCount.setInventCount(inventCount.toPlainString());
                            sonAllInventYearCount.setTotalCount(totalCount.toPlainString());
                            sonAllInventYearCount.setCreateTime(new Date());
                            for(IntellectInventYearCount intellectInventYearCount : regularList){
                                if(inventOrgan.getId().equals(intellectInventYearCount.getDeptId())&&companyCode.equals(intellectInventYearCount.getCompanyCode())){//将自己改为自己部门下的
                                    intellectInventYearCount.setPId(inventOrgan.getId());
                                    log.info("将"+inventOrgan.getDepartmentName()+"部门的上级id设为自己");
                                }
                            }
                            sonList.add(sonAllInventYearCount);
                        }
                    }
                }
                List<IntellectInventYearCount> list = new ArrayList<IntellectInventYearCount>();
                log.info("需要整合的数据有"+sonList.size()+"条");
                regularList.addAll(sonList);
                log.info("所有数据工具"+regularList.size()+"条");
                for(IntellectInventYearCount intellectInventYearCount:regularList){
                    intellectInventYearCount.setRegionId(region.getId());
                    intellectInventYearCount.setId(new ObjectId().toHexString());
                    list.add(intellectInventYearCount);
                }
                if(list.size()>0){
                    intellectInventYearCountMapper.insertIntellectInventYearCountList(list);
                }
                log.info("统计"+region.getId()+"的数据");
                //统计各地市
                List<IntellectInventYearCount> listForCity =intellectInventYearCountMapper.findIntellectInventYearCountByCity(region.getId(),year+"");
                //将数据汇总
                for(IntellectInventYearCount intellectInventYearCount :listForCity){
                    intellectInventYearCount = removeDecimal(intellectInventYearCount);
                    intellectInventYearCount.setId(new ObjectId().toHexString());
                    intellectInventYearCount.setDeptId(region.getId());
                    intellectInventYearCount.setDeptName(region.getRegionName());
                    intellectInventYearCount.setPId("09");
                    intellectInventYearCount.setRegionId(region.getId());
                    intellectInventYearCountMapper.insertIntellectInventYearCount(intellectInventYearCount);
                }
                log.info(region.getId()+"跑完了");
            }
            List<IntellectInventYearCount> listAll = intellectInventYearCountMapper.findIntellectInventYearCountAll(year+"");
            log.info("统计广东公司的数据");
            for(IntellectInventYearCount intellectInventYearCount:listAll){
                intellectInventYearCount = removeDecimal(intellectInventYearCount);
                intellectInventYearCount.setId(new ObjectId().toHexString());
                intellectInventYearCount.setDeptId("09");
                intellectInventYearCount.setDeptName("广东公司");
                intellectInventYearCount.setPId("");
                intellectInventYearCountMapper.insertIntellectInventYearCount(intellectInventYearCount);
            }

        }
        log.info("跑完了");
    }


    public IntellectInventYearCount removeDecimal(IntellectInventYearCount intellectInventYearCount){
        BigDecimal wgInventCount = new BigDecimal(intellectInventYearCount.getWgInventCount());
        intellectInventYearCount.setWgInventCount(wgInventCount.stripTrailingZeros().toPlainString());

        BigDecimal wgWorthValue = new BigDecimal(intellectInventYearCount.getWgWorthValue());
        intellectInventYearCount.setWgWorthValue(wgWorthValue.stripTrailingZeros().toPlainString());

        BigDecimal xjInventCount = new BigDecimal(intellectInventYearCount.getXjInventCount());
        intellectInventYearCount.setXjInventCount(xjInventCount.stripTrailingZeros().toPlainString());

        BigDecimal xjWorthValue = new BigDecimal(intellectInventYearCount.getXjWorthValue());
        intellectInventYearCount.setXjWorthValue(xjWorthValue.stripTrailingZeros().toPlainString());

        BigDecimal appInventCount = new BigDecimal(intellectInventYearCount.getAppInventCount());
        intellectInventYearCount.setAppInventCount(appInventCount.stripTrailingZeros().toPlainString());

        BigDecimal appWorthValue = new BigDecimal(intellectInventYearCount.getAppWorthValue());
        intellectInventYearCount.setAppWorthValue(appWorthValue.stripTrailingZeros().toPlainString());

        BigDecimal appNoScanInventCount = new BigDecimal(intellectInventYearCount.getAppNoScanInventCount());
        intellectInventYearCount.setAppNoScanInventCount(appNoScanInventCount.stripTrailingZeros().toPlainString());

        BigDecimal appNoScanWorthValue = new BigDecimal(intellectInventYearCount.getAppNoScanWorthValue());
        intellectInventYearCount.setAppNoScanWorthValue(appNoScanWorthValue.stripTrailingZeros().toPlainString());

        BigDecimal pcInventCount = new BigDecimal(intellectInventYearCount.getPcInventCount());
        intellectInventYearCount.setPcInventCount(pcInventCount.stripTrailingZeros().toPlainString());

        BigDecimal pcWorthValue = new BigDecimal(intellectInventYearCount.getPcWorthValue());
        intellectInventYearCount.setPcWorthValue(pcWorthValue.stripTrailingZeros().toPlainString());

        BigDecimal totalCount = new BigDecimal(intellectInventYearCount.getTotalCount());
        intellectInventYearCount.setTotalCount(totalCount.stripTrailingZeros().toPlainString());

        BigDecimal inventCount = new BigDecimal(intellectInventYearCount.getInventCount());
        intellectInventYearCount.setInventCount(inventCount.stripTrailingZeros().toPlainString());
        return  intellectInventYearCount;
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }
}
