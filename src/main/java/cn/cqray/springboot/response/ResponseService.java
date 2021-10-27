package cn.cqray.springboot.response;

import cn.cqray.springboot.ApiConfiguration;
import cn.cqray.springboot.ApiConstants;
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

//    public <T> T fail(String code) {
//        String message = hotchpotchConfiguration.getResponseDataConfig().getFailureMessage();
//        throw new ResponseException(code, message);
//    }

    public <T> T fail(String message) {
        String code = apiConfiguration.getResponseDataConfig().getFailureCode();
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message) {
        throw new ResponseException(code, message);
    }

    public <T> T fail(String code, String message, Object data) {
        throw new ResponseException(code, message, data);
    }

    public <T> T succeed(Object data) {
        String code = apiConfiguration.getResponseDataConfig().getSuccessCode();
        String message = apiConfiguration.getResponseDataConfig().getSuccessMessage();
        Object page = null;
//        if (data instanceof List) {
//            page = new PageInfo<>((List<?>)data);
//        }
        throw new ResponseException(code, message, data, page);
    }

    public <T> T succeed(Object data, String message) {
        String code = apiConfiguration.getResponseDataConfig().getSuccessCode();
        throw new ResponseException(code, message, data);
    }
}
