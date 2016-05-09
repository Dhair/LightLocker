package com.dhair.light.locker.application;

import android.app.Application;
import android.content.Context;

import com.android.common.util.ToastUtils;

/**
 * Creator: dengshengjin on 16/5/9 11:48
 * Email: deng.shengjin@zuimeia.com
 */
public class LightLockerApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(getApplicationContext());
    }
}
