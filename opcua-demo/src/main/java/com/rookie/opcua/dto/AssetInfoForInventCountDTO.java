package com.rookie.opcua.dto;

import lombok.Data;

@Data
public class AssetInfoForInventCountDTO {
    private String companyCode;
    private String manageDeptCode;
    private String manageDeptName;
    private String organId;
    private Integer cardNum;
    private Integer notInvent;
    private Integer invent;
    private Integer appScanNum;
    private Integer appNotScanNum;
    private Integer pcNum;
    private Integer inerrantNum;
    private Integer wrongNum;
    private Integer lossesNum;
    private Integer addNum;
    private Integer worthValue;

}
