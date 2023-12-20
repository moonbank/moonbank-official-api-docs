package com.moonbank.models;

import lombok.Data;

@Data
public class QueryMerchantRechargeRequest extends MbApiBaseRequest{
    private Integer pageNum;
    private Integer pageSize;
}
