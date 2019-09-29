package com.lvgo.unname;

/**
 * 线程池接口
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/24 15:31
 */
public abstract class AbstractThreadPool implements ThreadPool {

    /**
     * 原生线程执行方法
     *
     * @param runnable jdk 原生线程
     */
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}