package com.lvgo.unname;

import com.lvgo.unname.beas.ThreadA;
import com.lvgo.unname.util.ListUtil;

import java.util.List;

/**
 * //TODO 一句话描述此类作用
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/29 9:24
 */
public class UnnameExecutor<T> {

    private List<UnameThread> threads;
    private int threadTotal = 10;

    public UnnameExecutor(List<T> task, int threadTotal) {
        this.threadTotal = threadTotal;
        if (ListUtil.isEmpty(task)) {
            throw new IllegalArgumentException("task is null or size is zero");
        }
        for (int i = 0; i < this.threadTotal; i++) {
            threads.add(new ThreadA<T>(task.get(i)));
        }
    }
}
