package com.rookie.opcua.job;

import com.rookie.opcua.dto.AssetsInfoUpdateDTO;
import com.rookie.opcua.entity.AssetsInfo;
import com.rookie.opcua.mapper.AssetsInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UpdateAssetsInfoJob implements ApplicationRunner {
    private String[] assetsTypes ={"0900","0901","0902","0903","0904","0905","0906","0907","0908","0909"
    ,"0910","0911","0912","0913","0914","0915","0916","0917","0918","0919","0920","0921","0981","0982"
    ,"0983","0984","0985","0990","0993"};

    @Autowired
    private AssetsInfoMapper assetsInfoMapper;
    public void updateAssets(){
        log.info("修改重复");
        //根据地市id循环表
        for(String assesType:assetsTypes){
            //查询出该地市下所有重复的资产卡片编号
            List<Map<String,String>> mapList = assetsInfoMapper.findAssetsInfoRepeatCardCode(assesType);
            for(Map<String,String> map :mapList){
                String companyCode = map.get("companyCode");
                String cardCode = map.get("cardCode");
                List<AssetsInfo> list = assetsInfoMapper.findAssetsInfoByCompanyCodeAndCardCodeOrderByCreateTime(assesType,companyCode,cardCode);
                if(list.size() == 2){
                    AssetsInfo assetsInfoNew = list.get(0);
                    AssetsInfo assetsInfoOld = list.get(1);
                    AssetsInfoUpdateDTO assetsInfoUpdateDTO = new AssetsInfoUpdateDTO();
                    assetsInfoUpdateDTO.setId(assetsInfoNew.getId());
                    assetsInfoUpdateDTO.setOldId(assetsInfoOld.getId());
                    assetsInfoUpdateDTO.setCurrentNodeId(assetsInfoOld.getCurrentNodeId());//节点
                    assetsInfoUpdateDTO.setIsOutside(assetsInfoOld.getIsOutside());
                    assetsInfoUpdateDTO.setIsSubmitted(assetsInfoOld.getIsSubmitted());
                    assetsInfoUpdateDTO.setCheckTime(assetsInfoOld.getCheckTime());
                    assetsInfoUpdateDTO.setCheckStatus(assetsInfoOld.getCheckStatus());
                    assetsInfoUpdateDTO.setCheckResult(assetsInfoOld.getCheckResult());
                    assetsInfoUpdateDTO.setUserName(assetsInfoOld.getUserName());
                    assetsInfoUpdateDTO.setCity(assesType);
                    //给旧卡片设置一个暂时的id
                    String temporaryId = "100110120119";
                    String newId = assetsInfoNew.getId();
                    assetsInfoMapper.updateRepeatAssets(assesType,temporaryId,assetsInfoOld.getId(),"0");
                    //修改新卡片
                    assetsInfoMapper.updateAsstesForNew(assetsInfoUpdateDTO);
                    //把旧卡片id换回去
                    assetsInfoMapper.updateRepeatAssets(assesType,newId,temporaryId,"0");
                }
            }
        }
        log.info("修改完成");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        updateAssets();
    }
}
