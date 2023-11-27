package utils;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

public class RedisUtils {
    // inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations


    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    public void addLink(String userId, String url) {
        listOps.leftPush(userId, url);
    }
}
