package com.rookie.opcua.job.Runner;

import com.rookie.opcua.dto.ProfitCenterDTO;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.job.TestJob;
import com.rookie.opcua.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
//@Component
public class Test{
//public class Test implements ApplicationRunner{

//    @Autowired
//    private RmAssetNewMapper rmAssetNewMapper;
//    @Autowired
//    private DayMapper dayMapper;
//    @Autowired
//    private CompanyCodeMapper companyCodeMapper;
//    @Autowired
//    private AssetZCLBMapper assetZCLBMapper;
//    @Autowired
//    private ZYCBLBMapper zycblbMapper;
//    @Autowired
//    private JLDWMapper jldwMapper;
//    @Autowired
//    private MatterDeptmentMapper matterDeptmentMapper;
//    @Autowired
//    private UseDeptMentMapper useDeptMentMapper;
//    @Autowired
//    private AssetsAscMapper assetsAscMapper;
//    @Autowired
//    private AssetsNatureMapper assetsNatureMapper;
//    @Autowired
//    private AddReasonMapper addReasonMapper;
//    @Autowired
//    private BoroughCompanyMapper boroughCompanyMapper;
//    @Autowired
//    private BranchMapper branchMapper;
//    @Autowired
//    private BusinessHallMapper businessHallMapper;
//    @Autowired
//    private StationMapper stationMapper;
//    @Autowired
//    private TeamMapper teamMapper;
//    @Autowired
//    private DepreciaeRangeMapper depreciaeRangeMapper;
//    @Autowired
//    private DepreciaeCodeMapper depreciaeCodeMapper;
//    @Autowired
//    private PositionCodeMapper positionCodeMapper;
//    @Autowired
//    private HBCostCenterMapper hbCostCenterMapper;
//    @Autowired
//    private ProfitCenterMapper profitCenterMapper;
//    @Autowired
//    private AssetsInfoMapper assetsInfoMapper;
//    @Autowired
//    private HbaseAssetNewLogMapper hbaseAssetNewLogMapper;
//    @Autowired
//    private RmAssetsLogMapper rmAssetsLogMapper;
//    @Autowired
//    private HbaseRunTImeLogMapper hbaseRunTImeLogMapper;
//
//
////    @Async
////    @Scheduled(cron = "0 0 6 * * ?")
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<CompanyCode> cpList = companyCodeMapper.selectList(null);//公司代码
//        List<AssetZCLB> amList = assetZCLBMapper.selectList(null);//资产配别
//        List<ZYCBLB> zyList = zycblbMapper.selectList(null);//作业成本类别
//        List<JLDW> jldwList = jldwMapper.selectList(null);//计量单位
//        List<MatterDeptment> swglbmList = matterDeptmentMapper.selectList(null);// 实物管理部门
//        List<UseDeptment> sybmList = useDeptMentMapper.selectList(null); // 使用部门
//        List<AssetsAsc> aaList = assetsAscMapper.selectList(null);// 资产归属
//        List<AssetsNature> anList =assetsNatureMapper.selectList(null); // 资产性质
//        List<AddReason> addRList = addReasonMapper.selectList(null);// 增加原因
//        List<BoroughCompany> bcList = boroughCompanyMapper.selectList(null);// 区县分公司
//        List<Branch> bList = branchMapper.selectList(null); // 支局清单
//        List<BusinessHall> bhList = businessHallMapper.selectList(null); // 营业厅
//        List<Station> stList = stationMapper.selectList(null); //基站
//        List<Team> tList = teamMapper.selectList(null); // 班组
//        List<DepreciaeRange> drList = depreciaeRangeMapper.selectList(null); // 折旧范围
//        List<DepreciaeCode> dcList = depreciaeCodeMapper.selectList(null); // 折旧码
//        List<PositionCode> pcList = positionCodeMapper.selectList(null);  // 科目定位码
//        List<HBCostCenter> cbzxList = hbCostCenterMapper.selectList(null); // 成本中心
//        List<ProfitCenterDTO> pList = profitCenterMapper.findProfitCenter();
//
//
//
//
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        int count = 2000; //每条线程去处理1000条数据
//        int totalCount = rmAssetNewMapper.findRmAssetByCount();
//        int runSize = totalCount/2000;
//        if(runSize<1){
//            runSize = 1;
//        }
//        //创建两个计数器
//        CountDownLatch begin = new CountDownLatch(1);
//        CountDownLatch end = new CountDownLatch(runSize);
//        for(int i=0;i<runSize;i++){ //循环创建线程
////            if((i+1)==runSize){//如果是最后一个
////                int startIndex = (i*count);
////                int endIndex = totalCount-startIndex;
////            }else{
////                int startIndex = (i*count);
////                int endIndex = ((i+1)*count);
////            }
//
//            TestJob testJob = new TestJob(i*1000,cpList,amList,zyList,jldwList,swglbmList,sybmList,aaList,
//                    anList,addRList,bcList,bList,bhList,stList,tList,drList,dcList,pcList,cbzxList,pList,
//                    rmAssetNewMapper,dayMapper,assetsInfoMapper,hbaseAssetNewLogMapper,rmAssetsLogMapper,hbaseRunTImeLogMapper);
//            executorService.execute(testJob);
//        }
//        begin.countDown();
//        end.await();
//    }
//


}
