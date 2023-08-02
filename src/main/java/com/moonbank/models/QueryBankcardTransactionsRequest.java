package com.moonbank.models;

import lombok.Data;

@Data
public class QueryBankcardTransactionsRequest extends MbApiBaseRequest{
    private Integer userBankcardId;
    private Integer pageSize;
    private Integer pageNum;
    private Long fromTimestamp;
    private Long endTimestamp;
}
