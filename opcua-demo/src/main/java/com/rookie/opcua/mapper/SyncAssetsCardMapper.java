package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.SyncAssetsCard;
import org.apache.ibatis.annotations.Param;

public interface SyncAssetsCardMapper extends BaseMapper<SyncAssetsCard> {
    void craeteSynAssetsCard(@Param("regionId") String regionId);
}
