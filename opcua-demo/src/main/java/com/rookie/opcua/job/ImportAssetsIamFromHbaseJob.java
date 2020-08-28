package com.rookie.opcua.job;

import com.rookie.opcua.entity.Day;
import com.rookie.opcua.entity.RmAssetsIam;
import com.rookie.opcua.mapper.DayMapper;
import com.rookie.opcua.mapper.RmAssetsIamMapper;
import com.rookie.opcua.utils.hbase.job.HBasePool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//@Component
@Slf4j
public class ImportAssetsIamFromHbaseJob {

    private static Connection hbaseConnection;

    @Autowired
    private RmAssetsIamMapper rmAssetsIamMapper;

    @Autowired
    private DayMapper DayMapper;

    public void importHbaseData() {
        //清空本地RM_ASSET_NEW
        try {
            rmAssetsIamMapper.truncateRmAssetsIam();

            //建立hbase链接，获取RM_ASSET_IAM_44_LOG表数据
            HBasePool pool = HBasePool.getInstance();
            hbaseConnection = pool.getConnection();
            String tableLogName = "RM_ASSET_IAM_44_LOG";
            HTable logTable = getTable(tableLogName);
            Scan scan = new Scan();
            //根据设置的时间去获取数据
            Day day = DayMapper.findDay();
            log.info("获取的时间是+++++++"+day.getDay());
            Integer dayInt = Integer.parseInt(day.getDay());
            SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
                    Bytes.toBytes("LOG"),
                    Bytes.toBytes("uptime"),
                    CompareFilter.CompareOp.EQUAL,
                    new SubstringComparator(getCountTime(dayInt))
            );
            String days = getCountTime(dayInt);
            scan.setFilter(filter1);
            ResultScanner rs = logTable.getScanner(scan);
            int n =0;
            for (Result[] results = rs.next(1000); results != null && results.length>0;
                 results = rs.next(1000)) {
                HTable table = (HTable) hbaseConnection.getTable(TableName.valueOf("RM_ASSET_IAM_44"));
                List<RmAssetsIam> assetList = new ArrayList<RmAssetsIam>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
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
                        RmAssetsIam rmIam = new RmAssetsIam();
                        for (Cell arc : arCell) {
                            String aName = new String(CellUtil.cloneQualifier(arc));
                            String aValue = new String(CellUtil.cloneValue(arc));
                            setValue(aName,aValue,rmIam);
                        }
                        Date parse = simpleDateFormat.parse(uptime);
                        rmIam.setUpdateTime(parse);
                        rmIam.setOperation(operation);
                        if (StringUtils.isNotBlank(rmIam.getId())) {
                            assetList.add(rmIam);
                        }
                    }
                }
                //批量插入
                rmAssetsIamMapper.insertRmAssetsIamList(assetList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private static HTable getTable(String tableName) throws IOException {
        return (HTable) hbaseConnection.getTable(TableName.valueOf(tableName));
    }

    private String getCountTime(int dayNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        Date today = new Date();
        theCa.setTime(today);
        theCa.add(theCa.DATE, dayNum);// 最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        String day = sdf.format(start);
        //day = "2019-04-26";
        log.info("获取日期："+day+"的数据");
        return day;
    }

    private void setValue(String name,String value,RmAssetsIam rmIam){
        try {
            if(name.equalsIgnoreCase("ID")){
                rmIam.setId(name);
            }
            if(name.equalsIgnoreCase("ASSET_ID")){
                rmIam.setASSET_ID(name);
            }
            if(name.equalsIgnoreCase("SPEC_ID")){
                rmIam.setSPEC_ID(name);
            }
            if(name.equalsIgnoreCase("SHARDING_ID")){
                rmIam.setSHARDING_ID(name);
            }
            if(name.equalsIgnoreCase("BUKRS")){
                rmIam.setBUKRS(name);
            }
            if(name.equalsIgnoreCase("OSSZSEQ")){
                rmIam.setOSSZSEQ(name);
            }
            if(name.equalsIgnoreCase("ASSETSCARDCODE")){
                rmIam.setASSETSCARDCODE(name);
            }
            if(name.equalsIgnoreCase("SECONDARYASSETSCARDCODE")){
                rmIam.setSECONDARYASSETSCARDCODE(name);
            }
            if(name.equalsIgnoreCase("ISESTIMATE")){
                rmIam.setISESTIMATE(name);
            }
            if(name.equalsIgnoreCase("ASSET_CATALOGUE")){
                rmIam.setASSET_CATALOGUE(name);
            }
            if(name.equalsIgnoreCase("ASSETSTYPE")){
                rmIam.setASSETSTYPE(name);
            }
            if(name.equalsIgnoreCase("DESCRIPTION")){
                rmIam.setDESCRIPTION(name);
            }
            if(name.equalsIgnoreCase("WORKCOSTTYPE")){
                rmIam.setWORKCOSTTYPE(name);
            }
            if(name.equalsIgnoreCase("NAMBERUNIT")){
                rmIam.setNAMBERUNIT(name);
            }
            if(name.equalsIgnoreCase("PNUMBER")){
                rmIam.setPNUMBER(name);
            }
            if(name.equalsIgnoreCase("MANUFACTURER")){
                rmIam.setMANUFACTURER(name);
            }
            if(name.equalsIgnoreCase("STANDARD")){
                rmIam.setSTANDARD(name);
            }
            if(name.equalsIgnoreCase("ADDRESS")){
                rmIam.setADDRESS(name);
            }
            if(name.equalsIgnoreCase("MANAGEDEPARTMENT")){
                rmIam.setMANAGEDEPARTMENT(name);
            }
            if(name.equalsIgnoreCase("USEDEPARTMENT")){
                rmIam.setUSEDEPARTMENT(name);
            }
            if(name.equalsIgnoreCase("COSTCENTER")){
                rmIam.setCOSTCENTER(name);
            }
            if(name.equalsIgnoreCase("SUPERVISOR")){
                rmIam.setSUPERVISOR(name);
            }
            if(name.equalsIgnoreCase("ASSETCUSTODIAN")){
                rmIam.setASSETCUSTODIAN(name);
            }
            if(name.equalsIgnoreCase("ASSETKEEPER")){
                rmIam.setASSETKEEPER(name);
            }
            if(name.equalsIgnoreCase("CAPITALDATE")){
                rmIam.setCAPITALDATE(name);
            }
            if(name.equalsIgnoreCase("WBS")){
                rmIam.setWBS(name);
            }
            if(name.equalsIgnoreCase("ASSETSRELEGATION")){
                rmIam.setASSETSRELEGATION(name);
            }
            if(name.equalsIgnoreCase("ISCLIENTASSET")){
                rmIam.setISCLIENTASSET(name);
            }
            if(name.equalsIgnoreCase("CLIENTNAME")){
                rmIam.setCLIENTNAME(name);
            }
            if(name.equalsIgnoreCase("ASSETSNATURE")){
                rmIam.setASSETSNATURE(name);
            }
            if(name.equalsIgnoreCase("NOTES")){
                rmIam.setNOTES(name);
            }
            if(name.equalsIgnoreCase("ABCTYPE")){
                rmIam.setABCTYPE(name);
            }
            if(name.equalsIgnoreCase("ADDTIONREASON")){
                rmIam.setADDTIONREASON(name);
            }
            if(name.equalsIgnoreCase("PARENT_ASSET_ID")){
                rmIam.setPARENT_ASSET_ID(name);
            }
            if(name.equalsIgnoreCase("ACCESSORY")){
                rmIam.setACCESSORY(name);
            }
            if(name.equalsIgnoreCase("PGYZ")){
                rmIam.setPGYZ(name);
            }
            if(name.equalsIgnoreCase("PGLJZJ")){
                rmIam.setPGLJZJ(name);
            }
            if(name.equalsIgnoreCase("PGLJJZ")){
                rmIam.setPGLJJZ(name);
            }
            if(name.equalsIgnoreCase("ISHELDFORSALE")){
                rmIam.setISHELDFORSALE(name);
            }
            if(name.equalsIgnoreCase("ISOVERAGE")){
                rmIam.setISOVERAGE(name);
            }
            if(name.equalsIgnoreCase("ISFORSRCAP")){
                rmIam.setISFORSRCAP(name);
            }
            if(name.equalsIgnoreCase("ISIDLE")){
                rmIam.setISIDLE(name);
            }
            if(name.equalsIgnoreCase("ISDEVELOPASSET")){
                rmIam.setISDEVELOPASSET(name);
            }
            if(name.equalsIgnoreCase("AREACODE")){
                rmIam.setAREACODE(name);
            }
            if(name.equalsIgnoreCase("COUNTYOFFICES")){
                rmIam.setCOUNTYOFFICES(name);
            }
            if(name.equalsIgnoreCase("BRANCH")){
                rmIam.setBRANCH(name);
            }
            if(name.equalsIgnoreCase("BUSINESSOFFICEID")){
                rmIam.setBUSINESSOFFICEID(name);
            }
            if(name.equalsIgnoreCase("BTSCODE")){
                rmIam.setBTSCODE(name);
            }
            if(name.equalsIgnoreCase("TEAM")){
                rmIam.setTEAM(name);
            }
            if(name.equalsIgnoreCase("ISEXPANSION")){
                rmIam.setISEXPANSION(name);
            }
            if(name.equalsIgnoreCase("ISIMPAIRMENT")){
                rmIam.setISIMPAIRMENT(name);
            }
            if(name.equalsIgnoreCase("DEPRECIAERANGE1")){
                rmIam.setDEPRECIAERANGE1(name);
            }
            if(name.equalsIgnoreCase("DEPRECIAECODE1")){
                rmIam.setDEPRECIAECODE1(name);
            }
            if(name.equalsIgnoreCase("DEPRECIAERANGE2")){
                rmIam.setDEPRECIAERANGE2(name);
            }
            if(name.equalsIgnoreCase("DEPRECIAECODE2")){
                rmIam.setDEPRECIAECODE2(name);
            }
            if(name.equalsIgnoreCase("USEFULLIFE1")){
                rmIam.setUSEFULLIFE1(name);
            }
            if(name.equalsIgnoreCase("PERIOD1")){
                rmIam.setPERIOD1(name);
            }
            if(name.equalsIgnoreCase("USEFULLIFE2")){
                rmIam.setUSEFULLIFE2(name);
            }
            if(name.equalsIgnoreCase("PERIOD2")){
                rmIam.setPERIOD2(name);
            }
            if(name.equalsIgnoreCase("COSTVALUE")){
                rmIam.setCOSTVALUE(name);
            }
            if(name.equalsIgnoreCase("DEPRECIATION")){
                rmIam.setDEPRECIATION(name);
            }
            if(name.equalsIgnoreCase("IMPAIRMENTSUM")){
                rmIam.setIMPAIRMENTSUM(name);
            }
            if(name.equalsIgnoreCase("NETVALUE")){
                rmIam.setNETVALUE(name);
            }
            if(name.equalsIgnoreCase("POSITIONCODE")){
                rmIam.setPOSITIONCODE(name);
            }
            if(name.equalsIgnoreCase("INACTIVEDATE")){
                rmIam.setINACTIVEDATE(name);
            }
            if(name.equalsIgnoreCase("PURCHASEDATE")){
                rmIam.setPURCHASEDATE(name);
            }
            if(name.equalsIgnoreCase("PURCHASEYEAR")){
                rmIam.setPURCHASEYEAR(name);
            }
            if(name.equalsIgnoreCase("FIRSTBILLING")){
                rmIam.setFIRSTBILLING(name);
            }
            if(name.equalsIgnoreCase("ISDISABLE")){
                rmIam.setISDISABLE(name);
            }
            if(name.equalsIgnoreCase("ORIGINALASSET")){
                rmIam.setORIGINALASSET(name);
            }
            if(name.equalsIgnoreCase("DEVELOPASSET")){
                rmIam.setDEVELOPASSET(name);
            }
            if(name.equalsIgnoreCase("ISRENT")){
                rmIam.setISRENT(name);
            }
            if(name.equalsIgnoreCase("ISLEASE")){
                rmIam.setISLEASE(name);
            }
            if(name.equalsIgnoreCase("CREDENTIALS")){
                rmIam.setCREDENTIALS(name);
            }
            if(name.equalsIgnoreCase("DEMOLITIONSTATU")){
                rmIam.setDEMOLITIONSTATU(name);
            }
            if(name.equalsIgnoreCase("WBSELEMENT")){
                rmIam.setWBSELEMENT(name);
            }
            if(name.equalsIgnoreCase("USEFULMONTHS")){
                rmIam.setUSEFULMONTHS(name);
            }
            if(name.equalsIgnoreCase("SOURCEASSETNO")){
                rmIam.setSOURCEASSETNO(name);
            }
            if(name.equalsIgnoreCase("ISPARENT")){
                rmIam.setISPARENT(name);
            }
            if(name.equalsIgnoreCase("DURABLELIFE")){
                rmIam.setDURABLELIFE(name);
            }
            if(name.equalsIgnoreCase("IAM_STATUS")){
                rmIam.setIAM_STATUS(name);
            }
            if(name.equalsIgnoreCase("IAM_DATE")){
                rmIam.setIAM_DATE(name);
            }
            if(name.equalsIgnoreCase("TIME_STAMP")){
                rmIam.setTIME_STAMP(name);
            }
            if(name.equalsIgnoreCase("BATCH_NO")){
                rmIam.setBATCH_NO(name);
            }
            if(name.equalsIgnoreCase("IS_ARTI_DIST")){
                rmIam.setIS_ARTI_DIST(name);
            }
            if(name.equalsIgnoreCase("GROUP_PROV_RULE")){
                rmIam.setGROUP_PROV_RULE(name);
            }
            if(name.equalsIgnoreCase("ASSETCUSTODIAN_ACCOUNT")){
                rmIam.setASSETCUSTODIAN_ACCOUNT(name);
            }
            if(name.equalsIgnoreCase("ASSETKEEPER_ACCOUNT")){
                rmIam.setASSETKEEPER_ACCOUNT(name);
            }
            if(name.equalsIgnoreCase("zzfpzc")){
                rmIam.setZzfpzc(name);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(name.equalsIgnoreCase("update_time")){
                rmIam.setUpdateTime(sdf.parse(value));
            }
            if(name.equalsIgnoreCase("operation")){
                rmIam.setOperation(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
