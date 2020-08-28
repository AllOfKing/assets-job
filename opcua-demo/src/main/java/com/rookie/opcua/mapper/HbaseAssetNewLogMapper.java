package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.HbaseAssetNewLog;

public interface HbaseAssetNewLogMapper extends BaseMapper<HbaseAssetNewLog> {
    void insertHbaseLog(HbaseAssetNewLog hbaseAssetNewLog);

}
