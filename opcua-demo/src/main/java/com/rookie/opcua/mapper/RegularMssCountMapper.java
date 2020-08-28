package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.RegularMssCount;

import java.util.List;

public interface RegularMssCountMapper extends BaseMapper<RegularMssCount> {
    void truncateRegularMssCount();
    int insertRegularMssCountList(List<RegularMssCount> list);
}
