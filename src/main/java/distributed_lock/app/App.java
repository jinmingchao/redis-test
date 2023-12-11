package distributed_lock.app;

import distributed_lock.thread.WorkThread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

        for(int i = 0; i < 3;i++) {
            pool.submit(new WorkThread());
        }
    }
}
