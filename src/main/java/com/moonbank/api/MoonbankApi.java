package com.moonbank.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.moonbank.constants.MoonbankMethods;
import com.moonbank.models.*;
import com.moonbank.utils.Base64ImgUtil;
import com.moonbank.utils.MoonbankEncryptUtil;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;

public class MoonbankApi {

    // test env gateway
    private static final String GATEWAY = "https://test.moonbank.me/api-web";
    // dev env gateway
//    private static final String GATEWAY = "http://localhost:8848/api-web";

    private static final int NOTIFY_TIMEOUT = 15000;

    private static final int NOTIFY_CONNECT_TIMEOUT = 1000;

    // if use proxy ,set this value true
    private static boolean useProxy = true;

    // proxy ip
    private static String proxyAddress = "127.0.0.1";

    // proxy port
    private static int proxyPort = 7070;

    // APPID
    private static final String APP_ID = "app_447770";

    // SECRET
    private static String APP_SECRET = "b635dd5c87f7bf73387929203321b1e1";

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
    public static void userRegister(String mobilePrefix, String mobileNumber,String email) {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setMobilePrefix(mobilePrefix);
        request.setMobileNumber(mobileNumber);
        request.setEmail(email);
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
     */
    public static void setUserProfession(String uId) {
        UserSetProfessionRequest request = new UserSetProfessionRequest();
        request.setFirst_name("ming");
        request.setFirst_name_en("ming");
        request.setLast_name("li");
        request.setLast_name_en("li");
        request.setBirthday("2000-01-01");
        request.setAnnual_income("100000");
        request.setOccupation("boss");
        request.setPosition("management");
        request.setId_type("passport");
        request.setCountry("CN");
        request.setNumber("123456");
        request.setExpiry_date("2027-01-01");
//        request.setFrontImg(Base64ImgUtil.GetImageStr("/Users/donnie/moonbank-official-api-docs/src/main/resources/passport1.jpg","jpg"));
//        request.setBackImg(Base64ImgUtil.GetImageStr("/Users/donnie/moonbank-official-api-docs/src/main/resources/passport2.jpg","jpg"));
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

    /**
     * apply bankcard
     * @param uId
     * @param bankcardId
     * @param residenceAddress
     */
    public static void applyBankcard(String uId,Integer bankcardId,String residenceAddress) {
        ApplyBankcardRequest request = new ApplyBankcardRequest();
        request.setBankCardId(bankcardId);
        request.setResidenceAddress(residenceAddress);
        String result = postData(uId,MoonbankMethods.APPLY_BANKCARD, request);
        System.out.println("applyBankcard response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("applyBankcard response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("applyBankcard encode result===>" + descStr);
        }
    }

    public static void rechargeBankcard(String uId, Integer userBankcardId, BigDecimal amount) {
        RechargeBankcardRequest request = new RechargeBankcardRequest();
        request.setUserBankcardId(userBankcardId);
        request.setAmount(amount);
        String result = postData(uId,MoonbankMethods.RECHARGE_BANKCARD, request);
        System.out.println("rechargeBankcard response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("rechargeBankcard response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("rechargeBankcard encode result===>" + descStr);
        }
    }

    public static void setBankcardPin(String uId, Integer userBankcardId, String pin) {
        SetBankcardPinRequest request = new SetBankcardPinRequest();
        request.setUserBankcardId(userBankcardId);
        request.setPin(pin);
        String result = postData(uId,MoonbankMethods.SET_BANKCARD_PIN, request);
        System.out.println("setBankcardPin response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("setBankcardPin response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("setBankcardPin encode result===>" + descStr);
        }
    }


    public static void main(String[] args) {
//        getSystemClock();

        bankcardTemplateList();

//        userRegister("1","18888888867","188888888662@188.com");
//        setUserProfession("hgao4u6m26jvhael");
//        applyBankcard("hgao4u6m26jvhael",9,"China");
//        rechargeBankcard("hgao4u6m26jvhael",135,new BigDecimal(16));

//        setBankcardPin("hgao4u6m26jvhael",136,"123456");
    }

    /** util method
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

        String sendContent = method+jsonDataString;
        System.out.println("originString="+sendContent);
        String signature = MoonbankEncryptUtil.encode(APP_SECRET, sendContent);
        System.out.println("sign="+signature);
        HttpRequest httpRequest = HttpRequest.post(GATEWAY + method).header("appId", APP_ID).header("sign", signature);

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
