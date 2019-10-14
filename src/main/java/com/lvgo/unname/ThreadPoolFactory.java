package com.lvgo.unname;

import java.util.concurrent.*;

/**
 * 并发任务线程池工厂
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/10/8 10:59
 */
class ThreadPoolFactory {

    static ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(
                50,
                200,
                0, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                });
    }
}
