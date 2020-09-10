package com.rookie.opcua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.opcua.dto.RmAssetNewListDTO;
import com.rookie.opcua.entity.RmAssetNew;
import com.rookie.opcua.mapper.RmAssetNewMapper;
import com.rookie.opcua.service.RmAssetNewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RmAssetNewServiceImpl extends ServiceImpl<RmAssetNewMapper, RmAssetNew> implements RmAssetNewService {
    private final RmAssetNewMapper RmAssetNewMapper;

    @Override
    public RmAssetNew selectOne(String id) {

        return RmAssetNewMapper.findById(id);
    }

    @Override
    public void insert() {
        List<RmAssetNew> list = new ArrayList<>();
        for(int i=0;i<1000;i++){
            RmAssetNew rmAssetNew = new RmAssetNew();
            rmAssetNew.setId("1");
            list.add(rmAssetNew);
        }
        RmAssetNewListDTO rmAssetNewListDTO = new RmAssetNewListDTO();
        rmAssetNewListDTO.setDataString("0903");
        rmAssetNewListDTO.setList(list);
        RmAssetNewMapper.insertRmAssetsList(rmAssetNewListDTO);
    }
}
