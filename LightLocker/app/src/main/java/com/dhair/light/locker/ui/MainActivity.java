package com.dhair.light.locker.ui;

import com.android.common.util.ToastUtils;
import com.dhair.light.locker.R;
import com.dhair.light.locker.ui.abs.AbsActivity;
import com.dhair.light.locker.utils.CustomHandlerThread;
import com.library.processutil.AndroidProcesses;

public class MainActivity extends AbsActivity {


    @Override
    protected void initData() {

    }

    @Override
    protected void initWidgets() {
        ToastUtils.showToast("sdfsdfdsf");
        AndroidProcesses.isMyProcessInTheForeground();
        new CustomHandlerThread() {
            @Override
            protected void onLooperInterAsync() {

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
}
