package com.rookie.opcua.dto;

import com.rookie.opcua.entity.AssetsInventCount;
import lombok.Data;

import java.util.List;

@Data
public class AssetsInventCountListDTO {
    private String assetsType;
    private List<AssetsInventCount> list;
}
