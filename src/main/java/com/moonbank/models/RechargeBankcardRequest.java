package com.moonbank.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeBankcardRequest extends MbApiBaseRequest{
    private Integer userBankcardId;
    private BigDecimal amount;
}
