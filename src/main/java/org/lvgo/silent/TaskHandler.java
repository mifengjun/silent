package org.lvgo.silent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 任务处理器
 *
 * @param <T> 任务泛型
 * @author lvgorice@gmail.com
 * @version 1.0
 */
public abstract class TaskHandler<T> {

    private final Logger log = LoggerFactory.getLogger(TaskHandler.class);
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

    private TaskOverRun taskOverRun;

    public TaskHandler(List<T> taskList) {
        this.taskList = taskList;
        lock = new ReentrantLock();
    }

    public TaskHandler overRun(TaskOverRun taskOverRun) {
        this.taskOverRun = taskOverRun;
        return this;
    }

    public TaskHandler sync(boolean sync) {
        this.sync = sync;
        return this;
    }


    public void execute() {
        execute(SilentExecutor.getThreadPool(), defaultThreadCount);
    }

    public void execute(int concurrentCount) {
        execute(SilentExecutor.getThreadPool(), concurrentCount);
    }

    public void execute(Executor executor) {
        execute(executor, defaultThreadCount);
    }

    public void execute(Executor executor, int concurrentCount) {
        execute(executor, concurrentCount, new TaskHandler.TaskRun());
    }

    private void execute(Executor executor, int concurrentCount, Runnable runnable) {
        final long start = System.currentTimeMillis();
        syncControl = new CountDownLatch(concurrentCount);
        if (taskList == null) {
            log.error("current taskList is null, ConcurrentTaskHandler exit!!!");
            return;
        }
        this.count = taskList.size();
        for (int i = 0; i < concurrentCount; i++) {
            try {
                executor.execute(runnable);
            } catch (RuntimeException r) {
                log.error("executor error , sleep 10 seconds");
                try {
                    Thread.sleep(1000L * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 同异步处理
        if (sync) {
            try {
                syncControl.await();
                printCost(start);
                if (taskOverRun != null) {
                    taskOverRun.overRun();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            new Thread(() -> {
                try {
                    syncControl.await();
                    printCost(start);
                    // set task over run this code block
                    if (taskOverRun != null && !sync) {
                        taskOverRun.overRun();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }


    private void printCost(long start) {
        long costTime = (System.currentTimeMillis() - start) / 1000;
        long hour = costTime / 60;
        long minute = hour % 60;
        long second = costTime % 60;
        String cost = "";
        if (hour != 0) {
            cost += hour + "h";
        }
        if (minute != 0) {
            cost += minute + "m";
        }
        cost += second + "s";
        log.info("current tasks successfully! total:{}, cost:{}", count, cost);
    }


    private T nextTask() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (index < count) ? taskList.get(index) : null;
        } finally {
            index += 1;
            lock.unlock();
        }
    }

    /**
     * custom service function implement
     *
     * @param t 任务数据
     */
    public abstract void run(T t);

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
                    TaskHandler.this.run(t);
                }
            } catch (Exception e) {
                log.error("task error", e);
            } finally {
                syncControl.countDown();
            }
        }
    }

}
