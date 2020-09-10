package com.rookie.opcua.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.InventBachMapper;
import com.rookie.opcua.mapper.ProfitAndLossCountMapper;
import com.rookie.opcua.service.ProfitAndLossCountService;
import com.rookie.opcua.utils.UUIDUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋小金
 *
 */
@Service
@AllArgsConstructor
public class ProfitAndLossCountServiceImpl extends ServiceImpl<ProfitAndLossCountMapper, ProfitAndLossCount> implements ProfitAndLossCountService {

    @Autowired
    private ProfitAndLossCountMapper profitAndLossCountMapper;
    @Autowired
    private InventOrganServiceImpl inventOrganService;
    @Autowired
    private InventBachMapper inventBachMapper;


    private List<InventOrgan> childList = new ArrayList<>();

    /**
     * 统计常态化盈亏
     */
    @Override
    public void statisticsLossCount(String year) {

        //查询所有组织
        QueryWrapper<InventOrgan> wrapper = new QueryWrapper<>();
        wrapper.eq("IS_VALID", "1");
        List<InventOrgan> inventOrganList = inventOrganService.list(wrapper);
        //筛选地市
        List<InventOrgan> regionInventOrganList = new ArrayList<>();
        inventOrganList.forEach(data ->{
            if (null != data.getParentId()) {
                if (data.getParentId().equals("09")) {
                    regionInventOrganList.add(data);
                }
            }

        });
        //查询每个地市的盈亏
        List<ProfitAndLossCount> profitAndLossCountList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(regionInventOrganList)) {
            for (InventOrgan inventOrgan : regionInventOrganList) {
                try {
                    List<ProfitAndLossCount> profitAndLossCounts = new ArrayList<>();
                    if (StringUtils.isEmpty(year)) {
                        profitAndLossCounts = profitAndLossCountMapper.selectLossCount(inventOrgan.getId());
                    } else {
                        profitAndLossCounts = profitAndLossCountMapper.selectLossCountByYear(inventOrgan.getId(),year);
                    }
                    profitAndLossCountList.addAll(profitAndLossCounts);
                } catch (Exception e) {
                    continue;
                }
            }
            chirdDepProfit(profitAndLossCountList, regionInventOrganList, "",year);

        }

    }

    /**
     * 统计专项
     *
     * @param year
     */
    @Override
    public void statisticsBatchLossCount(String year) {
        //获取批次
        List<InventBatch> inventBatches = inventBachMapper.selectBatch(year);
        //查询所有组织
        QueryWrapper<InventOrgan> wrapper = new QueryWrapper<>();
        wrapper.eq("IS_VALID", "1");
        List<InventOrgan> inventOrganList = inventOrganService.list(wrapper);
        //
        List<ProfitAndLossCount> profitAndLossCountList = new ArrayList<>();

        //
        if (CollectionUtils.isNotEmpty(inventBatches)) {
            for (InventBatch inventBatch : inventBatches) {
                try {
                    List<ProfitAndLossCount> profitAndLossCounts = profitAndLossCountMapper.selectLossCount(inventBatch.getId());
                    chirdDepProfit(profitAndLossCounts, inventOrganList, inventBatch.getId(), year);
                } catch (Exception e) {
                    continue;
                }

            }
        }
    }

    /**
     * 聚合子部门数据
     * @param profitAndLossCountList
     * @param regionInventOrganList
     */
    public void chirdDepProfit(List<ProfitAndLossCount> profitAndLossCountList, List<InventOrgan> regionInventOrganList, String batchId,String year) {
        //聚合子部门的数据
        for (ProfitAndLossCount profitAndLossCount : profitAndLossCountList) {
            //设置id
            profitAndLossCount.setId(UUIDUtils.getUUID());
            profitAndLossCount.setBatchId(StringUtils.isEmpty(batchId) ? "0" : batchId);
            //格式化数据保留两位小数
            profitAndLossCount.setLoseMoneyNetWorth(new BigDecimal(profitAndLossCount.getPreAgeNetWorth()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            profitAndLossCount.setPreAgeNetWorth( new BigDecimal(profitAndLossCount.getPreAgeNetWorth()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            profitAndLossCount.setScrappedWorth( new BigDecimal(profitAndLossCount.getScrappedWorth()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            //子部门集合
            List<InventOrgan> chirds = treeMenuList(regionInventOrganList, profitAndLossCount.getDeptId());
            //是否有子部门
            if (CollectionUtils.isNotEmpty(chirds) && chirds.size() > 1) {

                Integer loseMoneyCount = 0;
                Integer preAgeCount = 0;
                Integer scrappedCount = 0;
                Double loseMoneyNetWorth = 0.0;
                Double preAgeNetWorth = 0.0;
                Double scrappedWorth = 0.0;
                for (InventOrgan organ : chirds) {
                    for (ProfitAndLossCount pro : profitAndLossCountList) {
                        if (pro.getDeptId().equals(organ.getId())) {
                            loseMoneyCount += pro.getLoseMoneyCount();
                            preAgeCount += pro.getPreAgeCount();
                            scrappedCount += pro.getScrappedCount();
                            loseMoneyNetWorth += pro.getLoseMoneyNetWorth();
                            preAgeNetWorth += pro.getPreAgeNetWorth();
                            scrappedWorth += pro.getScrappedWorth();
                        }
                    }
                }
                profitAndLossCount.setLoseMoneyCount(loseMoneyCount);
                profitAndLossCount.setPreAgeCount(preAgeCount);
                profitAndLossCount.setScrappedCount(scrappedCount);
                profitAndLossCount.setLoseMoneyNetWorth(new BigDecimal(loseMoneyNetWorth).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                profitAndLossCount.setPreAgeNetWorth( new BigDecimal(preAgeNetWorth).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                profitAndLossCount.setScrappedWorth( new BigDecimal(scrappedWorth).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        //删除数据
        if (!StringUtils.isEmpty(year) && CollectionUtils.isNotEmpty(profitAndLossCountList)) {
            profitAndLossCountMapper.deleteBatch(profitAndLossCountList);
        }

        //新增数据
        profitAndLossCountMapper.batchInsert(profitAndLossCountList);
    }

    /**
     * 遍历子部门
     * @param menuList
     * @param pid
     * @return
     */
    public List<InventOrgan> treeMenuList(List<InventOrgan> menuList, String pid){
        for(InventOrgan mu: menuList){
            //遍历出父id等于参数的id，add进子节点集合
            if (null == mu.getId()) {
                continue;
            }
            if(mu.getParentId().equals(pid)){
                //递归遍历下一级 deptId
                treeMenuList(menuList,mu.getId());
                childList.add(mu);
            }
        }
        return childList;
    }
}
