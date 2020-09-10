package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.IntellectInventYearCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntellectInventYearCountMapper extends BaseMapper<IntellectInventYearCount> {
    void truncate();

    List<IntellectInventYearCount> findIntellectInventYearCountByCity(@Param("city") String city, @Param("year") String year);

    List<IntellectInventYearCount> findIntellectInventYearCountAll(@Param("year") String year);

    void insertIntellectInventYearCount(IntellectInventYearCount intellectInventYearCount);

    void insertIntellectInventYearCountList(List<IntellectInventYearCount> list);
}
