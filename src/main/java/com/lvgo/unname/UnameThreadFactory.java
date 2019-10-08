package com.lvgo.unname;

import java.util.concurrent.ThreadFactory;

/**
 * //TODO 一句话描述此类作用
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/10/8 10:50
 */
public class UnameThreadFactory implements ThreadFactory {
    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to
     * create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
