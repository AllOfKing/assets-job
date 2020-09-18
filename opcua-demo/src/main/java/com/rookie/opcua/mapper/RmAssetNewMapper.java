package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.RmAssetNewListDTO;
import com.rookie.opcua.dto.Test;
import com.rookie.opcua.entity.RmAssetNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RmAssetNewMapper  extends BaseMapper<RmAssetNew> {

    RmAssetNew findById(@Param("id") String id);

    int findRmAssetByCount(@Param("dayString") String dayString);

    void insertRmAssetsList(RmAssetNewListDTO rmAssetNewListDTO);

    void truncateRmAssets();

    List<RmAssetNew> findAssetNewByPage(@Param("start") int start,@Param("size") int size,@Param("dayString") String dayString);

    void createSubTable(@Param("dateString") String dateString);

    void deleteById(@Param("id") String id,@Param("dayString") String dayString);

    List<String> findAssetNewRepeat(@Param("city") String city);

    List<String> findBycardCode(@Param("cardCode") String cardCode,@Param("city") String city);
}
