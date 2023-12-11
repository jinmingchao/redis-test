package utils;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadIdUtil {

    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(1);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    Integer id = nextId.getAndIncrement();
                    return id;
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int getId() {
        return threadId.get();
    }
}
