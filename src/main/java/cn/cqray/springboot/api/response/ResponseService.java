package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiConfig;
import cn.cqray.springboot.api.ApiConfiguration;
import cn.cqray.springboot.api.ApiConstants;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Response服务
 * @author Cqray
 */
@Service(value = ApiConstants.SERVICE_RESPONSE)
public class ResponseService {

    private final ApiConfiguration apiConfiguration;

    public ResponseService(ApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
    }

    public <T> T fail(String message) {
        String code = apiConfiguration.getApiConfig().getFailureCode();
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message) {
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message, Object data) {
        throw new ResponseException(code, message, data);
    }

    public <T> T succeed(Object data) {
        ApiConfig apiConfig = apiConfiguration.getApiConfig();
        String message = apiConfig.getSuccessMessage();
        return succeed(data, message);
    }

    public <T> T succeed(Object data, String message) {
        String code = apiConfiguration.getApiConfig().getSuccessCode();
        throw new ResponseException(code, message, data);
    }

    public <T> T succeedWithPage(List<?> data) {
        return succeedWithPage(data, apiConfiguration.getApiConfig().getSuccessMessage());
    }

    public <T> T succeedWithPage(List<?> data, String message) {
        ApiConfig apiConfig = apiConfiguration.getApiConfig();
        String code = apiConfig.getSuccessCode();
        Object page = null;
        ResponsePageProvider pageProvider = apiConfig.getPageProvider();
        if (pageProvider != null) {
            page = pageProvider.getPage(data);
        }
        throw new ResponseException(code, message, data, page);
    }

}
