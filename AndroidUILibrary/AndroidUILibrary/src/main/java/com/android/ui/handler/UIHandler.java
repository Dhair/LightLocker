package com.android.ui.handler;

import android.os.Handler;
import android.os.Looper;

import com.dhair.light.locker.exception.CustomException;

/**
 * Creator: dengshengjin on 16/5/9 22:45
 * Email: deng.shengjin@zuimeia.com
 */
public class UIHandler {

    private static Handler mUIHandler = new Handler(Looper.getMainLooper());

    public static void post(Runnable runnable) {
        if (runnable == null) {
            throw new CustomException("runnable can't be null");
        }
        mUIHandler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        if (runnable == null) {
            throw new CustomException("runnable can't be null");
        }
        mUIHandler.postDelayed(runnable, delayMillis);
    }

}
