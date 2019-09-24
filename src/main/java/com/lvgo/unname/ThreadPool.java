package com.lvgo.unname;

/**
 * 线程池接口, 支持原生和自定义两种执行策略
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/24 17:00
 */
public interface ThreadPool {
    /**
     * 原生线程执行方法
     *
     * @param runnable jdk 原生线程
     */
    void execute(Runnable runnable);

    /**
     * UnameThread 线程执行方法
     *
     * @param unameThread untitled 线程
     */
    void execute(UnameThread unameThread);
}