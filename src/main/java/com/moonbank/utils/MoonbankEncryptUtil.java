package com.moonbank.utils;

import cn.hutool.core.codec.Base64;
import com.google.common.base.Strings;

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
        String base64String =Base64.encode(content);
        System.out.println("base64String="+base64String);
        String aesString = AesAPIUtils.encode(base64String,secret);
        System.out.println("aesString="+aesString);
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
        if (Strings.isNullOrEmpty(content)) {
            return null;
        }
        String base64String = AesAPIUtils.decode(content,secret);
        return Base64.decodeStr(base64String);
    }
}
