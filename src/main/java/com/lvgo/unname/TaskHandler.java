package com.lvgo.unname;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TaskHandler<T> {
    private final static Logger LOG = Logger.getLogger(TaskHandler.class.getName());
    /**
     * 默认启动线程数量
     */
    private int defaultThreadCount = 50;
    /**
     * 当前任务数
     */
    private int count;
    /**
     * 任务执行索引
     */
    private int index = 0;
    /**
     * 数据读取锁
     */
    private ReentrantLock lock;
    /**
     * 待处理任务列表
     */
    private List<T> taskList;
    /**
     * 任务处理同步锁
     */
    private CountDownLatch syncControl;
    /**
     * 是否同步
     */
    private boolean sync;
    /**
     * 任务执行辅助参数
     */
    private Map<String, Object> param;

    public TaskHandler(List<T> taskList) {
        this.taskList = taskList;
        lock = new ReentrantLock();
    }

    public TaskHandler sync(boolean sync) {
        this.sync = sync;
        return this;
    }


    public void execute() {
        execute(ThreadPoolFactory.getThreadPool(), defaultThreadCount);
    }

    public void execute(int concurrentCount) {
        execute(ThreadPoolFactory.getThreadPool(), concurrentCount);
    }

    public void execute(Executor executor) {
        execute(executor, defaultThreadCount);
    }

    public void execute(Executor executor, int concurrentCount) {
        execute(executor, concurrentCount, new TaskHandler.TaskRun());
    }

    private void execute(Executor executor, int concurrentCount, Runnable runnable) {
        syncControl = new CountDownLatch(concurrentCount);
        if (taskList == null) {
            LOG.log(Level.WARNING, "current taskList is null, ConcurrentTaskHandler exit!!!");
            return;
        }
        this.count = taskList.size();
        for (int i = 0; i < concurrentCount; i++) {
            try {
                executor.execute(runnable);
            } catch (RuntimeException r) {
                LOG.log(Level.WARNING, "executor error , sleep 10 seconds");
                try {
                    Thread.sleep(1000L * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (sync) {
            try {
                syncControl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private T nextTask() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            index += 1;
            return (index < count) ? taskList.get(index) : null;
        } finally {
            lock.unlock();
        }
    }


    /**
     * custom service function implement
     *
     * @param t 任务数据
     */
    public abstract void runTask(T t);

    private class TaskRun implements Runnable {
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            T t;
            try {
                while ((t = nextTask()) != null) {
                    runTask(t);
                }
            } catch (Exception e) {
                LOG.warning("task error . " + Thread.currentThread());
            } finally {
                syncControl.countDown();
            }
        }
    }

}
