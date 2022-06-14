package cn.cqray.springboot.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 接口编写相关配置信息
 * @author Cqray
 */
@Configuration(value = ApiConstants.CONFIGURATION_API)
@ComponentScan(basePackageClasses = ApiAutoConfiguration.class)
public class ApiAutoConfiguration {}
