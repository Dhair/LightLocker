package com.dhair.light.locker.component.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Creator: dengshengjin on 16/5/9 23:16
 * Email: deng.shengjin@zuimeia.com
 */
public class FixedThreadExecutor {
    private Executor mExecutor;
    private static FixedThreadExecutor mInstance;

    private FixedThreadExecutor(int nThreads) {
        mExecutor = Executors.newFixedThreadPool(nThreads);
    }

    public static FixedThreadExecutor getInstance() {
        return getInstance(3);
    }

    public static FixedThreadExecutor getInstance(int nThreads) {
        if (mInstance == null) {
            synchronized (FixedThreadExecutor.class) {
                if (mInstance == null) {
                    mInstance = new FixedThreadExecutor(nThreads);
                }
            }
        }
        return mInstance;
    }


    public void submit(Runnable command) {
        mExecutor.execute(command);
    }
}
