package xian_liu;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import utils.RedisUtil;

import java.util.concurrent.TimeUnit;

/**
 * 场景1: 系统要限定用户的某个行为在指定的时间里只能允许发生N次
 * <p>
 * # 指定用户 user_id 的某个行为 action_key 在特定的时间内 period 只允许发生一定的次数
 * <p>
 * <p>
 * 解决方案:
 * 用一个 zset 结构记录用户的行为历史，每一个行为都会作为 zset 中的一个
 * key 保存下来。同一个用户同一种行为用一个 zset 记录。
 */
public class App_1 {

    private RedisTemplate redisTemplate;

    public App_1() {
        redisTemplate = RedisUtil.getRedisTemplate();
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        ZSetOperations zset = redisTemplate.opsForZSet();
        // zadd 在key对应zset中加入代表本次行为的value和score，都是当前时间戳
        zset.add(key, "" + nowTs, nowTs); //key, value, score
        // zrem 范围移除zset窗口外的数据
        zset.removeRangeByScore(key, 0, nowTs - period * 1000); // 删除分[0, nowTs - period]段内的value
        // 每次插入操作时, 重新设置 zset 过期时间，避免冷用户持续占用内存
        // 过期时间应该等于时间窗口的长度, 再多宽限 1s
        redisTemplate.expire(key, period, TimeUnit.SECONDS);
        // 如果一共存在的操作记录 <= maxCount, 则可以继续操作;
        return zset.zCard(key) <= maxCount;
    }

    public static void main(String[] args) {
        App_1 limiter = new App_1();
        for (int i = 0; i < 20; i++) {
            // zrangebyscore hist:jinmingchao:reply 0 inf withscores
            System.out.println(limiter.isActionAllowed("jinmingchao", "reply", 60, 5));
        }
    }

//    private Jedis jedis;
//
//    public App_1(Jedis jedis) {
//        this.jedis = jedis;
//    }
//
//    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
//        String key = String.format("hist:%s:%s", userId, actionKey);
//        long nowTs = System.currentTimeMillis();
//        Pipeline pipe = jedis.pipelined();
//        pipe.multi();
//        pipe.zadd(key, nowTs, "" + nowTs);
//        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
//        Response<Long> count = pipe.zcard(key);
//        pipe.expire(key, period + 1);
//        pipe.exec();
//        pipe.close();
//        return count.get() <= maxCount;
//    }
//
//    public static void main(String[] args) {
//        Jedis jedis = new Jedis();
//        App_1 limiter = new App_1(jedis);
//        for (int i = 0; i < 20; i++) {
//            System.out.println(limiter.isActionAllowed("laoqian", "reply", 60, 5));
//        }
//    }
}
