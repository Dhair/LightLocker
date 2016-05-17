package com.dhair.light.locker.application;

import android.app.Application;
import android.content.Context;

import com.android.common.crash.CrashHandler;
import com.android.common.util.ToastUtils;
import com.android.process.library.processutil.AndroidProcesses;
import com.dhair.light.locker.BuildConfig;
import com.dhair.light.locker.component.thread.SingleThreadExecutor;
import com.dhair.light.locker.utils.channel.ChannelUtils;
import com.dhair.light.locker.utils.process.RuntimeUtils;
import com.dhair.light.locker.utils.version.VersionUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Creator: dengshengjin on 16/5/9 11:48
 * Email: deng.shengjin@zuimeia.com
 */
public class LightLockerApplication extends Application {
    private static LightLockerApplication mApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        ToastUtils.init(getContext());
        if (BuildConfig.DEBUG) {
            LeakCanary.install(LightLockerApplication.this);
        }
        CrashHandler.init(getContext());
        SingleThreadExecutor.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                ChannelUtils.initChannel(getContext());
                initMultiProcess();
                if (RuntimeUtils.isServiceProcess()) {
                    onServiceProcessEvent();
                } else {
                    onUIProcessEvent();
                }
            }
        });
    }

    private void initMultiProcess() {
        String processName = AndroidProcesses.getCurrentProcessName(getContext());
        RuntimeUtils.updateProcess(processName);
    }

    private void onUIProcessEvent() {
        VersionUtils.updateVersion(getContext());
    }

    private void onServiceProcessEvent() {

    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }
}
