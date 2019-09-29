package com.lvgo.unname.core;

import org.junit.jupiter.api.Test;

class ExecutorTest {

    @Test
    void execute() {
        new Executor<String>() {
            @Override
            public void execute(String s) {
                System.out.println("s = " + s);
            }
        };
    }
}