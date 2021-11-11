package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiConfig;
import cn.cqray.springboot.api.ApiConfiguration;
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
    private static volatile ResponseData firstInstance;
    /** 配置信息 **/
    private ApiConfiguration apiConfiguration;
    /** 内容 **/
    private final Map<String, Object> body = new HashMap<>();

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
    void setApiConfig(ApiConfiguration apiConfiguration) {
        log.info("ResponseData初始化ApiConfiguration");
        this.apiConfiguration = apiConfiguration;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    @NotNull
    static ResponseData newResponseData(String code, String message, Object data, Object page) {
        ApiConfig config = firstInstance.apiConfiguration.getApiConfig();
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

    @NotNull
    public static ResponseData succeed(Object data) {
        ApiConfig config = firstInstance.apiConfiguration.getApiConfig();
        return newResponseData(config.getSuccessCode(), config.getSuccessMessage(), data, null);
    }
}
