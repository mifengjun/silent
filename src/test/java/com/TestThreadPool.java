package com;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author lvgo
 * @version 1.0
 * @package com
 * @description: //TODO 一句话描述此类作用
 * @date 2019/9/24 11:42
 */
public class TestThreadPool {
    @Test
    public void test(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.execute(()-> System.out.println("123"));
    }
}
