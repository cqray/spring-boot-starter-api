package cn.cqray.springboot.api.redis;

import cn.cqray.springboot.api.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 * @author Cqray
 */
@Configuration(value = ApiConstants.CONFIGURATION_REDIS)
public class RedisConfiguration {

    @Bean(name = ApiConstants.BEAN_REDIS_OBJECT)
    public RedisTemplate<String, Object> getObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Key的序列化采用StringRedisSerializer
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        // Value的序列化采用GenericJackson2JsonRedisSerializer
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
