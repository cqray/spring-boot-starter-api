package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiAutoConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Response服务
 * @author Cqray
 */
@Service(value = "Api_ResponseService")
public class ResponseService {

    private final ApiAutoConfiguration configuration;

    public ResponseService(@NotNull ApiAutoConfiguration configuration) {
        this.configuration = configuration;
    }

    public <T> T fail(String message) {
        String code = configuration.getApiConfig().getFailureCode();
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message) {
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message, Object data) {
        throw new ResponseException(code, message, data);
    }

    public <T> T succeed(Object data) {
        String message = configuration.getApiConfig().getSuccessMessage();
        return succeed(data, message);
    }

    public <T> T succeed(Object data, String message) {
        String code = configuration.getApiConfig().getSuccessCode();
        throw new ResponseException(code, message, data);
    }

    public <T> T succeedWithPage(List<?> data) {
        return succeedWithPage(data, configuration.getApiConfig().getSuccessMessage());
    }

    public <T> T succeedWithPage(List<?> data, String message) {
        String code = configuration.getApiConfig().getSuccessCode();
        Object page = null;
        ResponsePageProvider pageProvider = configuration.getApiConfig().getPageProvider();
        if (pageProvider != null) {
            page = pageProvider.getPage(data);
        }
        throw new ResponseException(code, message, data, page);
    }
}
