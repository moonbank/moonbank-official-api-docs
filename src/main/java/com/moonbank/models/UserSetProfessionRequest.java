package com.moonbank.models;

import lombok.Data;

@Data
public class UserRegisterRequest extends MbApiBaseRequest{
    private String mobilePrefix;
    private String mobileNumber;
}
