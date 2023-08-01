package com.moonbank.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SetBankcardPinRequest extends MbApiBaseRequest{
    private Integer userBankcardId;
    private String pin;
}
