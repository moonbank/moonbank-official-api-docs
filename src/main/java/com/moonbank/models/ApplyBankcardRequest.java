package com.moonbank.models;

import lombok.Data;

@Data
public class ApplyBankcardRequest extends MbApiBaseRequest{
    private Integer bankCardId;
    private String residenceAddress;
}
