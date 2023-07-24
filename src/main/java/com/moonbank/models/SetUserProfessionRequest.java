package com.moonbank.models;

import lombok.Data;

@Data
public class SetUserProfessionRequest extends MbApiBaseRequest{
    private String mobilePrefix;
    private String mobileNumber;
}
