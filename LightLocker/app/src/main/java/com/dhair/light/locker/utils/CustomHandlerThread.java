package com.dhair.light.locker.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.widget.Toast;

import com.dhair.light.locker.application.LightLockerApplication;

/**
 * Creator: dengshengjin on 16/5/9 22:31
 * Email: deng.shengjin@zuimeia.com
 */
public class CustomHandlerThread extends HandlerThread {
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
    protected void onLooperPrepared() {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LightLockerApplication.getContext(), "CustomHandleThread", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }
}
