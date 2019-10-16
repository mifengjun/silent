package org.lvgo.silent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TaskHandlerTest {
    private final Logger log = LoggerFactory.getLogger(TaskHandlerTest.class);
    private List<String> testData = new ArrayList<>();

    @BeforeEach
    public void initParam() {
        for (int i = 1; i <= 10; i++) {
            testData.add(i + "");
        }
    }

    @Test
    void sync() {
        new TaskHandler<String>(testData) {
            @Override
            public void run(String s) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("第" + s + "个任务" + Thread.currentThread());
            }
        }.sync(true).overRun(() -> {
            log.debug("我所有的任务执行结束了");
        }).execute(1);

        log.error("我出现在最后代表任务为同步进行， 否则为异步");


        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void execute() {
    }

    @Test
    void execute1() {
    }

    @Test
    void execute2() {
    }

    @Test
    void execute3() {
    }
}