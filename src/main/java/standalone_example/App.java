package standalone_example;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class App {
    public static void main(String[] args) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("192.168.247.100", 6379);
        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.afterPropertiesSet();

        RedisTemplate template = new RedisTemplate();
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        StringRedisSerializer serializer = new StringRedisSerializer();


        template.setConnectionFactory(factory);
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();


        System.out.println(template.getDefaultSerializer().getClass().getName());

        template.opsForValue().set("books","java");
//        System.out.println((String) template.opsForValue().get("books"));
    }
}
