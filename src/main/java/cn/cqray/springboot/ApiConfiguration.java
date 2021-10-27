package cn.cqray.springboot;

import cn.cqray.springboot.response.ResponseDataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 接口编写相关配置信息
 * @author Cqray
 */
@Configuration(value = ApiConstants.CONFIGURATION_API)
@ComponentScan(basePackageClasses = ApiConfiguration.class)
public class ApiConfiguration {

    /** Starter配置 **/
    private volatile ApiConfig apiConfig;
    /** ResponseData配置 **/
    private volatile ResponseDataConfig responseDataConfig;

    /**
     * 获取用户的设置
     * @param apiConfig 配置
     */
    @Autowired(required = false)
    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    /**
     * 获取用户的设置
     * @param responseDataConfig 配置
     */
    @Autowired(required = false)
    public void setResponseDataConfig(ResponseDataConfig responseDataConfig) {
        this.responseDataConfig = responseDataConfig;
    }

    /**
     * 取Starter配置
     * @return Starter配置对象
     */
    public ApiConfig getApiConfig() {
        if (apiConfig == null) {
            synchronized (ApiConfiguration.class) {
                if (apiConfig == null) {
                    // 用户没有设置，则使用默认配置
                    apiConfig = new ApiConfig();
                }
            }
        }
        return apiConfig;
    }

    /**
     * 取ResponseData配置
     * @return ResponseData配置对象
     */
    public ResponseDataConfig getResponseDataConfig() {
        if (responseDataConfig == null) {
            synchronized (ApiConfiguration.class) {
                if (responseDataConfig == null) {
                    // 用户没有设置，则使用默认配置
                    responseDataConfig = new ResponseDataConfig();
                }
            }
        }
        return responseDataConfig;
    }
}
