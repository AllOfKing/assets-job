package com.rookie.opcua.job;

import com.rookie.opcua.dto.ProfitCenterDTO;
import com.rookie.opcua.dto.RmAssetNewListDTO;
import com.rookie.opcua.dto.RmAssetsLogListDTO;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.ObjectId;
import com.rookie.opcua.utils.hbase.job.HBasePool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ImportAssetsFromHbaseJob implements  ApplicationRunner{

    private static Connection hbaseConnection;

    @Autowired
    private  RmAssetNewMapper rmAssetNewMapper;
    @Autowired
    private DayMapper dayMapper;
    @Autowired
    private CompanyCodeMapper companyCodeMapper;
    @Autowired
    private AssetZCLBMapper assetZCLBMapper;
    @Autowired
    private ZYCBLBMapper zycblbMapper;
    @Autowired
    private JLDWMapper jldwMapper;
    @Autowired
    private MatterDeptmentMapper matterDeptmentMapper;
    @Autowired
    private UseDeptMentMapper useDeptMentMapper;
    @Autowired
    private AssetsAscMapper assetsAscMapper;
    @Autowired
    private AssetsNatureMapper assetsNatureMapper;
    @Autowired
    private AddReasonMapper addReasonMapper;
    @Autowired
    private BoroughCompanyMapper boroughCompanyMapper;
    @Autowired
    private BranchMapper branchMapper;
    @Autowired
    private BusinessHallMapper businessHallMapper;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private DepreciaeRangeMapper depreciaeRangeMapper;
    @Autowired
    private DepreciaeCodeMapper depreciaeCodeMapper;
    @Autowired
    private PositionCodeMapper positionCodeMapper;
    @Autowired
    private HBCostCenterMapper hbCostCenterMapper;
    @Autowired
    private ProfitCenterMapper profitCenterMapper;
    @Autowired
    private AssetsInfoMapper assetsInfoMapper;
    @Autowired
    private HbaseAssetNewLogMapper hbaseAssetNewLogMapper;
    @Autowired
    private RmAssetsLogMapper rmAssetsLogMapper;
    @Autowired
    private HbaseRunTImeLogMapper hbaseRunTImeLogMapper;

    //    @Async
//    @Scheduled(cron = "0 0 1 * * ?")
    public void importAssetsFromHabse() {
        log.info("进入hbase数据获取");
        //操作记录
        boolean bln = true;
        while (true){
            HbaseAssetNewLog hbaseAssetNewLog = new HbaseAssetNewLog();
            try {
                log.info("定時任務跑起來了呀");
                HBasePool pool = HBasePool.getInstance();
                hbaseConnection = pool.getConnection();
                String tableLogName = "RM_ASSET_44_LOG";
                //获取表对象
                HTable logTable = getTable(tableLogName);
                //初始化Scan实例
                Scan scan = new Scan();
                //根据设置的时间去获取数据
                Day day = dayMapper.findDay();
                // 创建分表
                SimpleDateFormat sf = new SimpleDateFormat("MMdd");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
                String dayStriing = sf.format(sf2.parse(day.getDay()));
                rmAssetNewMapper.createSubTable(sf.format(sf2.parse(day.getDay())));
                rmAssetsLogMapper.createSubTable(sf.format(sf2.parse(day.getDay())));
                //将开始时间与当前时间作比对，当前时间推后一个小时
                boolean timeBln = timeComparison(day.getDay());
                if(timeBln == false){//如果等于false则证明跑时间已经快跑到了当前时间则跳出当前循环
                    Thread.sleep(30000);
                    continue;
                }
                log.info("获取的时间是+++++++"+day.getDay());
                //设值过滤条件
                FilterList filterList=new FilterList();
                Filter startFilter = new SingleColumnValueFilter(
                        Bytes.toBytes("LOG"),
                        Bytes.toBytes("uptime"),
                        CompareFilter.CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(day.getDay())

                );

                filterList.addFilter(startFilter);
                // 计算结束时间 开始时间后的一个小时
                Filter endFilter = new SingleColumnValueFilter(
                        Bytes.toBytes("LOG"),
                        Bytes.toBytes("uptime"),
                        CompareFilter.CompareOp.LESS,
                        Bytes.toBytes(getEndTime(day.getDay()))
                );
                //将本次结束时间保存进数据库，以便下次取来用作开始时间
                dayMapper.updateTime(getEndTime(day.getDay()));
                HbaseRunTImeLog hbaseRunTImeLog = new HbaseRunTImeLog();
                hbaseRunTImeLog.setId(new ObjectId().toHexString());
                hbaseRunTImeLog.setLastRunTime(getEndTime(day.getDay()));
                hbaseRunTImeLog.setRunCycle("1小时");
                hbaseRunTImeLogMapper.insert(hbaseRunTImeLog);
                filterList.addFilter(endFilter);
                //将过滤条件加入进去
                scan.setFilter(filterList);
                //返回结果
                ResultScanner rs = logTable.getScanner(scan);
                int n =0;
                for (Result[] results = rs.next(1000); results != null && results.length>0;
                     results = rs.next(1000)) {
                    n += results.length;
                    //获取到n条数据
                    HTable table = (HTable) hbaseConnection.getTable(TableName.valueOf("RM_ASSET_44"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    List<RmAssetNew> assetList = new ArrayList<RmAssetNew>();

                    List<RmAssetsLog> logList = new ArrayList<>();
                    for (Result result : results) {
                        String rowKey="";
                        String operation = "";
                        String uptime = "";
                        String busiTableName = "";
                        String keyColumn = "";
                        String id = "";
                        RmAssetsLog rmAssetsLog = new RmAssetsLog();
                        for (Cell cell : result.rawCells()) {
                            String logName = new String(CellUtil.cloneQualifier(cell));//列名
                            String logValue = new String(CellUtil.cloneValue(cell));//列值
                            if ("rowKey".equals(logName)) {
                                rowKey=logValue;
                            } else if ("operation".equals(logName)) {
                                operation=logValue;
                            } else if ("uptime".equals(logName)) {
                                uptime = logValue;
                            }else if("busiTableName".equals(logName)){
                                busiTableName = logValue;
                            }else if("keyColumn".equals(logName)){
                                keyColumn = logValue;
                            }else if("id".equals(logName)){
                                id = logValue;
                            }

                            if(StringUtils.isBlank(id)){
                                id = new ObjectId().toHexString();
                            }
                            rmAssetsLog.setId(id);
                            rmAssetsLog.setRowKey(rowKey);
                            rmAssetsLog.setOperation(operation);
                            rmAssetsLog.setUptime(uptime);
                            rmAssetsLog.setBusiTableName(busiTableName);
                            rmAssetsLog.setKeyColumn(keyColumn);
                        }
                        logList.add(rmAssetsLog);
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
                            hbaseAssetNewLog.setPullTime(parse);
                            rmNew.setUpdateTime(parse);
                            rmNew.setOperation(operation);
                            if (StringUtils.isNotBlank(rmNew.getId())) {
                                assetList.add(rmNew);
                            }
                        }
                    }
                    if(logList.size()>0){
                        RmAssetsLogListDTO rmAssetsLogListDTO = new RmAssetsLogListDTO();
                        rmAssetsLogListDTO.setDayString(sf.format(sf2.parse(day.getDay())));
                        rmAssetsLogListDTO.setList(logList);
                        rmAssetsLogMapper.insertList(rmAssetsLogListDTO);
                    }
                    if(assetList.size()>0){
                        RmAssetNewListDTO rmAssetNewListDTO = new RmAssetNewListDTO();
                        rmAssetNewListDTO.setDataString(sf.format(sf2.parse(day.getDay())));
                        rmAssetNewListDTO.setList(assetList);
                        rmAssetNewMapper.insertRmAssetsList(rmAssetNewListDTO);
                    }
                }
                log.info("数量"+n);
                hbaseAssetNewLog.setPullCount(n+"");
                hbaseAssetNewLog.setCreateTime(new Date());
                hbaseAssetNewLog.setId(new ObjectId().toHexString());
                hbaseAssetNewLogMapper.insertHbaseLog(hbaseAssetNewLog);
                log.info("接口数据获取完毕");
                //获取完所有数据之后，开始数据转换
                processingData(dayStriing);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

    private static HTable getTable(String tableName) throws IOException {
        return (HTable) hbaseConnection.getTable(TableName.valueOf(tableName));
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

    private void processingData(String dayString){
        log.info("开始跑任务");
        try {
            //查询总条数
            int totalCount = rmAssetNewMapper.findRmAssetByCount(dayString);
            if(totalCount >0){// 如果有数据的话
                List<CompanyCode> cpList = companyCodeMapper.selectList(null);//公司代码
                List<AssetZCLB> amList = assetZCLBMapper.selectList(null);//资产配别
                List<ZYCBLB> zyList = zycblbMapper.selectList(null);//作业成本类别
                List<JLDW> jldwList = jldwMapper.selectList(null);//计量单位
                List<MatterDeptment> swglbmList = matterDeptmentMapper.selectList(null);// 实物管理部门
                List<UseDeptment> sybmList = useDeptMentMapper.selectList(null); // 使用部门
                List<AssetsAsc> aaList = assetsAscMapper.selectList(null);// 资产归属
                List<AssetsNature> anList =assetsNatureMapper.selectList(null); // 资产性质
                List<AddReason> addRList = addReasonMapper.selectList(null);// 增加原因
                List<BoroughCompany> bcList = boroughCompanyMapper.selectList(null);// 区县分公司
                List<Branch> bList = branchMapper.selectList(null); // 支局清单
                List<BusinessHall> bhList = businessHallMapper.selectList(null); // 营业厅
                List<Station> stList = stationMapper.selectList(null); //基站
                List<Team> tList = teamMapper.selectList(null); // 班组
                List<DepreciaeRange> drList = depreciaeRangeMapper.selectList(null); // 折旧范围
                List<DepreciaeCode> dcList = depreciaeCodeMapper.selectList(null); // 折旧码
                List<PositionCode> pcList = positionCodeMapper.selectList(null);  // 科目定位码
                List<HBCostCenter> cbzxList = hbCostCenterMapper.selectList(null); // 成本中心
                List<ProfitCenterDTO> pList = profitCenterMapper.findProfitCenter();


                for(int i =0;i < totalCount; i +=500){ //每500条数据循环一次
                    List<RmAssetNew> list = rmAssetNewMapper.findAssetNewByPage(i,500,dayString);
                    for(RmAssetNew ra :list){ //循环处理数据
                        AssetsInfo ai = new AssetsInfo();
                        // 20200614 添加字段
                        ai.setPovertyAlleviat(ra.getZzpfzc());
                        ai.setLicense(ra.getZzLicense());
                        ai.setLicenseMethod(ra.getZzFz07());
                        ai.setDueDate(ra.getZzLdqrq());
                        ai.setSoftwareManageDepartCode(ra.getZzRjjsglbm());
                        ai.setSoftwareMaintDepartCode(ra.getZzRjjswhbm());
                        ai.setStatus(ra.getStatus());
                        //  -------添加字段-end-----------------

                        // 2020-4-23 资产状态处理
                        switch (ra.getStatus()) {
                            case "5":
                                ra.setStatus("0");
                                break;
                            case "6":
                                ra.setStatus("0");
                                break;
                        }
                        ai.setId(ra.getId());
                        ai.setSpecId(ra.getSpecId());
                        ai.setVersion(ra.getVersion());
                        ai.setSharingId(ra.getShardingId());
                        for (CompanyCode cp : cpList) { // 公司代码
                            if (ra.getBukrs().equals(cp.getTid())) {
                                ai.setCompanyCode(cp.getOrganizeid());
                                ai.setCompanyName(cp.getOrganizeName());
                                break;
                            }
                        }
                        ai.setOsszseq(ra.getOsszseq());
                        ai.setCardCode(ra.getAssetscardcode());
                        ai.setCardSecondCode(ra.getSecondaryassetscardcode());
                        if ("100383".equals(ra.getIsestimate())) {// 是  getIsestimate是否暂估
                            ai.setIsStopEstimate("1");
                        } else if ("100382".equals(ra.getIsestimate())) {// 否
                            ai.setIsStopEstimate("0");
                        }
                        ai.setAssetsCatalog(ra.getAssetCatalogue());
                        for (AssetZCLB zclb : amList) { //资产类别
                            if (ra.getAssetstype().equals(zclb.getTid())) {
                                // 对于无形资产指向到软件类
                                if ("20".equals(zclb.getCode())) {
                                    ai.setAssetsType("软件");
                                    ai.setAssetsType15("00002001");
                                } else {
                                    ai.setAssetsType(zclb.getName());
                                    ai.setAssetsType15(zclb.getCode());
                                }
                                break;
                            }
                        }
                        // 对于资产分类无法在盘点系统中找到的归类到其他
                        if (StringUtils.isBlank(ai.getAssetsType15())) { // 资产分类 15类
                            ai.setAssetsType("其他");
                            ai.setAssetsType15("00002999");
                        }
                        ai.setAssetsName(ra.getDescription());
                        ai.setDescription(ra.getDescription());
                        for (ZYCBLB zy : zyList) { // 作业成本类别
                            if (ra.getWorkcosttype().equals(zy.getTid())) {
                                ai.setCostType(zy.getZZCLB_DEC());
                                break;
                            }
                        }
                        for (JLDW jldw : jldwList) { // 计量单位
                            if (ra.getNamberunit().equals(jldw.getTid())) {
                                ai.setNumberName(jldw.getCHUNIT());
                                break;
                            }
                        }
                        ai.setNumberA(ra.getPNUMBER());
                        ai.setManufacturer(ra.getManufacturer());
                        ai.setSpecProgram(ra.getStandard().replace(",", "，"));
                        ai.setAddress(ra.getAddress().replace("\\", "/"));
                        for (MatterDeptment swglbm : swglbmList) { // 实物管理部门
                            if (ra.getManagedepartment().equals(swglbm.getTid())) {
                                ai.setManageDeptCode(swglbm.getGLDEPTCODE());
                                ai.setManageDeptName(swglbm.getGLDEPTNAME());
                                break;
                            }
                        }
                        for (UseDeptment sybm : sybmList) { // 使用部门
                            if (ra.getUsedepartment().equals(sybm.getTid())) {
                                ai.setUseDeptCode(sybm.getSYDEPTCODE());
                                ai.setUseDeptName(sybm.getSYDEPTNAME());
                                break;
                            }
                        }
                        ai.setLiablePenson(ra.getSupervisor());
                        if (StringUtils.isNotBlank(ra.getAssetcustodianAccount())) { //判断资产管理员账号
                            // 如果是中文姓名
                            if (hasChineseByReg(ra.getAssetcustodian())) {
                                ai.setAssetsMamagePerson(ra.getAssetcustodianAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetcustodianAccount() : ra
                                        .getAssetcustodianAccount() + "@GD");
                                ai.setAssetsMamageName(ra.getAssetcustodian());
                            } else {
                                ai.setAssetsMamagePerson(ra.getAssetcustodian());
                                ai.setAssetsMamageName(ra.getAssetcustodianAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetcustodianAccount() : ra
                                        .getAssetcustodianAccount() + "@GD");
                            }
                        }
                        if (StringUtils.isNotBlank(ra.getAssetkeeperAccount())) { // 判断保管员账号
                            // 如果是中文姓名
                            if (hasChineseByReg(ra.getAssetkeeper())) {
                                ai.setAssetsKeepPerson(ra.getAssetkeeperAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetkeeperAccount() : ra
                                        .getAssetkeeperAccount() + "@GD");
                                ai.setAssetsKeepName(ra.getAssetkeeper());
                            } else {
                                ai.setAssetsKeepPerson(ra.getAssetkeeper());
                                ai.setAssetsKeepName(ra.getAssetkeeperAccount()
                                        .indexOf("@GD") > 0 ? ra
                                        .getAssetkeeperAccount() : ra
                                        .getAssetkeeperAccount() + "@GD");
                            }
                        }
                        ai.setCapitalDate(ra.getCapitaldate());
                        ai.setWbs(ra.getWbs());
                        for (AssetsAsc aa : aaList) { // 资产归属
                            if (ra.getAssetsrelegation().equals(aa.getTid())) {
                                ai.setAssetsAscri(aa.getOrdtx());
                                break;
                            }
                        }
                        // 是否是客户端资产
                        if ("100383".equals(ra.getIsclientasset())) {// 是
                            ai.setIsClientAssets("1");
                        } else if ("100382".equals(ra.getIsclientasset())) {// 否
                            ai.setIsClientAssets("0");
                        }
                        ai.setClientName(ra.getClientname());
                        for (AssetsNature an : anList) {
                            if (ra.getAssetsnature().equals(an.getTid())) {
                                ai.setAssetsNature(an.getOrdtx());
                                break;
                            }
                        }
                        ai.setEnginRemarks(ra.getNotes());
                        ai.setAbcClass(ra.getAbctype());
                        for (AddReason add : addRList) { // 增加原因
                            if (ra.getAddtionreason().equals(add.getTid())) {
                                ai.setAddtionReason(add.getZjtext());
                                break;
                            }
                        }
                        ai.setSubAttachment(ra.getAccessory());
                        ai.setPgyz(ra.getPgyz());
                        ai.setPgljzj(ra.getPgljzj());
                        ai.setPgljjz(ra.getPgljjz());
                        //是否持有待售资产
                        if ("100383".equals(ra.getIsheldforsale())) {// 是
                            ai.setIsStaySale("1");
                        } else if ("100382".equals(ra.getIsheldforsale())) {// 否
                            ai.setIsStaySale("0");
                        }
                        // 是否逾龄资产
                        if ("100383".equals(ra.getIsoverage())) {// 是
                            ai.setIsOverdue("1");
                        } else if ("100382".equals(ra.getIsoverage())) {// 否
                            ai.setIsOverdue("0");
                        }
                        // 是否待报废
                        if ("100383".equals(ra.getIsforsrcap())) {// 是
                            ai.setIsForSrcap("1");
                        } else if ("100382".equals(ra.getIsforsrcap())) {// 否
                            ai.setIsForSrcap("0");
                        }
                        // 是否闲置资产
                        if ("100383".equals(ra.getIsidle())) {// 是
                            ai.setIsIdle("1");
                        } else if ("100382".equals(ra.getIsidle())) {// 否
                            ai.setIsIdle("0");
                        }
                        // 是否研发产生资产
                        if ("100383".equals(ra.getIsdevelopasset())) {// 是
                            ai.setIsDevelopasset("1");
                        } else if ("100382".equals(ra.getIsdevelopasset())) {// 否
                            ai.setIsDevelopasset("0");
                        }
                        ai.setAreaCode(ra.getAreacode());
                        for (BoroughCompany bc : bcList) { //区县分公司
                            if (ra.getCountyoffices().equals(bc.getTid())) {
                                ai.setCountyOffices(bc.getDfName());
                                break;
                            }
                        }
                        for (Branch br : bList) { // 支局清单
                            if (ra.getBranch().equals(br.getTid())) {
                                ai.setBranch(br.getDfName());
                                break;
                            }
                        }
                        for (BusinessHall bh : bhList) { //营业厅
                            if (ra.getBusinessofficeid().equals(bh.getTid())) {
                                ai.setBusinessOfficesId(bh.getDfName());
                                break;
                            }
                        }
                        for (Station st : stList) { // 基站
                            if (ra.getBtscode().equals(st.getTid())) {
                                ai.setStationName(st.getStationName());
                                break;
                            }
                        }
                        for (Team t : tList) { // 班组
                            if (ra.getTeam().equals(t.getTid())) {
                                ai.setTeam(t.getZz_banzt());
                                break;
                            }
                        }
                        // 是否扩容资产
                        if ("100383".equals(ra.getIsexpansion())) {// 是
                            ai.setIsExpansion("1");
                        } else if ("100382".equals(ra.getIsexpansion())) {// 否
                            ai.setIsExpansion("0");
                        }
                        // 是否减持资产
                        if ("100383".equals(ra.getIsimpairment())) {// 是
                            ai.setIsImpairment("1");
                        } else if ("100382".equals(ra.getIsimpairment())) {// 否
                            ai.setIsImpairment("0");
                        }
                        for (DepreciaeRange dr : drList) { // 折旧范围
                            if (ra.getDepreciaerange1().equals(dr.getTid())) {
                                ai.setDepreciaeRange1(dr.getAreaUsageIND());
                                break;
                            }
                        }
                        // 折旧码
                        for (DepreciaeCode dc : dcList) {
                            if (ra.getDepreciaecode1().equals(dc.getTid())) {
                                ai.setDepreciaeCode1(dc.getAfasl());
                                break;
                            }
                        }
                        // 折旧范围
                        for (DepreciaeRange dr : drList) {
                            if (ra.getDepreciaerange2().equals(dr.getTid())) {
                                ai.setDepreciaeRange2(dr.getAreaUsageIND());
                                break;
                            }
                        }
                        // 折旧码
                        for (DepreciaeCode dc : dcList) {
                            if (ra.getDepreciaecode2().equals(dc.getTid())) {
                                ai.setDepreciaeCode2(dc.getAfasl());
                                break;
                            }
                        }
                        ai.setDurableYears(ra.getUsefullife1());
                        ai.setPeriod(ra.getPeriod1());
                        ai.setDurableYears2(ra.getUsefullife2());
                        ai.setPeriod2(ra.getPeriod2());
                        ai.setOriginalValue(ra.getCostvalue());
                        ai.setAccumDeprec(ra.getDepreciation());
                        ai.setDeprecValue(ra.getImpairmentsum());
                        ai.setWorthValue(ra.getNetvalue());
                        for (PositionCode pc : pcList) { // 科目定位码
                            if (ra.getPositioncode().equals(pc.getTid())) {
                                ai.setPositionCode(pc.getKtogr());
                                break;
                            }
                        }
                        ai.setUnactiveDate(ra.getInactivedate());
                        ai.setPurchasedDate(ra.getPurchasedate());
                        ai.setPurchasedYear(ra.getPurchaseyear());
                        ai.setFirstBilling(ra.getFirstbilling());
                        // 是否停用
                        if ("100383".equals(ra.getIsdisable())) {// 是
                            ai.setIsDisable("1");
                        } else if ("100382".equals(ra.getIsdisable())) {// 否
                            ai.setIsDisable("0");
                        }
                        ai.setOriginalCode(ra.getOriginalasset());
                        // 是否出租
                        if ("100383".equals(ra.getIsrent())) {// 是
                            ai.setIsRent("1");
                        } else if ("100382".equals(ra.getIsrent())) {// 否
                            ai.setIsRent("0");
                        }
                        // 是否融资租入
                        if ("100383".equals(ra.getIslease())) {// 是
                            ai.setIsLease("1");
                        } else if ("100382".equals(ra.getIslease())) {// 否
                            ai.setIsLease("0");
                        }
                        ai.setCardEntials(ra.getCredentials());
                        ai.setDepreciaeStatu(ra.getDemolitionstatu());
                        ai.setUsefulmonths(ra.getUsefulmonths());
                        ai.setSourceCode(ra.getSourceassetno());
                        // 是否父卡
                        if ("100383".equals(ra.getIsparent())) {// 是
                            ai.setIsParent("1");
                        } else if ("100382".equals(ra.getIsparent())) {// 否
                            ai.setIsParent("0");
                        }
                        ai.setDurableLife(ra.getDurablelife());
                        ai.setParentAssetsCode(ra.getParentAssetId());
                        String prctr = "";
                        // 成本中心
                        for (HBCostCenter costCenter : cbzxList) {
                            if (ra.getCostcenter().equals(costCenter.getTid())) {
                                ai.setCostCode(costCenter.getCOSTCENTER());
                                ai.setCostName(costCenter.getCOSTCENTERREMARK());
                                prctr = costCenter.getPRCTR();
                                break;
                            }
                        }
                        if (StringUtils.isNotBlank(ai.getCostCode())) { // 成本中心编号
                            if (StringUtils.isNotBlank(prctr)) { // 判断利润中心是否为空
                                String city = "";
                                for (ProfitCenterDTO profitCenterDTO : pList) {
                                    if (prctr.equals(profitCenterDTO.getProfitCode())) { // 如果对象中的利润中心编码有这条数据
                                        String rId = profitCenterDTO.getRId(); // 则把regin_id 赋给rid
                                        if (StringUtils.isNotBlank(rId)) {
                                            city = rId;
                                        } else { // 如果rid为空，则把地市编码赋给city
                                            city = profitCenterDTO.getCityCode();
                                        }
                                        break;
                                    }
                                }
                                if (StringUtils.isNotBlank(city)) { // 如果city有值
                                    //如果资产分类或者原值为空或者为零则存入临时表
                                    if (ai.getAssetsType15().equals("19") // 领购
                                            || ai.getAssetsType15().equals("60") //投资性房产
                                            || ai.getAssetsType15().equals("30") //长摊资产
                                            || ai.getAssetsType15().equals("40")// 待摊资产
                                            || StringUtils.isBlank(ai
                                            .getOriginalValue()) // 原值
                                            || Double
                                            .parseDouble(ai.getOriginalValue()) <= 0) {
                                        //根据companyCode和cardCord查询一个AssetsInfolist
                                        List<AssetsInfo> aiList =assetsInfoMapper.findAssetsInfoTmepByComPanyCodeAndCardCode(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                        ai.setAssetsStatus("0".equals(ra.getStatus()) ? "0":"1");
                                        if (aiList != null && aiList.size() > 0) { // 如果aiList不为空,则证明数据库有这条数据，则修改
                                            AssetsInfo aiOld = aiList.get(0);
                                            ai.setCity("tmp_"+city);
                                            ai.setId(aiOld.getId());
                                            ai.setCurrentNodeId(aiOld.getCurrentNodeId());
                                            ai.setIsOutside(aiOld.getIsOutside());
                                            ai.setIsSubmitted(aiOld.getIsSubmitted());
                                            assetsInfoMapper.updateAssetsInfo(ai);
                                        }else{ // 如果没有，则新增
                                            ai.setCity("tmp_"+city);
                                            ai.setId(new ObjectId().toHexString());
                                            assetsInfoMapper.insertAssetsInfo(ai);
                                        }
                                    }else {
                                        ai.setCity(city);
                                        // 2020-4-23 加了两个状态码，如果资产状态是0，直接把资产置为无效
                                        if ("D".equals(ra.getOperation()) // 操作类型
                                                || "0".equals(ra.getStatus())) {
                                            assetsInfoMapper.updateAssetsStatusAndLastUpdateTime(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                        }else{
                                            List<AssetsInfo> aiList =  assetsInfoMapper.findAssetsInfoByComPanyCodeAndCardCode(ai.getCompanyCode(),ai.getCardCode(),ai.getCity());
                                            ai.setAssetsStatus("0".equals(ra.getStatus()) ? "0":"1");
                                            if (aiList != null && aiList.size() > 0) {
                                                AssetsInfo aiOld = aiList.get(0);
                                                String newId = ai.getId();
                                                ai.setId(aiOld.getId());
                                                ai.setCurrentNodeId(aiOld.getCurrentNodeId());
                                                ai.setIsOutside(aiOld.getIsOutside());
                                                ai.setIsSubmitted(aiOld.getIsSubmitted());
                                                ai.setCheckTime(aiOld.getCheckTime());
                                                ai.setCheckStatus(aiOld.getCheckStatus());
                                                ai.setCheckResult(aiOld.getCheckResult());
                                                ai.setUserName(aiOld.getUserName());
                                                List<Map<String,Object>> raeList = assetsInfoMapper.findAssetEntityNewByAssetId(newId);
                                                if(raeList.size()>0){
                                                    ai.setRelationId(MapUtils.getString(raeList.get(0),"relationID"));
                                                    ai.setRelationIdMark("1");
                                                }
                                                aiOld.setCity(city);
                                                assetsInfoMapper.updateAssetsInfo(ai);
                                            }else{
                                                List<Map<String,Object>> raeList = assetsInfoMapper.findAssetEntityNewByAssetId(ai.getId());
                                                if (raeList.size() > 0) {
                                                    ai.setRelationId(MapUtils
                                                            .getString(raeList.get(0),
                                                                    "relationID")
                                                            .substring(1));
                                                    ai.setRelationIdMark("1");
                                                }
                                                ai.setCity(city);
                                                assetsInfoMapper.insertAssetsInfo(ai);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        rmAssetNewMapper.deleteById(ra.getId(),dayString);
                    }
                }
            }
            log.info("跑完了");
            log.info("跑完了");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 是否包含汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘'”、！￥等符号）<br>
     *
     * @param str
     * @return
     */
    public static boolean hasChineseByReg(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str).find();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        importAssetsFromHabse();
    }

    public String getEndTime( String startTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            Date date = format.parse(startTime);
            format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            return format.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    public boolean timeComparison(String startTime){
        boolean bln = true;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");
            Date date = sf.parse(startTime);
            Date nowDate = new Date();
            String nowDateString = sf.format(dateRoll(nowDate,Calendar.HOUR,-1));
            System.out.println(nowDateString);
            Date date2 = sf.parse(nowDateString);
            System.out.println(date.getTime());
            System.out.println(date2.getTime());
            if(date.getTime()>=date2.getTime()){
                bln = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  bln;
    }
    public Date dateRoll(Date date, int i, int d) {
        // 获取Calendar对象并以传进来的时间为准
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将现在的时间滚动固定时长,转换为Date类型赋值
        calendar.add(i, d);
        // 转换为Date类型再赋值
        date = calendar.getTime();
        return date;
    }


}
