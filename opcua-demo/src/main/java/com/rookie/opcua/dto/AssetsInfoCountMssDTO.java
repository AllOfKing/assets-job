package com.rookie.opcua.dto;

import lombok.Data;

@Data
public class AssetsInfoCountMssDTO {
    private String organId;
    private int cardNum;
    private int gxwcf;
    private int gxycf;
    private int dbwcf;
    private int dbycf;
    private int pkwcf;
    private int pkycf;
    private int pywcf;
    private int pyycf;
    private int cflwcf;
    private int cflycf;
    private double worthValue;
}
