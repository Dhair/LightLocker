package com.dhair.light.locker.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.common.util.ToastUtils;
import com.dhair.light.locker.R;
import com.dhair.light.locker.component.thread.CustomHandlerThread;
import com.dhair.light.locker.data.local.ServiceConfigManager;
import com.dhair.light.locker.ui.abs.AbsSwipeBackActivity;
import com.dhair.light.locker.ui.main.presenter.MainPresenter;
import com.library.processutil.AndroidProcesses;

public class MainActivity extends AbsSwipeBackActivity<MainPresenter> {


    @Override
    protected void initData() {

    }

    @Override
    protected void initWidgets() {
        ToastUtils.showToast("sdfsdfdsf");
        ServiceConfigManager.getInstance(getApplicationContext()).updateCurrentVersion();
        Log.e("", "initWidgets " + ServiceConfigManager.getInstance(getApplicationContext()).getCurrentVersion());
        AndroidProcesses.isMyProcessInTheForeground();
        new CustomHandlerThread() {
            @Override
            protected void onLooperInterAsync() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void onLooperInter() {

            }
        }.start();
    }

    @Override
    protected void initWidgetsActions() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected MainPresenter createPresenter(Context context) {
        return new MainPresenter(context);
    }
}
