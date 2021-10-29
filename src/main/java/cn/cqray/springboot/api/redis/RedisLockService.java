package cn.cqray.springboot.api.redis;

/**
 * Redis带锁
 * @author Cqray
 */
public interface RedisLockService {

    /**
     * 加锁
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @return 成功返回一个Token值
     */
    String lock(String key, String value, long timeout);

    /**
     * 加锁
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @return 成功返回一个Token值
     */
    String tryLock(String key, String value, long timeout);

    /**
     * 解锁
     * @param key 键
     * @param token 加锁返回的token
     */
    void unlock(String key, String token);
}
