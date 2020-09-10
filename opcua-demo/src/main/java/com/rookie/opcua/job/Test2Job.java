package com.rookie.opcua.job;

import com.rookie.opcua.dto.Test;
import com.rookie.opcua.mapper.RmAssetNewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component
public class Test2Job implements ApplicationRunner {

    @Autowired
    private RmAssetNewMapper rmAssetNewMapper;
    public void run(){
        String a[] = {"0904","0905","0906","0907"};
       for(String city:a){
           List<String> list = rmAssetNewMapper.findAssetNewRepeat(city);
           for(String cardCode :list){
               List<String> listTest = rmAssetNewMapper.findBycardCode(cardCode,city);
               if(listTest.size()>0){
                   String tId = listTest.get(0);
                   rmAssetNewMapper.deleteById(tId);
               }
           }
       }
       log.info("跑完了");
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }
}
