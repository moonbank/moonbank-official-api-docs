package com.moonbank.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRechargeInfoRequest extends MbApiBaseRequest{
    private String currency;

    private BigDecimal amount;
}
