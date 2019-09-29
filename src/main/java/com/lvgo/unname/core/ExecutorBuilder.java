package com.lvgo.unname.core;

import java.util.List;

/**
 * //TODO 一句话描述此类作用
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/29 11:13
 */
public class ExecutorBuilder<E> {

    private boolean sync = false;

    public  ExecutorBuilder build(E e) {
        return new ExecutorBuilder<E>();
    }

    public ExecutorBuilder<E> sync(boolean sync) {
        this.sync = sync;
        return this;
    }

    public ExecutorBuilder<E> task(List<E> tasks) {
        return this;
    }
}
