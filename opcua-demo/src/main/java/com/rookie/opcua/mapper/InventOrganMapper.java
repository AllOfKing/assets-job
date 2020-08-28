package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.InventOrgan;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface InventOrganMapper extends BaseMapper<InventOrgan> {
    List<InventOrgan> findInventOrganListByReginId(@Param("regionId") String regionId);
}
