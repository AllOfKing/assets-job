package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.ProfitCenterDTO;
import com.rookie.opcua.entity.ProfitCenter;

import java.util.List;

public interface ProfitCenterMapper extends BaseMapper<ProfitCenter> {
    List<ProfitCenterDTO> findProfitCenter();
}
