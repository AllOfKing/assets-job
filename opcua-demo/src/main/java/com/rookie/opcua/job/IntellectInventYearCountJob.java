package com.rookie.opcua.job;

import com.rookie.opcua.entity.IntellectInventYearCount;
import com.rookie.opcua.entity.InventProject;
import com.rookie.opcua.entity.Region;
import com.rookie.opcua.mapper.AssetsInfoMapper;
import com.rookie.opcua.mapper.IntellectInventYearCountMapper;
import com.rookie.opcua.mapper.InventProjectMapper;
import com.rookie.opcua.mapper.RegionMapper;
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
    private AssetsInfoMapper assetsInfoMapper;

    @Autowired
    private InventProjectMapper inventProjectMapper;

    public void run(){
        log.info("IntellectInventYearCountJob启动");
        //先清空数据表
//        intellectInventYearCountMapper.truncate();
        //先查询部门
        List<Region> regionList = regionMapper.findRegionByParentId("09");
        // 获取年份
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int years[] = {currentYear-1,currentYear};
        //根据部门和年份去统计所有资产卡片的卡片数量，已盘点，净值
        //先去查询常态化
        //遍历所有地市去查询
        for(int year :years){// 循环当前年和前一年
            for (Region region : regionList) {
                //根据地市编号去查询分表
                //根据年度区分
                //获取常态化
//                List<IntellectInventYearCount> regularList = assetsInfoMapper.findIntellectInventYearCountForReqular(year+"",region.getId());
//                List<IntellectInventYearCount> list = new ArrayList<IntellectInventYearCount>();
//                for(IntellectInventYearCount intellectInventYearCount:regularList){
//                    intellectInventYearCount.setId(new ObjectId().toHexString());
//                    list.add(intellectInventYearCount);
//                }
//
//                if(list.size()>0){
//                    intellectInventYearCountMapper.insertIntellectInventYearCountList(list);
//                }
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
            }
            List<IntellectInventYearCount> listAll = intellectInventYearCountMapper.findIntellectInventYearCountAll(year+"");
            for(IntellectInventYearCount intellectInventYearCount:listAll){
                intellectInventYearCount = removeDecimal(intellectInventYearCount);
                intellectInventYearCount.setId(new ObjectId().toHexString());
                intellectInventYearCount.setDeptId("09");
                intellectInventYearCount.setDeptName("广东公司");
                intellectInventYearCount.setPId("");
                intellectInventYearCountMapper.insertIntellectInventYearCount(intellectInventYearCount);
            }
            log.info("跑完了");
            log.info("跑完了");
        }
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
