package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.Day;

public interface DayMapper extends BaseMapper<Day> {
    Day findDay();
}
