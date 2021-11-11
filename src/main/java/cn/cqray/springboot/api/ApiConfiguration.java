package cn.cqray.springboot.api;

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

    /**
     * 获取用户的设置
     * @param apiConfig 配置
     */
    @Autowired(required = false)
    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
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
}
