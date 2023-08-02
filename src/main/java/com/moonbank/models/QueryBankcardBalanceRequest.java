package com.moonbank.models;

import lombok.Data;

@Data
public class QueryBankcardBalanceRequest extends MbApiBaseRequest{
    private Integer userBankcardId;
}
