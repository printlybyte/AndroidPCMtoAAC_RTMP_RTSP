package com.lgd.androidpcmtoaac_rtmp_rtsp.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuguodong on 2018/5/12.
 */

public final  class AppOperator {
    private static ExecutorService EXECUTORS_INSTANCE;
    private static int ThreadNumber = 2;

    public static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (AppOperator.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() > 0 ?
                            Runtime.getRuntime().availableProcessors() : ThreadNumber);
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }
    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }
    public static void shutDown(){
        EXECUTORS_INSTANCE.shutdown();
    }
}
