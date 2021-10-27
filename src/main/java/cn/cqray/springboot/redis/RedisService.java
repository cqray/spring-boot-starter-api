package cn.cqray.springboot.redis;

import cn.cqray.springboot.ApiConfig;
import cn.cqray.springboot.ApiConfiguration;
import cn.cqray.springboot.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务
 * @author Cqray
 */
@Service(value = ApiConstants.SERVICE_REDIS)
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;
    private RedisLockService redisLockService;
    private final ApiConfiguration hotchpotchConfiguration;

    public RedisService(ApiConfiguration hotchpotchConfiguration) {
        this.hotchpotchConfiguration = hotchpotchConfiguration;
    }

    @Autowired(required = false)
    void setRedisLockService(RedisLockService redisLockService) {
        this.redisLockService = redisLockService;
    }

    @Autowired
    @Qualifier(ApiConstants.BEAN_REDIS_OBJECT)
    void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public String lock(String key, String value, long timeout) {
        if (redisLockService != null) {
            return redisLockService.lock(key, value, timeout);
        }
        return null;
    }

    public String tryLock(String key, String value, long timeout) {
        if (redisLockService != null) {
            return redisLockService.tryLock(key, value, timeout);
        }
        return null;
    }

    public void unlock(String key, String token) {
        if (redisLockService != null) {
            redisLockService.unlock(key, token);
        }
    }

    /**
     * 判断keys是否存在
     * @param keys 键值列表
     */
    public boolean hasKey(String... keys) {
        if (keys == null || keys.length == 0) {
            return false;
        }
        for (String key : keys) {
            Boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey == null || !hasKey) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取值的类型
     * @param key 键
     */
    public String type(String key) {
        DataType dataType = redisTemplate.type(key);
        assert dataType != null;
        return dataType.code();
    }

    public void delete(String... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        set(key, value, getDefaultExpire());
    }

    /**
     * 添加缓存并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time <= 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取缓存值
     * @param key 键
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time <= 0 ? 0 : time, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存失效剩余时间(秒)
     * @param key 键
     * @return -1永久 -2过期
     */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire == null ? -2 : expire;
    }

    /**
     * 增加指定键的值
     * @param key 键
     * @param addition 增加量
     * @return 处理后的值
     */
    public long add(String key, long addition) {
        Long value;
        if (addition >= 0) {
            value = redisTemplate.opsForValue().increment(key, addition);
        } else {
            value = redisTemplate.opsForValue().decrement(key, -addition);
        }
        return value == null ? 0 : value;
    }

    public HashOperations<String, String, Object> getHash() {
        return redisTemplate.opsForHash();
    }

    public ListOperations<String, Object> getList() {
        return redisTemplate.opsForList();
    }

    public long getDefaultExpire() {
        ApiConfig config = hotchpotchConfiguration.getApiConfig();
        long time = config.getRedisExpireTime();
        boolean useExpire = config.isRedisUseExpire();
        return useExpire ? time : 0;
    }
}
