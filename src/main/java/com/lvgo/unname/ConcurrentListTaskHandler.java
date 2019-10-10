package com.lvgo.unname;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 并发列表任务处理器
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/10/10 9:19
 */
public abstract class ConcurrentListTaskHandler<T> {
    private final static Logger LOG = Logger.getLogger(ConcurrentListTaskHandler.class.getName());
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
     * 任务执行辅助参数
     */
    private Map<String, Object> mapParams;


    public ConcurrentListTaskHandler(List<T> taskList, Map<String, Object> mapParams) {
        this.taskList = taskList;
        lock = new ReentrantLock();
        this.mapParams = mapParams;
    }


    public ConcurrentListTaskHandler(List<T> taskList) {
        this(taskList, null);
    }

    /**
     * execute task
     *
     * @param t task element
     */
    protected abstract void run(T t);

    /**
     * execute task with param
     *
     * @param t         task element
     * @param mapParams task param
     */
    protected abstract void runWithParam(T t, Map<String, Object> mapParams);

    public void execute() {
        execute(ThreadPoolFactory.newThreadPool(), defaultThreadCount);
    }

    public void execute(ExecutorService executorService, int concurrentCount) {
        if (taskList == null) {
            LOG.log(Level.WARNING, "current taskList is null, ConcurrentListTaskHandler exit!!!");
            return;
        }
        this.count = taskList.size();
        for (int i = 0; i < concurrentCount; i++) {
            try {
                executorService.execute(new ConcurrentListTaskRun());
            } catch (RuntimeException r) {

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

    public void execute(int concurrentCount) {
        execute(ThreadPoolFactory.newThreadPool(), concurrentCount);
    }

    private class ConcurrentListTaskRun implements Runnable {
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
                    if (mapParams != null) {
                        ConcurrentListTaskHandler.this.runWithParam(t, mapParams);
                    } else {
                        ConcurrentListTaskHandler.this.run(t);
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}
