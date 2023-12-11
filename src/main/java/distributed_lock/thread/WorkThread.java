package distributed_lock.thread;

import org.springframework.data.redis.core.RedisTemplate;
import utils.RedisUtil;
import utils.ThreadIdUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class WorkThread implements Callable<Boolean> {

    public WorkThread(){}

    @Override
    public Boolean call()  {

        RedisTemplate template = RedisUtil.getRedisTemplate();
        boolean b = true;

        while (b) {
            if (template.opsForValue().setIfAbsent("lock", "monitor", 5, TimeUnit.SECONDS)) {
                System.out.println("Thread " + ThreadIdUtil.getId() + " get the lock");
                b = false;
            }
        }

        System.out.println("Thread " + ThreadIdUtil.getId() + " stop");
        return null;
    }
}
