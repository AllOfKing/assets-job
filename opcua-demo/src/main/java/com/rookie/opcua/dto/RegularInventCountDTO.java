package com.rookie.opcua.dto;

import com.rookie.opcua.entity.RegularInventCount;
import lombok.Data;

import java.util.List;

@Data
public class RegularInventCountDTO {

    private String assetsType;
    private List<RegularInventCount> list;
}
