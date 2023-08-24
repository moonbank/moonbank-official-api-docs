package com.moonbank.models;

import lombok.Data;

@Data
public class ApplyBankcardRequest extends MbApiBaseRequest{
    private Integer bankcardId;
    private String residenceAddress;
    private Integer userBankcardId;
    private String tag;
}
