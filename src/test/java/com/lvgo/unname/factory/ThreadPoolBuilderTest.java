package com.lvgo.unname.factory;

import com.lvgo.unname.ThreadPool;
import com.lvgo.unname.UnameThread;
import com.lvgo.unname.beas.ThreadA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolBuilderTest {

    @Test
    void build() {
        ThreadPool threadPool = ThreadPoolFactory.getThreadPool(ThreadPoolType.A);
        threadPool.execute(new UnameThread() {
            @Override
            public void run() {

                System.out.println("ThreadPoolBuilderTest.run");
            }
        });
    }
}