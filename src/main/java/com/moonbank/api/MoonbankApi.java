package com.moonbank.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moonbank.constants.MoonbankMethods;
import com.moonbank.models.ApiResponse;
import com.moonbank.models.MbApiBaseRequest;
import com.moonbank.models.SystemClockRequest;
import com.moonbank.utils.AESUtils;
import com.moonbank.utils.MoonbankEncryptUtil;
import org.apache.logging.log4j.util.Base64Util;

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
     * get system clock
     */
    public static void getSystemClock() {
        SystemClockRequest request = new SystemClockRequest();
        String result = postData(MoonbankMethods.SYS_CLOCK, request);
        System.out.println("getSystemClock response:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("getSystemClock response:  " + result);
        if (apiResponse.isSuccess()) {
            String descStr = MoonbankEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("getSystemClock encode===>" + descStr);
        }
    }


    public static void main(String[] args) {
        getSystemClock();
    }


    /**
     * send post data
     *
     * @param method
     * @param request
     * @return
     */
    private static String postData(String method, MbApiBaseRequest request) {

        String jsonDataString = JSON.toJSONString(request);

        System.out.println("post path：" + method);
        System.out.println("body：" + jsonDataString);

        String sendContent = method + jsonDataString;
        String signature = MoonbankEncryptUtil.encode(APP_SECRET, sendContent);
        HttpRequest httpRequest = HttpRequest.post(GATEWAY + method).header("appid", APP_ID).header("sign", signature);

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
