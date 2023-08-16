package com.moonbank.models;

import lombok.Data;

@Data
public class UserRechargeInfoRequest extends MbApiBaseRequest{
    private String currency;
}
