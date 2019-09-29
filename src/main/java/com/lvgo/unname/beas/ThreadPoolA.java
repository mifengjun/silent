package com.lvgo.unname.beas;

import com.lvgo.unname.AbstractThreadPool;
import com.lvgo.unname.UnameThread;

/**
 * 线程池A
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/24 16:53
 */
public class ThreadPoolA extends AbstractThreadPool {
    /**
     * UnameThread 线程执行方法
     *
     * @param unameThread untitled 线程
     */
    @Override
    public void execute(UnameThread unameThread) {
        System.out.println("ThreadPoolA.execute");
        unameThread.run();
    }
}
