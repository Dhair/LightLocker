package com.dhair.light.locker.component.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Creator: dengshengjin on 16/5/9 23:16
 * Email: deng.shengjin@zuimeia.com
 */
public class SingleThreadExecutor {
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    private static class SingleThreadExecutorHolder {
        private static SingleThreadExecutor mSingleThreadExecutor = new SingleThreadExecutor();
    }

    public static SingleThreadExecutor getInstance() {
        return SingleThreadExecutorHolder.mSingleThreadExecutor;
    }

    public void submit(Runnable command) {
        mExecutor.execute(command);
    }
}
