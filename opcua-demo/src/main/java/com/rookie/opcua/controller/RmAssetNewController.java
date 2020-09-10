package com.rookie.opcua.controller;


import com.rookie.opcua.dto.ServerStatusDTO;
import com.rookie.opcua.entity.RmAssetNew;
import com.rookie.opcua.job.ImportAssetsFromHbaseJob;
import com.rookie.opcua.service.ProfitAndLossCountService;
import com.rookie.opcua.service.RmAssetIamService;
import com.rookie.opcua.service.RmAssetNewService;
import com.rookie.opcua.service.impl.ProfitAndLossCountServiceImpl;
import com.rookie.opcua.utils.ObjectId;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rmAsset")
@AllArgsConstructor
public class RmAssetNewController {

    private final RmAssetNewService RmAssetNewService;

    private final RmAssetIamService rmAssetIamService;

    private final ProfitAndLossCountService profitAndLossCountService;


    @GetMapping("/assets/findById")
    public RmAssetNew findById(String id){
        return  RmAssetNewService.selectOne(id);
    }


    @GetMapping("/assets/insertList")
    public void insertList(){
        RmAssetNewService.insert();
    }

    @GetMapping("/insertIamList")
    public void insertRmAssetsIamList(){
        rmAssetIamService.insertRmAssetsIamList();
    };

    @GetMapping("/initLossCount")
    @ResponseBody
    public String statisticsLossCount(String year){
        profitAndLossCountService.statisticsLossCount(year);
        return "200";
    }

   @GetMapping("/initBatchLossCount")
    @ResponseBody
    public String statisticsBatchLossCount(String year){
        profitAndLossCountService.statisticsBatchLossCount(year);
        return "200";
    }

}
