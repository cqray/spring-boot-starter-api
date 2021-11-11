package cn.cqray.springboot.api;

/**
 * 常量类
 * @author Cqray
 */
public class ApiConstants {

    /** 前缀 **/
    static final String PREFIX = "API_";
    /** Response服务名 **/
    public static final String SERVICE_RESPONSE = PREFIX + "ResponseService";
    /** Redis服务名 **/
    public static final String SERVICE_REDIS = PREFIX + "RedisService";
    /** 接口服务名 **/
    public static final String SERVICE_API = PREFIX + "ApiService";
    /** Redis Bean **/
    public static final String BEAN_REDIS_OBJECT = PREFIX + "RedisObjectBean";
    /** Redis配置名 **/
    public static final String CONFIGURATION_REDIS = PREFIX + "RedisConfiguration";
    /** 框架配置名 **/
    public static final String CONFIGURATION_API = PREFIX + "ApiConfiguration";
    /** 请求过滤器 **/
    public static final String FILTER_FIX = PREFIX + "FixFilter";

}
