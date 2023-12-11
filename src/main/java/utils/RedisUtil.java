package utils;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisUtil {

    private static RedisStandaloneConfiguration config;

    private static JedisConnectionFactory factory;

    private static RedisTemplate template;

    private static StringRedisSerializer serializer = new StringRedisSerializer();

    static {
        config = new RedisStandaloneConfiguration("192.168.247.100", 6379);
        factory = new JedisConnectionFactory(config);
        factory.afterPropertiesSet();

        template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();
    }

    public static RedisTemplate getRedisTemplate() {
        return template;
    }
}
