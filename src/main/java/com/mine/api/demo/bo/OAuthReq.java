package com.mine.api.demo.bo;

import lombok.Data;

@Data
public class OAuthReq {

    /**
     * 用户名
     */
//    @NotBlank(message = "{user.username.not.blank}")
//    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH, message = "{user.username.length.valid}")
    private String userName;

    /**
     * 用户密码
     */
//    @NotBlank(message = "{user.password.not.blank}")
//    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}")
    private String password;

    private String grantType;

    private String clientId;

    private String clientSecret;

    private String scope = "server";

    private String deviceType;

}
