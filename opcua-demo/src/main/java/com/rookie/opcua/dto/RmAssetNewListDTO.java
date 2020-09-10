package com.rookie.opcua.dto;

import com.rookie.opcua.entity.RmAssetNew;
import lombok.Data;

import java.util.List;

@Data
public class RmAssetNewListDTO {

    private String dataString;

    private List<RmAssetNew> list;
}
