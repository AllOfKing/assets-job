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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋小金
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

    private List<ProfitAndLossCount> profitChildList = new ArrayList<>();

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
        inventOrganList.forEach(data -> {
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
                        profitAndLossCounts = profitAndLossCountMapper.selectLossCountByYear(inventOrgan.getId(), year);
                    }
                    profitAndLossCountList.addAll(profitAndLossCounts);
                } catch (Exception e) {
                    continue;
                }
            }
            chirdDepProfit(profitAndLossCountList, inventOrganList, "", year);
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
     *
     * @param profitAndLossCountList
     * @param regionInventOrganList
     */
    public void chirdDepProfit(List<ProfitAndLossCount> profitAndLossCountList, List<InventOrgan> regionInventOrganList, String batchId, String year) {
        List<ProfitAndLossCount> depProfitAndLossCount = new ArrayList<>();
//        for (InventOrgan inventOrgan : regionInventOrganList) {
//            List<InventOrgan> inventOrgans = new ArrayList<>();
//            //获取子节点
//            treeOrganList(regionInventOrganList,inventOrgans,inventOrgan.getId());
//            //查看子节点是否在统计数据中
//            ProfitAndLossCount profitAndLossCount = new ProfitAndLossCount();
//            profitAndLossCount.setId(UUIDUtils.getUUID());
//            Integer loseMoneyCount = 0;
//            Integer preAgeCount = 0;
//            Integer scrappedCount = 0;
//            Double loseMoneyNetWorth = 0d;
//            Double preAgeNetWorth = 0d;
//            Double scrappedWorth = 0d;
//
//            for (InventOrgan organ : inventOrgans) {
//                for (ProfitAndLossCount profitAndLossCount : profitAndLossCountList) {
//                    if (organ.getId().equals(profitAndLossCount.getDeptId())) {
//
//                    }
//                }
//            }
//
//            if (CollectionUtils.isNotEmpty(inventOrgans)) {
//                System.out.println("ffff");
//            }
//        }

        //聚合子部门的数据
        for (ProfitAndLossCount profitAndLossCount : profitAndLossCountList) {
            //设置id
            profitAndLossCount.setId(UUIDUtils.getUUID());
            profitAndLossCount.setBatchId(StringUtils.isEmpty(batchId) ? "0" : batchId);
            //格式化数据保留两位小数
            profitAndLossCount.setLoseMoneyNetWorth(format(profitAndLossCount.getLoseMoneyNetWorth()));
            profitAndLossCount.setPreAgeNetWorth(format(profitAndLossCount.getPreAgeNetWorth()));
            profitAndLossCount.setScrappedWorth(format(profitAndLossCount.getScrappedWorth()));

            //子部门集合
            List<InventOrgan> chirds = new ArrayList<>();
            treeOrganList(regionInventOrganList,chirds, profitAndLossCount.getDeptId());
            //是否有子部门
            if (CollectionUtils.isNotEmpty(chirds) && chirds.size() > 1) {
//
                Integer loseMoneyCount = profitAndLossCount.getLoseMoneyCount();
                Integer preAgeCount = profitAndLossCount.getPreAgeCount();
                Integer scrappedCount = profitAndLossCount.getScrappedCount();
                Double loseMoneyNetWorth = profitAndLossCount.getLoseMoneyNetWorth();
                Double preAgeNetWorth = profitAndLossCount.getPreAgeNetWorth();
                Double scrappedWorth = profitAndLossCount.getScrappedWorth();
                for (ProfitAndLossCount pro : profitAndLossCountList) {
                    for (InventOrgan organ : chirds) {
                        if (pro.getDeptId().equals(organ.getId()) && pro.getCompanyCode().equals(profitAndLossCount.getCompanyCode())) {
                            loseMoneyCount += pro.getLoseMoneyCount();
                            preAgeCount += pro.getPreAgeCount();
                            scrappedCount += pro.getScrappedCount();
                            loseMoneyNetWorth += pro.getLoseMoneyNetWorth();
                            preAgeNetWorth += pro.getPreAgeNetWorth();
                            scrappedWorth += pro.getScrappedWorth();
                            break;
                        }
                    }
                }
                profitAndLossCount.setLoseMoneyCount(loseMoneyCount);
                profitAndLossCount.setPreAgeCount(preAgeCount);
                profitAndLossCount.setScrappedCount(scrappedCount);
                profitAndLossCount.setLoseMoneyNetWorth(format(loseMoneyNetWorth));
                profitAndLossCount.setPreAgeNetWorth(format(preAgeNetWorth));
                profitAndLossCount.setScrappedWorth(format(scrappedWorth));
            }

        }

        //删除数据
        if (CollectionUtils.isNotEmpty(profitAndLossCountList)) {
            profitAndLossCountMapper.deleteBatch(profitAndLossCountList);
        }

        //新增数据
        profitAndLossCountMapper.batchInsert(profitAndLossCountList);
    }

    /**
     * 遍历子部门
     *
     * @param allList
     * @param pid
     * @return
     */
    public void treeOrganList(List<InventOrgan> allList,List<InventOrgan> returnList, String pid) {
        for (InventOrgan mu : allList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (null == mu.getParentId()) {
                continue;
            }
            if (mu.getParentId().equals(pid)) {
                //递归遍历下一级 deptId
                treeOrganList(allList, returnList,mu.getId());
                returnList.add(mu);
            }
        }
    }

    /**
     * 获取父id
     * @param id
     * @param allOrganList
     * @param organList
     */
    public void addParentOrgan(String id, List<InventOrgan> allOrganList, List<InventOrgan> organList) {
        for (InventOrgan organ : allOrganList) {
            if (id.equals(organ.getId())) {
                organList.add(organ);
                if (StringUtils.isEmpty(organ.getParentId())) {
                    return;
                }
                String pId = organ.getParentId();
                addParentOrgan(pId, allOrganList, organList);
            }
        }
    }

    /**
     * @param menuList
     * @param pid
     * @return
     */
    public List<ProfitAndLossCount> treeProfitList(List<ProfitAndLossCount> menuList, String pid) {
        for (ProfitAndLossCount mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (null == mu.getPId()) {
                continue;
            }
            if (mu.getPId().equals(pid)) {
                //递归遍历下一级 deptId
                treeProfitList(menuList, mu.getDeptId());
                profitChildList.add(mu);
            }
        }
        return profitChildList;
    }

    public Double format(double value) {

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(value));
    }
}
