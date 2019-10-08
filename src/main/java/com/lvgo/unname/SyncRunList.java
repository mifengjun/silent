package com.lvgo.unname;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步数组多线程处理抽象类
 */
public abstract class SyncRunList<E> {

    /**
     * 所有存取监护锁
     */
    private final ReentrantLock lock;
    /**
     * 辅助参数
     */
    private Map<String, Object> args;
    /**
     * 元素数组
     */
    private List<E> tasks;
    /**
     * 下一个待执行元素索引
     */
    private int takeIdx;

    protected SyncRunList(List<E> tasks) {
        this(tasks, null);
    }

    /**
     * 构造函数
     *
     * @param tasks 元素数组
     * @param args  辅助参数
     */
    protected SyncRunList(List<E> tasks, Map<String, Object> args) {
        this.lock = new ReentrantLock(false);
        this.tasks = tasks;
        this.args = args;
    }

    /**
     * 获取下一个待执行元素
     *
     * @return
     */
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (takeIdx >= tasks.size()) {
                return null;
            }
            return tasks.get(takeIdx++);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除数组中指定元素
     *
     * @param o
     * @return
     */
    public boolean remove(E o) {
        if (o == null) {
            return false;
        }
        final List<E> ls = this.tasks;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int idx = ls.indexOf(o);
            ls.remove(idx);
            if (idx < takeIdx) {
                takeIdx--;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 单元素任务执行
     *
     * @param task 元素
     */
    protected abstract void taskRun(E task);

    /**
     * 单个任务线程执行结束后处理
     *
     * @param syncRunnable
     */
    protected void afterRun(SyncRunnable syncRunnable) {
    }

    /**
     * 新启执行线程
     *
     * @return 执行线程
     */
    private SyncRunnable syncRunnable() {
        return new SyncRunnable();
    }

    /**
     * 在线程池环境下遍历数组元素，执行给定的批量任务
     *
     * @param executor  执行容器
     * @param taskCount 任务启动总数
     */
    public void execute(Executor executor, int taskCount) {
        for (int r = 0; r < taskCount; ) {
            try {
                executor.execute(syncRunnable());
                r++;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

    public List<E> getTasks() {
        return tasks;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    /**
     * 任务执行线程
     */
    protected class SyncRunnable implements Runnable {
        @Override
        public void run() {
            try {
                E item;
                while ((item = poll()) != null) {
                    taskRun(item);
                }
            } finally {
                afterRun(this);
            }
        }
    }

}
