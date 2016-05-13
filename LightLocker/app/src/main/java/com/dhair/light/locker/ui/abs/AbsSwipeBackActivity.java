package com.dhair.light.locker.ui.abs;

import android.os.Bundle;
import android.view.View;

import com.android.ui.swipeback.ActivityTranslucentUtil;
import com.android.ui.swipeback.SwipeBackActivityBase;
import com.android.ui.swipeback.SwipeBackActivityHelper;
import com.android.ui.swipeback.SwipeBackLayout;
import com.dhair.light.locker.ui.abs.presenter.MvpPresenter;
import com.dhair.light.locker.ui.abs.view.MvpView;

/**
 * Created by dengshengjin on 16/5/12.
 */
public abstract class AbsSwipeBackActivity<P extends MvpPresenter> extends AbsMvpActivity<P> implements MvpView, SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onPreSetContentView() {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        ActivityTranslucentUtil.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
