package com.rookie.opcua.dto;

import com.rookie.opcua.entity.RmAssetsLog;
import lombok.Data;

import java.util.List;

@Data
public class RmAssetsLogListDTO {
    private String dayString;
    private List<RmAssetsLog> list;
}
