package com.lvgo.unname;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * //TODO 一句话描述此类作用
 *
 * @author lvgo
 * @version 1.0
 * @date 2019/9/29 11:12
 */
public class UntitledTest {


    List<String> testStrArray = new ArrayList<>();
    BlockingQueue<String> tq = new ArrayBlockingQueue<>(100);
    @BeforeEach
    public void be() {
        System.out.println("开始初始化数据");
        for (int i = 1; i <= 100; i++) {
            testStrArray.add("第" + i + "行");
        }
        for (int i = 1; i <= 100; i++) {
            tq.offer("哦们木木木");
        }

        System.out.println("初始化数据完成");
    }


    @Test
    public void test() throws InterruptedException {
        for (int i = 100; i >= 1; i--) {
            int finalI = i;
            new Thread(() -> {
                testStrArray.remove(0);
                System.out.println("我是第" + finalI + "个, 我的线程信息是" + Thread.currentThread() + ", 我看到的数量是" + testStrArray.size());
            }).start();
        }
        Thread.currentThread().join(10000);
    }


    @Test
    public void test1() throws InterruptedException {
        for (int i = 100; i >= 1; i--) {
            int finalI = i;
            new Thread(() -> {
                try {
                    tq.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是第" + finalI + "个, 我的线程信息是" + Thread.currentThread() + ", 我看到的数量是" + tq.size());

            }).start();
        }
        Thread.currentThread().join(10000);
    }
}
