package com.dhair.light.locker.component.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;


/**
 * Creator: dengshengjin on 16/5/9 22:31
 * Email: deng.shengjin@zuimeia.com
 */
public abstract class CustomHandlerThread extends HandlerThread {
    private final static String TAG = CustomHandlerThread.class.getSimpleName();
    private Handler mHandler;

    public CustomHandlerThread() {
        super(TAG, Process.THREAD_PRIORITY_DEFAULT);
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    @Override
    protected final void onLooperPrepared() {
        onLooperInterAsync();
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                onLooperInter();
            }
        });
    }

    protected abstract void onLooperInterAsync();

    protected abstract void onLooperInter();
}
