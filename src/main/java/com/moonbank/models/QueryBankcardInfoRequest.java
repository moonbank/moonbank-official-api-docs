package com.moonbank.models;

import lombok.Data;

@Data
public class QueryBankcardInfoRequest extends MbApiBaseRequest{
    private Integer userBankcardId;
}
