package com.rookie.opcua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.opcua.entity.ProfitAndLossCount;
import com.rookie.opcua.entity.RmAssetsIam;

/**
 * @author admin
 */
public interface ProfitAndLossCountService  extends IService<ProfitAndLossCount> {
    /**
     * 统计盈亏数据
     * @param year
     */
    void statisticsLossCount(String year);

    /**
     * 统计专项盈亏
     * @param year
     */
    void statisticsBatchLossCount(String year);
}
