package cn.cqray.springboot.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    private static volatile JsonUtil sInstance;
    private final ObjectMapper objectMapper;

    private JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        if (sInstance == null) {
            synchronized (JsonUtil.class) {
                if (sInstance == null) {
                    sInstance = this;
                }
            }
        }
    }

    @SneakyThrows
    public static String toJson(Object o) {
        return sInstance.objectMapper.writeValueAsString(o);
    }

    @SneakyThrows
    public static <T> T parse(String json, Class<T> cls) {
        return sInstance.objectMapper.readValue(json, cls);
    }
}
