package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.InventProject;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface InventProjectMapper extends BaseMapper<InventProject> {
    List<InventProject> findBatchId(@Param("regionIds") String regionIds, @Param("projectName") String projectName);

    //根据年份获取项目下的批次id
    List<InventProject> findBatchIdByYear(@Param("regionId") String regionId,@Param("year") String year);
}
