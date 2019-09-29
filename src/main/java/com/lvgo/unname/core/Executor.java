package com.lvgo.unname.core;

/**
 * //TODO 一句话描述此类作用
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/29 9:56
 */
@FunctionalInterface
public interface Executor<T> {
    void execute(T t);
}
