package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.RmAssetNewListDTO;
import com.rookie.opcua.dto.Test;
import com.rookie.opcua.entity.RmAssetNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RmAssetNewMapper  extends BaseMapper<RmAssetNew> {

    RmAssetNew findById(@Param("id") String id);

    int findRmAssetByCount();

    void insertRmAssetsList(RmAssetNewListDTO rmAssetNewListDTO);

    void truncateRmAssets();

    List<RmAssetNew> findAssetNewByPage(int start,int size);

    void createSubTable(@Param("dateString") String dateString);

    void deleteById(@Param("id") String id);

    List<String> findAssetNewRepeat(@Param("city") String city);

    List<String> findBycardCode(@Param("cardCode") String cardCode,@Param("city") String city);
}
