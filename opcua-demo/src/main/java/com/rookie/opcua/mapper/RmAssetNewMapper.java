package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.RmAssetNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RmAssetNewMapper  extends BaseMapper<RmAssetNew> {

    RmAssetNew findById(@Param("id") String id);

    int findRmAssetByCount();

    void insertRmAssetsList(@Param("list") List<RmAssetNew> list);

    void truncateRmAssets();

    List<RmAssetNew> findAssetNewByPage(int start,int size);
}
