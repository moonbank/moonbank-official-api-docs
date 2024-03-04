package com.moonbank.models;

import lombok.Data;

@Data
public class KycGatewayRequest extends MbApiBaseRequest {
    private String doneViewURL;
    private String timeoutViewURL;
}
