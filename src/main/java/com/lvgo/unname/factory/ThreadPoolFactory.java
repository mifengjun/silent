package com.lvgo.unname.factory;

import com.lvgo.unname.ThreadPool;
import com.lvgo.unname.beas.ThreadPoolA;
import com.lvgo.unname.beas.ThreadPoolB;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 线程池工厂, 用于创建线程池
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/24 15:24
 */
public interface ThreadPoolFactory {

    Consumer<ThreadPoolBuilder> CONSUMER = builder -> {
        builder.build(ThreadPoolType.A, ThreadPoolA::new);
        builder.build(ThreadPoolType.B, ThreadPoolB::new);
    };



    Map<ThreadPoolType, Supplier<ThreadPool>> THREAD_POOLS = new HashMap<>(2);

    /**
     * 工厂创建线程池方法
     *
     * @param threadPoolType 线程池类型
     * @return 线程池
     */
    static ThreadPool getThreadPool(ThreadPoolType threadPoolType) {
        CONSUMER.accept(THREAD_POOLS::put);
        return THREAD_POOLS.get(threadPoolType).get();
    }
}
