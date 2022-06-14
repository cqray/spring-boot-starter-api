package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiConfig;
import cn.cqray.springboot.api.ApiConstants;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Response服务
 * @author Cqray
 */
@Service(value = ApiConstants.SERVICE_RESPONSE)
public class ResponseService {

    private final ApiConfig apiConfig;

    public ResponseService(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public <T> T fail(String message) {
        String code = apiConfig.getFailureCode();
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message) {
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message, Object data) {
        throw new ResponseException(code, message, data);
    }

    public <T> T succeed(Object data) {
        String message = apiConfig.getSuccessMessage();
        return succeed(data, message);
    }

    public <T> T succeed(Object data, String message) {
        String code = apiConfig.getSuccessCode();
        throw new ResponseException(code, message, data);
    }

    public <T> T succeedWithPage(List<?> data) {
        return succeedWithPage(data, apiConfig.getSuccessMessage());
    }

    public <T> T succeedWithPage(List<?> data, String message) {
        String code = apiConfig.getSuccessCode();
        Object page = null;
        ResponsePageProvider pageProvider = apiConfig.getPageProvider();
        if (pageProvider != null) {
            page = pageProvider.getPage(data);
        }
        throw new ResponseException(code, message, data, page);
    }
}
