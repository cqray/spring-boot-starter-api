package cn.cqray.springboot.api.response;

import cn.cqray.springboot.api.ApiAutoConfiguration;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;

/**
 * 返回值处理工具
 * @author Cqray
 */
@RestControllerAdvice(annotations = RestController.class)
public class ResponseDataAdvice implements ResponseBodyAdvice<Object> {

    private final ApiAutoConfiguration configuration;
    private final Field mtTypeField;
    private final Field mtSubtypeField;

    @SneakyThrows
    public ResponseDataAdvice(ApiAutoConfiguration configuration) {
        this.configuration = configuration;
        this.mtTypeField = MimeType.class.getDeclaredField("type");
        this.mtSubtypeField = MimeType.class.getDeclaredField("subtype");
        this.mtTypeField.setAccessible(true);
        this.mtSubtypeField.setAccessible(true);
    }

    @Override
    public boolean supports(@NotNull MethodParameter methodParameter, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        return configuration.getApiConfig().isResponseAdviceEnable();
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(@Nullable Object o,
                                  @NotNull MethodParameter methodParameter,
                                  @NotNull MediaType mediaType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> aClass,
                                  @NotNull ServerHttpRequest serverHttpRequest,
                                  @NotNull ServerHttpResponse serverHttpResponse) {
        // 处理Null和String类型返回值
        if(o == null || o instanceof String) {
            mtTypeField.set(mediaType, "application");
            mtSubtypeField.set(mediaType, "json");
            return ResponseData.succeed(o).getBody();
        }
        // 处理ResponseData类型返回值
        if (o instanceof ResponseData) {
            return ((ResponseData) o).getBody();
        }
        // 处理其他返回值，外层需包裹ResponseData
        Class<?>[] classes = configuration.getApiConfig().getResponseExcludeClasses();
        if (classes != null) {
            // 如果有排除在外的类，则直接返回，不需要包裹在ResponseData内
            for (Class<?> cls : classes) {
                if (cls.isAssignableFrom(o.getClass())) {
                    return o;
                }
            }
        }
        return ResponseData.succeed(o).getBody();
    }

    @ExceptionHandler(ResponseException.class)
    public Object handle(@NotNull ResponseException exc) {
        return exc.getResponseData().getBody();
    }
}
