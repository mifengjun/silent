package org.lvgo.silent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发任务线程池工厂
 *
 * @author lvgorice@gmail.com
 * @version 1.0
 * @date 2019/10/8 10:59
 */
class SilentThreadSupport {

    static ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(
                50,
                200,
                0, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new SilentThreadFactory());
    }

    private static final class SilentThreadFactory implements ThreadFactory {

        private final ThreadGroup group;
        private final AtomicInteger index = new AtomicInteger(1);

        private SilentThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup()
                    : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new SilentThread(group, r,
                    "Thread-" + index.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    }

    private static final class SilentThread extends Thread {

        SilentThread(ThreadGroup group, Runnable r, String s) {
            super(group, r, s);
        }

        @Override
        public String toString() {
            ThreadGroup group = getThreadGroup();
            if (group != null) {
                return "SilentTask[" + getName() + "," + getPriority() + "," +
                        group.getName() + "]";
            } else {
                return "SilentTask[" + getName() + "," + getPriority() + "," +
                        "" + "]";
            }
        }
    }
}
