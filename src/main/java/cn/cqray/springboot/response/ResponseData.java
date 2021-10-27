package cn.cqray.springboot.response;

import cn.cqray.springboot.ApiConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应体数据集
 * @author Cqray
 */
@Component
public class ResponseData {

    /** 首个实例 **/
    private static volatile ResponseData firstInstance;
    /** 配置 **/
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
    void setApiConfiguration(ApiConfiguration configuration) {
        apiConfiguration = configuration;
    }

    @NotNull
    static ResponseData newResponseData(String code, String message, Object data, Object page) {
        ResponseDataConfig config = getResponseDataConfig();
        ResponseData rd = new ResponseData();
        rd.body.put(config.getCodeKey(), code);
        if (config.isShowNull() || data != null) {
            rd.body.put(config.getDataKey(), data);
        }
        if (config.isShowNull() || message != null) {
            rd.body.put(config.getMessageKey(), message);
        }
        if (data instanceof List) {
            if (config.isShowNull() && page != null) {
                rd.body.put(config.getPageKey(), page);
            }
        }
        return rd;
    }

    static ResponseDataConfig getResponseDataConfig() {
        return firstInstance.apiConfiguration.getResponseDataConfig();
    }

    public Map<String, Object> getBody() {
        return body;
    }

//    @NotNull
//    public static ResponseData fail(String message) {
//        return fail(message, getResponseDataConfig().getFailureCode(), null);
//    }
//
//    @NotNull
//    public static ResponseData fail(String message, String code) {
//        return fail(message, code, null);
//    }
//
//    @NotNull
//    public static ResponseData fail(String message, String code, Object data) {
//        return back(code, data, message);
//    }
//
//    @NotNull
//    public static ResponseData fail(@NotNull ResponseException exc) {
//        return fail(exc.getMessage(), exc.getCode(), null);
//    }
//
//    @NotNull
//    public static ResponseData succeedWithPage(Collection<?> data, Object page) {
//        ResponseDataConfig config = getResponseDataConfig();
//        ResponseData rd = back(config.getSuccessCode(), data, config.getSuccessMessage());
//        rd.body.put("page", page);
//        return rd;
//    }

    @NotNull
    public static ResponseData succeed(Object data) {
        ResponseDataConfig config = getResponseDataConfig();
        return newResponseData(config.getSuccessCode(), config.getSuccessMessage(), data, null);
//        return back(config.getSuccessCode(), data, config.getSuccessMessage());
    }

//    @NotNull
//    public static ResponseData succeed(String message) {
//        ResponseDataConfig config = getResponseDataConfig();
//        return back(config.getSuccessCode(), null, message);
//    }
//
//    @NotNull
//    public static ResponseData succeed(Object data, String message) {
//        ResponseDataConfig config = getResponseDataConfig();
//        return back(config.getSuccessCode(), data, message);
//    }
//
//    @NotNull
//    private static ResponseData back(String code, Object data, String message) {
//        ResponseDataConfig config = getResponseDataConfig();
//        ResponseData rd = new ResponseData();
//        rd.body.put(config.getCodeKey(), code);
//        if (config.isShowNull() || data != null) {
//            rd.body.put(config.getDataKey(), data);
//        }
//        if (config.isShowNull() || message != null) {
//            rd.body.put(config.getMessageKey(), message);
//        }
//        return rd;
//    }
}
