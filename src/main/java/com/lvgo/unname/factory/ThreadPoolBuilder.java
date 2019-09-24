package com.lvgo.unname.factory;

import com.lvgo.unname.ThreadPool;

import java.util.function.Supplier;

/**
 * 线程池构建器
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/24 16:24
 */
public interface ThreadPoolBuilder {

    /**
     * 初始构建
     *
     * @param threadPoolType 线程池类型
     * @param supplier       供应线程池
     */
    void build(ThreadPoolType threadPoolType, Supplier<ThreadPool> supplier);
}
