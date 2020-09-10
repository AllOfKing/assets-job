package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.RmAssetsLogListDTO;
import com.rookie.opcua.entity.RmAssetsLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RmAssetsLogMapper extends BaseMapper<RmAssetsLog> {
    void insertList(RmAssetsLogListDTO rmAssetsLogListDTO);
    void createSubTable(@Param("dayString") String dayString);
}
