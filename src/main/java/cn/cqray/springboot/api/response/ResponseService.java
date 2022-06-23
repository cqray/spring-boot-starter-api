package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiConstants;
import cn.cqray.springboot.api.ApiService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Response服务
 * @author Cqray
 */
@Service(value = ApiConstants.SERVICE_RESPONSE)
public class ResponseService {

    private final ApiService apiService;

    public ResponseService(ApiService apiService) {
        this.apiService = apiService;
    }

    public <T> T fail(String message) {
        String code = apiService.getApiConfig().getFailureCode();
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message) {
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message, Object data) {
        throw new ResponseException(code, message, data);
    }

    public <T> T succeed(Object data) {
        String message = apiService.getApiConfig().getSuccessMessage();
        return succeed(data, message);
    }

    public <T> T succeed(Object data, String message) {
        String code = apiService.getApiConfig().getSuccessCode();
        throw new ResponseException(code, message, data);
    }

    public <T> T succeedWithPage(List<?> data) {
        return succeedWithPage(data, apiService.getApiConfig().getSuccessMessage());
    }

    public <T> T succeedWithPage(List<?> data, String message) {
        String code = apiService.getApiConfig().getSuccessCode();
        Object page = null;
        ResponsePageProvider pageProvider = apiService.getApiConfig().getPageProvider();
        if (pageProvider != null) {
            page = pageProvider.getPage(data);
        }
        throw new ResponseException(code, message, data, page);
    }
}
