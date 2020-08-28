package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.dto.AssetInfoForInventCountDTO;
import com.rookie.opcua.dto.AssetsInfoCountMssDTO;
import com.rookie.opcua.dto.AssetsInfoUpdateDTO;
import com.rookie.opcua.dto.ReqularAndSpecialYearCountDTO;
import com.rookie.opcua.entity.AssetsInfo;
import com.rookie.opcua.entity.ReqularAndSpecialYearCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AssetsInfoMapper extends BaseMapper<AssetsInfo> {
     List<AssetsInfo> findAssetsInfoTmepByComPanyCodeAndCardCode(@Param("companyCode") String companyCode, @Param("cardCode") String cardCode,@Param("city")String city);

     List<AssetsInfo> findAssetsInfoByComPanyCodeAndCardCode(@Param("companyCode") String companyCode, @Param("cardCode") String cardCode,@Param("city")String city);

     List<Map<String,String>> findAssetsInfoRepeatCardCode(@Param("city") String city);

     List<AssetsInfo> findAssetsInfoByCompanyCodeAndCardCodeOrderByCreateTime(@Param("city") String city,@Param("companyCode") String companyCode,@Param("cardCode") String cardCode);

     AssetsInfo findAssetsInfoById(AssetsInfo assetsInfo);

     void updateRepeatAssets(@Param("city") String city,@Param("updateId") String updateId,@Param("oldId") String oldId,@Param("assetsStatus") String assetsStatus);

     void updateAsstesForNew(AssetsInfoUpdateDTO assetsInfoUpdateDTO);

     int updateAssetsInfo(AssetsInfo assetsInfo);

     int insertAssetsInfo(AssetsInfo assetsInfo);

     int insertAssetsInfoList(List<AssetsInfo> assetsInfos);

     int updateAssetsStatusAndLastUpdateTime(@Param("companyCode")String companyCode,@Param("cardCode") String cardCode,@Param("city") String city);

     List<Map<String , Object>>  findAssetEntityNewByAssetId(@Param("assetId") String assetId);

     List<AssetInfoForInventCountDTO> assetsInfoDataSummanry(@Param("batchId") String batchId, @Param("regionId") String regionId, @Param("assetsType") String assetsType);

     List<AssetInfoForInventCountDTO> regularInfoDataSummanry(@Param("regionId") String regionId, @Param("assetsType") String assetsType);

     void createAssetsInfoTmp(@Param("regionId") String regionId);

     List<AssetsInfoCountMssDTO> findCountMssHandleByOrgan(@Param("regionId") String regionId);


     // 获取常态化的常态化与专项盘点统计表数据
     List<ReqularAndSpecialYearCount> findReqularYearCount(@Param("year") String year, @Param("regionId") String regionId);

     //获取专项的常态化与专项盘点统计表数据
     List<ReqularAndSpecialYearCount> findSpecialYearCount(@Param("regionId") String regionId,@Param("batchId") String batchId);
     // 查询地市资产
     List<ReqularAndSpecialYearCount> findCityReqularYearCount(@Param("year") String year,@Param("city") String city);
     //总资产
     List<ReqularAndSpecialYearCount> findHeadOfficeYearCount(@Param("year") String year);

}
