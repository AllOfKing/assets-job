package com.rookie.opcua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.opcua.entity.RmAssetsIam;
import com.rookie.opcua.mapper.ProfitCenterMapper;
import com.rookie.opcua.mapper.RmAssetsIamMapper;
import com.rookie.opcua.service.RmAssetIamService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RmAssetIamServiceImpl extends ServiceImpl<RmAssetsIamMapper, RmAssetsIam> implements RmAssetIamService {

    private RmAssetsIamMapper rmAssetsIamMapper;

    @Autowired
    private ProfitCenterMapper profitCenterMapper;

    @Override
    public void insertRmAssetsIamList() {
        profitCenterMapper.findProfitCenter();
    }
}
