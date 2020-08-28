package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.ReqularAndSpecialYearCount;

import java.util.List;

public interface ReqularAndSpecialYearCountMapper extends BaseMapper<ReqularAndSpecialYearCount> {

    void insertReqularAndSpecialYearCountByList(List<ReqularAndSpecialYearCount> list);

    void insertReqularAndSpecialYearCount(ReqularAndSpecialYearCount reqularAndSpecialYearCount);

    void truncate();
}
