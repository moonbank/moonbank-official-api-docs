package com.moonbank.utils;

import cn.hutool.core.codec.Base64;

/**
 * encrypt util
 */
public class MoonbankEncryptUtil {

    /**
     * encode
     *
     * @param secret
     * @param content
     * @return
     */
    public static String encode(String secret, String content) {
        String aesString = AESUtils.encode(secret, Base64.encode(content));
        return MD5Util.digest(aesString);
    }

    /**
     * decode
     *
     * @param secret
     * @param content
     * @return
     */
    public static String decode(String secret, String content) {
        String base64String = AESUtils.decode(secret, content);
        return Base64.decodeStr(base64String);
    }
}
