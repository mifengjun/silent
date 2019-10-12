package com.lvgo.unname;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

/**
 * 并发列表任务处理器
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/10/10 9:19
 */
public abstract class ConcurrentTaskWithParamHandler<T> extends ConcurrentTaskHandler {
    /**
     * 任务执行辅助参数
     */
    private Map<String, Object> mapParams;


    public ConcurrentTaskWithParamHandler(List<T> taskList, Map<String, Object> mapParams) {
        super(taskList);
        this.mapParams = mapParams;
    }

    @Override
    protected void run(Object o) {

    }

    @Override
    public void execute(ExecutorService executorService, int concurrentCount) {
        if (taskList == null) {
            LOG.log(Level.WARNING, "current taskList is null, ConcurrentTaskHandler exit!!!");
            return;
        }
        this.count = taskList.size();
        for (int i = 0; i < concurrentCount; i++) {
            try {
                executorService.execute(new ConcurrentListTaskWithParamRun());
            } catch (RuntimeException r) {

            }
        }
    }

    /**
     * execute task with param
     *
     * @param t         task element
     * @param mapParams task param
     */
    protected abstract void runWithParam(T t, Map<String, Object> mapParams);

    private class ConcurrentListTaskWithParamRun implements Runnable {
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
                while ((t = (T) nextTask()) != null) {
                    ConcurrentTaskWithParamHandler.this.runWithParam(t, mapParams);
                }
            } catch (Exception e) {

            }
        }
    }
}
