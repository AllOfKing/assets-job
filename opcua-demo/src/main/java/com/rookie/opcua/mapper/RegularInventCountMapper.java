package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.AssetsInventCountDTO;
import com.rookie.opcua.dto.AssetsInventCountListDTO;
import com.rookie.opcua.dto.RegularInventCountDTO;
import com.rookie.opcua.entity.AssetsInventCount;
import com.rookie.opcua.entity.RegularInventCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegularInventCountMapper extends BaseMapper<RegularInventCount> {
    void createRegularInventCount(@Param("assetsType") String assetsType);

    void truncateRegularInventCount(@Param("assetsType") String assetsType);

    void truncateRegularInventCountSummary();

    int insertInventCountForSummary(List<RegularInventCount> list);

    int insertInventCount(RegularInventCountDTO regularInventCountDTO);



    List<AssetsInventCountDTO> findAssetsInventCountForSummary(@Param("sql") String sql);

}
