package com.rookie.opcua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.opcua.entity.RmAssetsIam;

public interface RmAssetIamService extends IService<RmAssetsIam> {

    void insertRmAssetsIamList();
}
