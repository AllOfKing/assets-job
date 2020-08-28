package com.rookie.opcua.job.Runner;

import com.rookie.opcua.entity.RmAssetNew;
import com.rookie.opcua.mapper.RmAssetNewMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ImportAssetsDataRunner  implements Runnable{
    private Result[] results;
    private RmAssetNewMapper rmAssetNewMapper;
    private static Connection hbaseConnection;
    private CountDownLatch begin;
    private CountDownLatch end;

    public ImportAssetsDataRunner(Result[] results, RmAssetNewMapper rmAssetNewMapper,
                                  Connection hbaseConnection,CountDownLatch begin,
                                  CountDownLatch end) {
        this.results = results;
        this.rmAssetNewMapper = rmAssetNewMapper;
        this.hbaseConnection = hbaseConnection;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            HTable table = (HTable) hbaseConnection.getTable(TableName.valueOf("RM_ASSET_44"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            List<RmAssetNew> assetList = new ArrayList<RmAssetNew>();
            for (Result result : results) {
                String rowKey="";
                String operation = "";
                String uptime = "";
                for (Cell cell : result.rawCells()) {
                    String logName = new String(CellUtil.cloneQualifier(cell));//列名
                    String logValue = new String(CellUtil.cloneValue(cell));//列值
                    if ("rowKey".equals(logName)) {
                        rowKey=logValue;
                    } else if ("operation".equals(logName)) {
                        operation=logValue;
                    } else if ("uptime".equals(logName)) {
                        uptime = logValue;
                    }
                }
                if (StringUtils.isNotBlank(rowKey)) {
                    Get get = new Get(Bytes.toBytes(rowKey));
                    Result ar = table.get(get);
                    Cell[] arCell = ar.rawCells();
                    RmAssetNew rmNew = new RmAssetNew();
                    for (Cell arc : arCell) {
                        String aName = new String(CellUtil.cloneQualifier(arc));
                        String aValue = new String(CellUtil.cloneValue(arc));
                        setValue(aName,aValue,rmNew);
                    }
                    Date parse = simpleDateFormat.parse(uptime);
                    rmNew.setUpdateTime(parse);
                    rmNew.setOperation(operation);
                    if (StringUtils.isNotBlank(rmNew.getId())) {
                        assetList.add(rmNew);
                    }
                }
            }
            if(assetList.size()>0){
                rmAssetNewMapper.insertRmAssetsList(assetList);
            }
            begin.await();
        }catch (Exception  e){
            e.printStackTrace();
        }finally {
            end.countDown();
        }
    }



    private void setValue(String name,String value,RmAssetNew rmAssetNew)  {
        try {
            if(name.equalsIgnoreCase("ID")){
                rmAssetNew.setId(value);
            }
            if(name.equalsIgnoreCase("SPEC_ID")){
                rmAssetNew.setSpecId(value);
            }
            if(name.equalsIgnoreCase("CREATOR_ID")){
                rmAssetNew.setCreatorId(value);
            }
            if(name.equalsIgnoreCase("CREATE_DATE")){
                rmAssetNew.setCreateDate(value);
            }
            if(name.equalsIgnoreCase("modifier_id")){
                rmAssetNew.setModifierId(value);
            }
            if(name.equalsIgnoreCase("modify_date")){
                rmAssetNew.setModifyDate(value);
            }
            if(name.equalsIgnoreCase("version")){
                rmAssetNew.setVersion(value);
            }
            if(name.equalsIgnoreCase("sharding_id")){
                rmAssetNew.setShardingId(value);
            }
            if(name.equalsIgnoreCase("bukrs")){
                rmAssetNew.setBukrs(value);
            }
            if(name.equalsIgnoreCase("osszseq")){
                rmAssetNew.setOsszseq(value);
            }
            if(name.equalsIgnoreCase("assetscardcode")){
                rmAssetNew.setAssetscardcode(value);
            }
            if(name.equalsIgnoreCase("secondaryassetscardcode")){
                rmAssetNew.setSecondaryassetscardcode(value);
            }
            if(name.equalsIgnoreCase("isestimate")){
                rmAssetNew.setIsestimate(value);
            }
            if(name.equalsIgnoreCase("asset_catalogue")){
                rmAssetNew.setAssetCatalogue(value);
            }
            if(name.equalsIgnoreCase("assetstype")){
                rmAssetNew.setAssetstype(value);
            }
            if(name.equalsIgnoreCase("description")){
                rmAssetNew.setDescription(value);
            }
            if(name.equalsIgnoreCase("workcosttype")){
                rmAssetNew.setWorkcosttype(value);
            }
            if(name.equalsIgnoreCase("namberunit")){
                rmAssetNew.setNamberunit(value);
            }
            if(name.equalsIgnoreCase("PNUMBER")){
                rmAssetNew.setPNUMBER(value);
            }
            if(name.equalsIgnoreCase("manufacturer")){
                rmAssetNew.setManufacturer(value);
            }
            if(name.equalsIgnoreCase("standard")){
                rmAssetNew.setStandard(value);
            }
            if(name.equalsIgnoreCase("address")){
                rmAssetNew.setAddress(value);
            }
            if(name.equalsIgnoreCase("managedepartment")){
                rmAssetNew.setManagedepartment(value);
            }
            if(name.equalsIgnoreCase("usedepartment")){
                rmAssetNew.setUsedepartment(value);
            }
            if(name.equalsIgnoreCase("costcenter")){
                rmAssetNew.setCostcenter(value);
            }
            if(name.equalsIgnoreCase("supervisor")){
                rmAssetNew.setSupervisor(value);
            }
            if(name.equalsIgnoreCase("assetcustodian")){
                rmAssetNew.setAssetcustodian(value);
            }
            if(name.equalsIgnoreCase("assetkeeper")){
                rmAssetNew.setAssetkeeper(value);
            }
            if(name.equalsIgnoreCase("assetcustodian_account")){
                rmAssetNew.setAssetcustodianAccount(value);
            }
            if(name.equalsIgnoreCase("assetkeeper_account")){
                rmAssetNew.setAssetkeeperAccount(value);
            }
            if(name.equalsIgnoreCase("capitaldate")){
                rmAssetNew.setCapitaldate(value);
            }
            if(name.equalsIgnoreCase("wbs")){
                rmAssetNew.setWbs(value);
            }
            if(name.equalsIgnoreCase("assetsrelegation")){
                rmAssetNew.setAssetsrelegation(value);
            }
            if(name.equalsIgnoreCase("isclientasset")){
                rmAssetNew.setIsclientasset(value);
            }
            if(name.equalsIgnoreCase("clientname")){
                rmAssetNew.setClientname(value);
            }
            if(name.equalsIgnoreCase("assetsnature")){
                rmAssetNew.setAssetsnature(value);
            }
            if(name.equalsIgnoreCase("notes")){
                rmAssetNew.setNotes(value);
            }
            if(name.equalsIgnoreCase("abctype")){
                rmAssetNew.setAbctype(value);
            }
            if(name.equalsIgnoreCase("parent_asset_id")){
                rmAssetNew.setParentAssetId(value);
            }
            if(name.equalsIgnoreCase("addtionreason")){
                rmAssetNew.setAddtionreason(value);
            }
            if(name.equalsIgnoreCase("accessory")){
                rmAssetNew.setAccessory(value);
            }
            if(name.equalsIgnoreCase("pgyz")){
                rmAssetNew.setPgyz(value);
            }
            if(name.equalsIgnoreCase("pgljzj")){
                rmAssetNew.setPgljzj(value);
            }
            if(name.equalsIgnoreCase("pgljjz")){
                rmAssetNew.setPgljjz(value);
            }
            if(name.equalsIgnoreCase("isheldforsale")){
                rmAssetNew.setIsheldforsale(value);
            }
            if(name.equalsIgnoreCase("isoverage")){
                rmAssetNew.setIsoverage(value);
            }
            if(name.equalsIgnoreCase("isforsrcap")){
                rmAssetNew.setIsforsrcap(value);
            }
            if(name.equalsIgnoreCase("isidle")){
                rmAssetNew.setIsidle(value);
            }
            if(name.equalsIgnoreCase("isdevelopasset")){
                rmAssetNew.setIsdevelopasset(value);
            }
            if(name.equalsIgnoreCase("areacode")){
                rmAssetNew.setAreacode(value);
            }
            if(name.equalsIgnoreCase("countyoffices")){
                rmAssetNew.setCountyoffices(value);
            }
            if(name.equalsIgnoreCase("branch")){
                rmAssetNew.setBranch(value);
            }
            if(name.equalsIgnoreCase("businessofficeid")){
                rmAssetNew.setBusinessofficeid(value);
            }
            if(name.equalsIgnoreCase("btscode")){
                rmAssetNew.setBtscode(value);
            }
            if(name.equalsIgnoreCase("team")){
                rmAssetNew.setTeam(value);
            }
            if(name.equalsIgnoreCase("team")){
                rmAssetNew.setTeam(value);
            }
            if(name.equalsIgnoreCase("isexpansion")){
                rmAssetNew.setIsexpansion(value);
            }
            if(name.equalsIgnoreCase("isimpairment")){
                rmAssetNew.setIsimpairment(value);
            }
            if(name.equalsIgnoreCase("depreciaerange1")){
                rmAssetNew.setDepreciaerange1(value);
            }
            if(name.equalsIgnoreCase("depreciaecode1")){
                rmAssetNew.setDepreciaecode1(value);
            }
            if(name.equalsIgnoreCase("depreciaerange2")){
                rmAssetNew.setDepreciaerange2(value);
            }
            if(name.equalsIgnoreCase("depreciaecode2")){
                rmAssetNew.setDepreciaecode2(value);
            }
            if(name.equalsIgnoreCase("usefullife1")){
                rmAssetNew.setUsefullife1(value);
            }
            if(name.equalsIgnoreCase("period1")){
                rmAssetNew.setPeriod1(value);
            }
            if(name.equalsIgnoreCase("usefullife2")){
                rmAssetNew.setUsefullife2(value);
            }
            if(name.equalsIgnoreCase("period2")){
                rmAssetNew.setPeriod2(value);
            }
            if(name.equalsIgnoreCase("costvalue")){
                rmAssetNew.setCostvalue(value);
            }
            if(name.equalsIgnoreCase("depreciation")){
                rmAssetNew.setDepreciation(value);
            }
            if(name.equalsIgnoreCase("impairmentsum")){
                rmAssetNew.setImpairmentsum(value);
            }
            if(name.equalsIgnoreCase("netvalue")){
                rmAssetNew.setNetvalue(value);
            }
            if(name.equalsIgnoreCase("positioncode")){
                rmAssetNew.setPositioncode(value);
            }
            if(name.equalsIgnoreCase("inactivedate")){
                rmAssetNew.setInactivedate(value);
            }
            if(name.equalsIgnoreCase("purchasedate")){
                rmAssetNew.setPurchasedate(value);
            }
            if(name.equalsIgnoreCase("purchaseyear")){
                rmAssetNew.setPurchaseyear(value);
            }
            if(name.equalsIgnoreCase("firstbilling")){
                rmAssetNew.setFirstbilling(value);
            }
            if(name.equalsIgnoreCase("isdisable")){
                rmAssetNew.setIsdisable(value);
            }
            if(name.equalsIgnoreCase("originalasset")){
                rmAssetNew.setOriginalasset(value);
            }
            if(name.equalsIgnoreCase("developasset")){
                rmAssetNew.setDevelopasset(value);
            }
            if(name.equalsIgnoreCase("isrent")){
                rmAssetNew.setIsrent(value);
            }
            if(name.equalsIgnoreCase("islease")){
                rmAssetNew.setIslease(value);
            }
            if(name.equalsIgnoreCase("credentials")){
                rmAssetNew.setCredentials(value);
            }
            if(name.equalsIgnoreCase("demolitionstatu")){
                rmAssetNew.setDemolitionstatu(value);
            }
            if(name.equalsIgnoreCase("wbselement")){
                rmAssetNew.setWbselement(value);
            }
            if(name.equalsIgnoreCase("usefulmonths")){
                rmAssetNew.setUsefulmonths(value);
            }
            if(name.equalsIgnoreCase("sourceassetno")){
                rmAssetNew.setSourceassetno(value);
            }
            if(name.equalsIgnoreCase("isparent")){
                rmAssetNew.setIsparent(value);
            }
            if(name.equalsIgnoreCase("durablelife")){
                rmAssetNew.setDurablelife(value);
            }
            if(name.equalsIgnoreCase("data_source_id")){
                rmAssetNew.setDataSourceId(value);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(name.equalsIgnoreCase("update_time")){
                rmAssetNew.setUpdateTime(sdf.parse(value));
            }
            if(name.equalsIgnoreCase("operation")){
                rmAssetNew.setOperation(value);
            }
            if(name.equalsIgnoreCase("status")){
                rmAssetNew.setStatus(value);
            }
            if(name.equalsIgnoreCase("zzfpzc")){
                rmAssetNew.setZzpfzc(value);
            }
            if(name.equalsIgnoreCase("zz_license")){
                rmAssetNew.setZzLicense(value);
            }
            if(name.equalsIgnoreCase("zz_fz07")){
                rmAssetNew.setZzFz07(value);
            }
            if(name.equalsIgnoreCase("zz_ldqrq")){
                rmAssetNew.setZzLdqrq(value);
            }
            if(name.equalsIgnoreCase("zz_rjjsglbm")){
                rmAssetNew.setZzRjjsglbm(value);
            }
            if(name.equalsIgnoreCase("zz_rjjswhbm")){
                rmAssetNew.setZzRjjswhbm(value);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
