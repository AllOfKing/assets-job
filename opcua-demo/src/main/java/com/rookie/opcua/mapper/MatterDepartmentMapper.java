package com.rookie.opcua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.opcua.entity.MatterDepartment;

import java.util.List;

public interface MatterDepartmentMapper extends BaseMapper<MatterDepartment> {
    List<MatterDepartment> findMatterDepartmentBycityCode(String cityCode);
}
