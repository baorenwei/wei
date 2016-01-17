package com.example.base.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/1/8.
 */
public class ThreadPollUtils {

    public static void addCachedThreadPoll(Runnable thread){
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(thread);
    }
}
