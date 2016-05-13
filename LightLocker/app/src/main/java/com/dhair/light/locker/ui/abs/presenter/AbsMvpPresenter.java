package com.dhair.light.locker.ui.abs.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.dhair.light.locker.ui.abs.view.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class AbsMvpPresenter<V extends MvpView> implements MvpPresenter<V> {

    protected V mMvpView;
    private Handler mHandler;
    private Context mContext;
    private CompositeSubscription mCompositeSubscription;

    public AbsMvpPresenter(Context context) {
        mContext = context;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(V view) {
        mMvpView = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (!retainInstance) {
            mMvpView = null;
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription.clear();
    }

    @Override
    public void onSaveInstance(Bundle outState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    protected Context getContext() {
        return mContext;
    }

    protected boolean hasMvpView() {
        return mMvpView != null;
    }

    protected V getMvpView() {
        return mMvpView;
    }

    protected Handler getHandler() {
        if (mHandler == null) {
            synchronized (this) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }

        return mHandler;
    }

    protected void runOnUiThread(Runnable runnable) {
        getHandler().post(runnable);
    }

    protected void bind(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    protected void unBind(Subscription subscription) {
        mCompositeSubscription.remove(subscription);
    }
}
