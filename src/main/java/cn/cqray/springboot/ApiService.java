package cn.cqray.springboot;

import cn.cqray.springboot.redis.RedisService;
import cn.cqray.springboot.response.ResponseService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Hotchpotch总的服务
 * @author Cqray
 */
@Service(value = ApiConstants.SERVICE_API)
public class ApiService {

    private final Validator validator;
    private final RedisService redisService;
    private final ResponseService responseService;

    public ApiService(RedisService redisService, ResponseService responseService) {
        this.redisService = redisService;
        this.responseService = responseService;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public ResponseService getResponseService() {
        return responseService;
    }

    public void validate(Object object, Class<?>... groups) {
        if (object == null) {
            fail("对象为Null");
            return;
        }
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(o -> fail(o.getMessage()));
        }
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
