package com.rookie.opcua.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AssetsInfoUpdateDTO {

    private String id;
    private String oldId;
    private String currentNodeId = "1";
    private String isOutside = "";
    private String isSubmitted = "0";
    private Timestamp checkTime;
    private String checkStatus = "00";
    private String checkResult = "";
    private String userName;
    private String city;

}
