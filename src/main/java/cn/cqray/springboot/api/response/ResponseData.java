package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应体数据集
 * @author Cqray
 */
@Slf4j
@Component
public class ResponseData {

    /** 首个实例 **/
    private static transient volatile ResponseData firstInstance;
    /** 配置信息 **/
    private transient ApiConfig apiConfig;
    /** 格式转化工具 **/
    private transient ObjectMapper objectMapper;
    /** 数据内容 **/
    private transient final Map<String, Object> body = new HashMap<>();

    private ResponseData() {
        if (firstInstance == null) {
            synchronized (ResponseData.class) {
                if (firstInstance == null) {
                    firstInstance = this;
                }
            }
        }
    }

    @Autowired
    void setApiConfig(ApiConfig apiConfig) {
        log.info("ResponseData初始化ApiConfig");
        this.apiConfig = apiConfig;
    }

    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        log.info("ResponseData初始化ObjectMapper");
        this.objectMapper = objectMapper;
    }

//    public ResponseData setCode(String code) {
//        ApiConfig config = apiAutoConfiguration.getApiConfig();
//        body.put(config.getCodeKey(), code);
//        return this;
//    }
//
//    public ResponseData setData(Object data) {
//        ApiConfig config = apiAutoConfiguration.getApiConfig();
//        body.put(config.getDataKey(), data);
//        return this;
//    }
//
//    public ResponseData setMessage(String message) {
//        ApiConfig config = apiAutoConfiguration.getApiConfig();
//        body.put(config.getMessageKey(), message);
//        return this;
//    }

    public Map<String, Object> getBody() {
        return body;
    }

    @SneakyThrows
    @Override
    public String toString() {
        return firstInstance.objectMapper.writeValueAsString(body);
    }

    @NotNull
    public static ResponseData succeed(Object data) {
        ApiConfig config = getApiConfig();
        return newResponseData(config.getSuccessCode(), config.getSuccessMessage(), data, null);
    }

    @NotNull
    public static ResponseData failed(String code, String message) {
        return newResponseData(code, message, null, null);
    }

    @NotNull
    static ResponseData newResponseData(String code, String message, Object data, Object page) {
        ApiConfig config = getApiConfig();
        ResponseData rd = new ResponseData();
        rd.body.put(config.getCodeKey(), code);
        if (config.isResponseShowNull() || data != null) {
            rd.body.put(config.getDataKey(), data);
        }
        if (config.isResponseShowNull() || message != null) {
            rd.body.put(config.getMessageKey(), message);
        }
        if (config.isResponseShowNull() || page != null) {
            rd.body.put(config.getPageKey(), page);
        }
        return rd;
    }

    static ApiConfig getApiConfig() {
        if (firstInstance.apiConfig == null) {
            synchronized (ResponseData.class) {
                if (firstInstance.apiConfig == null) {
                    // 用户没有设置，则使用默认配置
                    firstInstance.apiConfig = new ApiConfig();
                }
            }
        }
        return firstInstance.apiConfig;
    }
}
