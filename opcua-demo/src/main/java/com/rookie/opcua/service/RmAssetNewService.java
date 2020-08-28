package com.rookie.opcua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.opcua.entity.RmAssetNew;

public interface RmAssetNewService extends IService<RmAssetNew> {

     RmAssetNew selectOne(String id);

     void insert();
}
