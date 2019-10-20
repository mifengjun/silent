package org.lvgo.silent;

/**
 * 任务结束时调用方法接口
 *
 * @author lvgorice@gmail.com
 * @version 1.0
 */
@FunctionalInterface
public interface TaskOverRun {
    /**
     * at the end 结束时执行
     */
    void athend();
}
