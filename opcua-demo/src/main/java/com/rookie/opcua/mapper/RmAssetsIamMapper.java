package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.RmAssetsIam;

import java.util.List;

public interface RmAssetsIamMapper extends BaseMapper<RmAssetsIam> {

    //清空本地RmAssetsIam表数据
    void truncateRmAssetsIam();

    void insertRmAssetsIamList(List<RmAssetsIam> list);
}
