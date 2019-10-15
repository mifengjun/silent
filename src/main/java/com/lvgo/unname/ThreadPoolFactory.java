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

    public static void main(String[] args) {

        long s = 6001000 / 1000;
        long second = s % 60;
        long hour = s / 60;
        long minute = hour % 60;

        System.out.println(hour / 60 + "时" + minute + "分" + second + "秒");


    }

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
