package cn.cqray.springboot.api;

import cn.cqray.springboot.api.dto.PageDto;
import cn.cqray.springboot.api.redis.RedisService;
import cn.cqray.springboot.api.response.ResponseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
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
@AllArgsConstructor
public class ApiService {

    private final ApiConfiguration apiConfiguration;
    /** Redis服务 **/ @Getter
    private final RedisService redisService;
    /** 接口响应服务 **/ @Getter
    private final ResponseService responseService;
    /** 接口参数验证 **/
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader(s);
    }

    public String getToken() {
        return getHeader(apiConfiguration.getApiConfig().getTokenKey());
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

    public String lockCache(String key, String value, long timeout) {
        return redisService.lock(key, value, timeout);
    }

    public String tryLockCache(String key, String value, long timeout) {
        return redisService.tryLock(key, value, timeout);
    }

    public void unlockCache(String key, String token) {
        redisService.unlock(key, token);
    }

    public boolean hasCache(String... keys) {
        return redisService.hasKey(keys);
    }

    public void deleteCache(String... keys) {
        redisService.delete(keys);
    }

    public void setCache(String key, Object value) {
        redisService.set(key, value);
    }

    public void setCache(String key, Object value, long expireTime) {
        redisService.set(key, value, expireTime);
    }

    public <T> T getCache(String key) {
        return redisService.get(key);
    }

    public void setCacheExpire(String key, long expire) {
        redisService.expire(key, expire);
    }

    public long getCacheExpire(String key) {
        return redisService.getExpire(key);
    }

    public long setCacheAdditon(String key, long addition) {
        return redisService.add(key, addition);
    }

    public HashOperations<String, String, Object> getCacheHash() {
        return redisService.getHash();
    }

    public ListOperations<String, Object> getCacheList() {
        return redisService.getList();
    }
}

