package com.dhair.light.locker.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.common.util.ToastUtils;
import com.dhair.light.locker.R;
import com.dhair.light.locker.component.thread.CustomHandlerThread;
import com.dhair.light.locker.data.local.ServiceConfigManager;
import com.dhair.light.locker.ui.abs.AbsMvpActivity;
import com.dhair.light.locker.ui.home.presenter.HomePresenter;
import com.library.processutil.AndroidProcesses;

public class HomeActivity extends AbsMvpActivity<HomePresenter> {

    @Override
    protected void onPreSetContentView() {
        super.onPreSetContentView();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

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
        return R.layout.activity_home;
    }

    @NonNull
    @Override
    protected HomePresenter createPresenter(Context context) {
        return new HomePresenter(context);
    }
}
