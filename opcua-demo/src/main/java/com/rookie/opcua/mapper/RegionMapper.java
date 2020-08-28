package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionMapper extends BaseMapper<Region> {
    List<Region> findRegionByParentId(@Param("parentId") String parentId);
}
