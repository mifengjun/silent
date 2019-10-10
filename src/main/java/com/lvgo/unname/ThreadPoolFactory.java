package com.lvgo.unname;

import java.util.concurrent.*;

/**
 * //TODO 一句话描述此类作用
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/10/8 10:59
 */
public class ThreadPoolFactory {

    public static ExecutorService newThreadPool() {
        return newThreadPool(new UnameThreadFactory());
    }

    public static ExecutorService newThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(50, 200, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                threadFactory);
    }

}
