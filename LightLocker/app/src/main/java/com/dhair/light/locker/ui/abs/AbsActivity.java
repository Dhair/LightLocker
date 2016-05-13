package com.dhair.light.locker.ui.abs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.common.util.DisplayUtils;
import com.dhair.light.locker.R;
import com.dhair.light.locker.component.service.ActivityFinishService;
import com.dhair.light.locker.component.service.AppUpgradeService;
import com.dhair.light.locker.exception.CustomException;
import com.dhair.light.locker.ui.abs.spec.ISystemStatusBar;

import butterknife.ButterKnife;

/**
 * Created by i on 2016/5/10.
 */
public abstract class AbsActivity extends AppCompatActivity implements ISystemStatusBar {
    private Context mContext;
    private ActivityFinishService mActivityFinishService;
    private AppUpgradeService mAppUpgradeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        onPreSetContentView();
        super.setContentView(R.layout.activity_abs);
        initAbsData();
        initAbsWidgets();
        ButterKnife.bind(this);
        onChildCreate(savedInstanceState);
        initActionBarActions();
        initData();
        initWidgets();
        initWidgetsActions();
    }

    private void initAbsData() {
        mActivityFinishService = new ActivityFinishService(AbsActivity.this);
        mActivityFinishService.registerReceiver();
        mAppUpgradeService = new AppUpgradeService(AbsActivity.this);
        mAppUpgradeService.registerReceiver();
    }

    private void initAbsWidgets() {
        updateStatusBarHeightV19();
        updateActionBarColorV19();
        updateStatusBarColorV21();
        updateAbsContentView();
    }

    protected void onChildCreate(Bundle savedInstanceState) {

    }

    @Deprecated
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        throw new CustomException("please don't call this method directly");
    }

    @Deprecated
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        throw new CustomException("please don't call this method directly");
    }

    @Deprecated
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        throw new CustomException("please don't call this method directly");
    }

    protected void onPreSetContentView() {

    }

    protected void initActionBarActions() {

    }

    private void updateStatusBarHeightV19() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && !DisplayUtils.isFullScreen(this)) {
            ViewGroup mStatusBarBox = (ViewGroup) findViewById(R.id.status_bar_box);
            if (mStatusBarBox != null) {
                ViewGroup.LayoutParams lp = mStatusBarBox.getLayoutParams();
                lp.height = DisplayUtils.getStatusBarHeight(getApplicationContext());
                mStatusBarBox.requestLayout();
                if (getStatusBarColor() > 0) {
                    mStatusBarBox.setBackgroundResource(getStatusBarColor());
                } else {
                    mStatusBarBox.setBackgroundResource(R.color.colorPrimaryDark);
                }
            }

        }
    }

    private void updateActionBarColorV19() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (getSupportActionBar() != null) {
                if (getStatusBarColor() > 0) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), getStatusBarColor())));
                } else {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                }
            }
        }
    }

    private void updateStatusBarColorV21() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getStatusBarColor() > 0) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getStatusBarColor());
        }
    }

    private void updateAbsContentView() {
        FrameLayout mAbsBox = (FrameLayout) findViewById(R.id.abs_box);
        if (mAbsBox != null) {
            LayoutInflater mInflater = LayoutInflater.from(AbsActivity.this);
            int contentView = getContentView();
            if (contentView <= 0) {
                throw new NullPointerException("Please return the contentView method");
            }
            mAbsBox.addView(mInflater.inflate(contentView, mAbsBox, false));
        }
    }

    public Context getContext() {
        return mContext;
    }

    protected abstract void initData();

    protected abstract void initWidgets();

    protected abstract void initWidgetsActions();

    @Override
    public int getStatusBarColor() {
        return 0;
    }

    @LayoutRes
    protected abstract int getContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mActivityFinishService != null) {
            mActivityFinishService.unregisterReceiver();
        }
        if (mAppUpgradeService != null) {
            mAppUpgradeService.unregisterReceiver();
        }
    }
}
