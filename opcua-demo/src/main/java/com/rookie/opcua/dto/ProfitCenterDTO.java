package com.rookie.opcua.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProfitCenterDTO implements Serializable {
    private String rId;
    private String cityCode;
    private String profitCode;

    public ProfitCenterDTO(String rId, String cityCode, String profitCode) {
        this.rId = rId;
        this.cityCode = cityCode;
        this.profitCode = profitCode;
    }
}
