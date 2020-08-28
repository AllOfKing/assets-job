package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.AssetsInventCountDTO;
import com.rookie.opcua.dto.AssetsInventCountListDTO;
import com.rookie.opcua.entity.AssetsInventCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssetsInventCountMapper extends BaseMapper<AssetsInventCount> {

    void createAssetsInventCount(@Param("assetsType") String assetsType);

    void truncateAssetsInventCount(@Param("assetsType") String assetsType);

    void truncateAssetsInventCountSummary();


    int insertInventCountForSummary(List<AssetsInventCount> list);

    int insertInventCount(@Param("dto") AssetsInventCountListDTO assetsInventCountListDTO);

    List<AssetsInventCountDTO> findAssetsInventCountForSummary(@Param("sql") String sql);
}
