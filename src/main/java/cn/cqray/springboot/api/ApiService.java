package cn.cqray.springboot.api;

import cn.cqray.springboot.api.dto.PageDto;
import cn.cqray.springboot.api.response.ResponseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * Api开发总服务
 * @author Cqray
 */

@Service(value = ApiConstants.SERVICE_API)
public class ApiService {

    /** 接口配置参数 **/
    private volatile ApiConfig apiConfig;
    /** 接口响应服务 **/ @Getter
    private final ResponseService responseService;
    /** 接口参数验证 **/
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ApiService(ResponseService responseService) {
        this.responseService = responseService;
    }

    /**
     * 接口参数验证
     * @param object 要验证的对象
     * @param groups 要验证的分组
     */
    public void validate(Object object, Class<?>... groups) {
        // 检查验证参数
        if (object == null) {
            fail("参数对象为Null");
            return;
        }
        // 验证分页信息
        if (object instanceof PageDto) {
            PageDto dto = (PageDto) object;
            if (dto.isPageable()) {
                if (dto.getPageNum() == null) {
                    fail("分页页码不能为空");
                    return;
                }
                if (dto.getPageSize() == null) {
                    fail("分页大小不能为空");
                }
            }
        }
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(o -> fail(o.getMessage()));
        }
    }

    public String getHeader(String s) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getHeader(s);
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    @Autowired(required = false)
    void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public ApiConfig getApiConfig() {
        if (this.apiConfig == null) {
            synchronized(ApiService.class) {
                if (this.apiConfig == null) {
                    this.apiConfig = ApiConfig.builder().build();
                }
            }
        }
        return this.apiConfig;
    }

    public <T> T fail(String message) {
        return responseService.fail(message);
    }

    public <T> T fail(String code, String message) {
        return responseService.fail(code, message);
    }

    public <T> T fail(String code, String message, Object data) {
        return responseService.fail(code, message, data);
    }

    public <T> T succeed() {
        return responseService.succeed(null);
    }

    public <T> T succeed(Object data) {
        return responseService.succeed(data);
    }

    public <T> T succeed(Object data, String message) {
        return responseService.succeed(data, message);
    }

    public <T> T succeedWithPage(List<?> data) {
        return responseService.succeedWithPage(data);
    }

    public <T> T succeedWithPage(List<?>  data, String message) {
        return responseService.succeedWithPage(data, message);
    }
}

