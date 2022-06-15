package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应体数据集
 * @author Cqray
 */
@Component
public class ResponseData {

    /** 首个实例 **/
    private static transient volatile ResponseData firstInstance;
    /** 配置信息 **/
    private transient ApiConfig apiConfig;
    /** 格式转化工具 **/
    private transient ObjectMapper objectMapper;
    /** 数据内容 **/
    private final Map<String, Object> body = Collections.synchronizedMap(new HashMap<>());

    private ResponseData() {
        // 记录首个实例，不出意外是在IOC容器中
        if (firstInstance == null) {
            synchronized (ResponseData.class) {
                if (firstInstance == null) {
                    firstInstance = this;
                }
            }
        }
    }

    /**
     * 设置配置信息，仅作用于首个实例
     * @param apiConfig 配置信息
     */
    @Autowired
    void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    /**
     * 设置Jackson对象操作，仅作用于首个实例
     * @param objectMapper 实例
     */
    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseData setCode(String code) {
        body.put(getApiConfig().getCodeKey(), code);
        return this;
    }

    public ResponseData setData(Object data) {
        body.put(getApiConfig().getDataKey(), data);
        return this;
    }

    public ResponseData setMessage(String message) {
        body.put(getApiConfig().getMessageKey(), message);
        return this;
    }

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
        return create(config.getSuccessCode(), config.getSuccessMessage(), data, null);
    }

    @NotNull
    public static ResponseData fail(String code, String message) {
        return create(code, message, null, null);
    }

    @NotNull
    public static ResponseData create(String code, String message, Object data, Object page) {
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

    /**
     * 获取API相关配置，没有配置则使用默认
     */
    static ApiConfig getApiConfig() {
        if (firstInstance.apiConfig == null) {
            synchronized (ResponseData.class) {
                if (firstInstance.apiConfig == null) {
                    // 用户没有设置，则使用默认配置
                    firstInstance.apiConfig = ApiConfig.builder().build();
                }
            }
        }
        return firstInstance.apiConfig;
    }
}
