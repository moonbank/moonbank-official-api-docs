package com.mine.api.demo.domain;

/**
 * 通用常量信息
 *
 * @author shark
 */
public class Constants
{
    //true:开启单点登录 false:关闭单点登录
    public static final boolean singleSign = false;

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi://";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap://";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    //http form请求
    public final static String HTTP_FORM_TYPE = "application/x-www-form-urlencoded";

    //http json请求
    public final static String HTTP_JSON_TYPE = "application/json";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌有效期（分钟）
     */
    public final static long TOKEN_EXPIRE = 720;

    /**
     * 令牌
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";


    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     *采购协议印章
     */
    public static final String PURSECHASE_SEAL="pursechaseSeal";


    /**
     *采购协议
     */
    public static final String PURSECHASE_CONTRACT="采购协议";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 注册用户短信 redis key
     */
    public static final String REG_USER_SMS_KEY = "reg_user_sms:";
    /**
     *阿里云短信配置
     */
    public static final String ALICLOUDSMS = "AliCloudSms";
    /**
     * 注册用户短信有效期（分钟）
     */
    public static final Integer REG_USER_SMS_EXPIRATION = 5;
    /**
     *企业邮箱账号配置
     */
    public static final String EMAILINFO = "emaiInfo";
    /**
     * erp场景字符
     */
    public static final String ERP_SCENEKEY = "erp";

    public enum StatusEnums {

        /**
         * 0
         */
        EXCEPTION_STATUS(1, "无效/不正常"),
        /**
         * 1
         */
        NORMAL_STATUS(0, "有效/正常"),
        ;

        StatusEnums(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private Integer code;

        private String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
