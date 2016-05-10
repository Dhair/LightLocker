package com.dhair.light.locker.component.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Creator: dengshengjin on 16/5/9 23:16
 * Email: deng.shengjin@zuimeia.com
 */
public class CachedThreadExecutor {
    private Executor mExecutor = Executors.newCachedThreadPool();

    private static class SingleThreadExecutorHolder {
        private static CachedThreadExecutor mSingleThreadExecutor = new CachedThreadExecutor();
    }

    public static CachedThreadExecutor getInstance() {
        return SingleThreadExecutorHolder.mSingleThreadExecutor;
    }

    public void submit(Runnable command) {
        mExecutor.execute(command);
    }
}
