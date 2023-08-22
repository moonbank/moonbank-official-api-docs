package com.moonbank.models;

import lombok.Data;

@Data
public class QueryAccountRechargeRequest extends MbApiBaseRequest{
    private String uid;
    private String symbol;
    private Integer pageNum;
    private Integer pageSize;
}
