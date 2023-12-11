package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class App {

   static ConfigurableApplicationContext context;

    public static void main(String[] args) {
         context = SpringApplication.run(App.class);
        System.out.println("22222222222");
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("192.168.247.100", 6379);
        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.afterPropertiesSet();
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        template.opsForList().leftPush("1","www.baidu.com");
//        RedisConfiguration rc = context.getBean(RedisConfiguration.class);
//        RedisUtils redisUtils = new RedisUtils();
//        redisUtils.addLink("1","www.baidu.com");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void exampleA(){
        System.out.println("hello world, I have just started up");
//        RedisConfiguration rc = context.getBean(RedisConfiguration.class);
//        System.out.println("11111");
    }
}
