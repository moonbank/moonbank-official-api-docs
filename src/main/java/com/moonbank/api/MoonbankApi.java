package com.moonbank.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.moonbank.constants.MoonbankMethods;
import com.moonbank.models.*;
import com.moonbank.utils.MoonbankEncryptUtil;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;

public class MoonbankApi {

    // test env gateway
    private static final String GATEWAY = "http://localhost:8848";

    private static final int NOTIFY_TIMEOUT = 15000;

    private static final int NOTIFY_CONNECT_TIMEOUT = 1000;

    // if use proxy ,set this value true
    private static boolean useProxy = false;

    // proxy ip
    private static String proxyAddress = "127.0.0.1";

    // proxy port
    private static int proxyPort = 7070;

    // APPID
    private static final String APP_ID = "app_447770";

    // SECRET
    private static String APP_SECRET = "123456";

    /**
     * get system clock(system status)
     */
    public static void getSystemClock() {
        SystemClockRequest request = new SystemClockRequest();
        String result = postData(null,MoonbankMethods.SYS_CLOCK, request);
        System.out.println("getSystemClock response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("getSystemClock response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("getSystemClock encode===>" + descStr);
        }
    }

    /**
     * get template list
     */
    public static void bankcardTemplateList() {
        BankcardTemplateListRequest request = new BankcardTemplateListRequest();
        String result = postData(null,MoonbankMethods.BANKCARD_TEMPLATE_LIST, request);
        System.out.println("bankcardTemplateList response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("bankcardTemplateList response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("bankcardTemplateList encode result===>" + descStr);
        }
    }

    /**
     * user register,get user unique ID
     *
     * @param mobilePrefix mobile prefix
     * @param mobileNumber mobile number
     */
    public static void userRegister(String mobilePrefix, String mobileNumber) {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setMobilePrefix(mobilePrefix);
        request.setMobileNumber(mobileNumber);
        String result = postData(null,MoonbankMethods.USER_REGISTER, request);
        System.out.println("userRegister response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("userRegister response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("userRegister encode result===>" + descStr);
        }
    }

    /**
     * set user profession and user info
     * @param uId
     * @param mobilePrefix
     * @param mobileNumber
     */
    public static void setUserProfession(String uId,String mobilePrefix, String mobileNumber) {
        SetUserProfessionRequest request = new SetUserProfessionRequest();
        request.setMobilePrefix(mobilePrefix);
        request.setMobileNumber(mobileNumber);
        String result = postData(uId,MoonbankMethods.SET_USER_PROFESSION, request);
        System.out.println("setUserProfession response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("setUserProfession response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("setUserProfession encode result===>" + descStr);
        }
    }


    public static void main(String[] args) {
        getSystemClock();

//        bankcardTemplateList();

//        userRegister("86","18888888888");
//        setUserProfession("123","86", "18888888888");
    }

    /**
     * send post data
     * @param uId
     * @param method
     * @param request
     * @return
     *
     */
    private static String postData(String uId, String method, MbApiBaseRequest request) {

        String jsonDataString = JSON.toJSONString(request);

        System.out.println("post path：" + method);
        System.out.println("body：" + jsonDataString);

        String sendContent = method + jsonDataString;
        String signature = MoonbankEncryptUtil.encode(APP_SECRET, sendContent);
        HttpRequest httpRequest = HttpRequest.post(GATEWAY + method).header("appid", APP_ID).header("sign", signature);

        if (!Strings.isNullOrEmpty(uId)) {
            httpRequest.header("uId", uId);
        }

        System.out.println("post all headers: " + httpRequest.headers());

        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
            httpRequest.setProxy(proxy);
        }

        httpRequest.timeout(NOTIFY_TIMEOUT)
                .body(jsonDataString)
                .charset(StandardCharsets.UTF_8)
                .setConnectionTimeout(NOTIFY_CONNECT_TIMEOUT);
        return httpRequest.execute().body();
    }
}
