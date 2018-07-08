package com.zavier.lenglish.common.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
    private static ExecutorService executorService;

    static {
        int processors = Runtime.getRuntime().availableProcessors();
        executorService = new ThreadPoolExecutor(processors, 2 * processors, 10, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(30), (r) -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
    }

    public static void run(Runnable command) {
        executorService.execute(command);
    }

    public static <V> Future<V> submit(Callable<V> task) {
        return executorService.submit(task);
    }

    public static <V> List<Future<V>> invokeAll(List<Callable<V>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks);
    }
}
