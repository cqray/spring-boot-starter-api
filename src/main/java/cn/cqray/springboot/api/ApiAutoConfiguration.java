package cn.cqray.springboot.api;

import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 接口编写相关配置信息
 * @author Cqray
 */
@PackagePrivate
@Configuration(value = "Api_ApiAutoConfiguration")
@ComponentScan(basePackageClasses = ApiAutoConfiguration.class)
public class ApiAutoConfiguration {

    private volatile ApiConfig apiConfig;

    @Autowired(required = false)
    void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public ApiConfig getApiConfig() {
        if (this.apiConfig == null) {
            synchronized(ApiAutoConfiguration.class) {
                if (this.apiConfig == null) {
                    this.apiConfig = ApiConfig.builder().build();
                }
            }
        }
        return this.apiConfig;
    }
}
