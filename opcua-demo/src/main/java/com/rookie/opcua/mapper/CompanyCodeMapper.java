package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.CompanyCode;

import java.util.List;

public interface CompanyCodeMapper extends BaseMapper<CompanyCode> {

    List<CompanyCode> findList();
}
