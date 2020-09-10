package com.rookie.opcua.controller;


import com.rookie.opcua.dto.ServerStatusDTO;
import com.rookie.opcua.entity.RmAssetNew;
import com.rookie.opcua.job.ImportAssetsFromHbaseJob;
import com.rookie.opcua.service.RmAssetIamService;
import com.rookie.opcua.service.RmAssetNewService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rmAsset")
@AllArgsConstructor
public class RmAssetNewController {
    private final RmAssetNewService RmAssetNewService;

    private final RmAssetIamService rmAssetIamService;


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

}
