package com.mine.api.demo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OAuthRes {

    private String accessToken;

    private String active;

    private Integer expiresIn;

    private String license;

    private String refreshToken;

    private String tokenType;

    private String scope = "server";

}
