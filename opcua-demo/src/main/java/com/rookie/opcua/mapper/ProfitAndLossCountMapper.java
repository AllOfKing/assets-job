package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.PositionCode;
import com.rookie.opcua.entity.ProfitAndLossCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 */
public interface ProfitAndLossCountMapper extends BaseMapper<ProfitAndLossCount> {
    /**
     * 查询一个地市的盈亏统计
     *
     * @param org
     * @return
     */
    List<ProfitAndLossCount> selectLossCount(@Param("org") String org);

    /**
     * 根据年份统计
     *
     * @param org
     * @param year
     * @return
     */
    List<ProfitAndLossCount> selectLossCountByYear(@Param("org") String org, @Param("year") String year);

    /**
     * 批量插入
     *
     * @param profitAndLossCount
     */
    void batchInsert(@Param("list") List<ProfitAndLossCount> profitAndLossCount);

    /**
     * 多条件批量删除
     *
     * @param profitAndLossCount
     */
    void deleteBatch(@Param("list") List<ProfitAndLossCount> profitAndLossCount);


}




