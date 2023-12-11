package scan_vs_keys;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import utils.RedisUtil;

import java.util.LinkedList;
import java.util.List;

public class App_1 {

    private static RedisTemplate redisTemplate;

    static {
        redisTemplate = RedisUtil.getRedisTemplate();
    }

    public static void main(String[] args) {
        //存数个key
        setVal(10000);
        //使用SCAN进行遍历
        //对应命令SCAN cursor [MATCH pattern] [Count count] [Type type]
        //如 scan 10727 match key99* count 1000
        ScanOptions scanner = ScanOptions.scanOptions().match("key99*").count(1000L).build();
        Cursor cursor = redisTemplate.scan(scanner);
        List<String> res = new LinkedList<>();
        while (cursor.getCursorId() != 0) { //cursor不为0, 说明还没遍历完所有的slot
            System.out.println("Cursor's id is " +cursor.getCursorId());
            while (cursor.hasNext()) {
                System.out.println("Next - Cursor's id is " +cursor.getCursorId());
               String value = (String) cursor.next();
               System.out.println(value);
               res.add(value);
            }
            System.out.println("The number of element in res is " +res.size());
        }

    }

    public static boolean setVal(Integer times) {
        ValueOperations operator = redisTemplate.opsForValue();
        String str;
        for (Integer i = 1; i <= times; i++) {
            str = "key" + i;
            operator.set(str, i.toString());
        }
        return true;
    }
}
