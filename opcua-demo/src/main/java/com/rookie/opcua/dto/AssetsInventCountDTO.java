package com.rookie.opcua.dto;

import lombok.Data;

@Data
public class AssetsInventCountDTO {
    private String companyCode;
    private String materDeptCode;
    private String materDeptName;
    private String deptId;
    private String deptName;
    private String pId;
    private String regionId;
    private String isLeaf;
    private String cardNum;
    private String notInvent;
    private String invent;
    private String appScanNum;
    private String appNotScanNum;
    private String pcNum;
    private String inerrantNum;
    private String wrongNum;
    private String addNum;
    private String lossesNum;
    private String worthValue;
}
