package com.dhair.light.locker.ui.abs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dhair.light.locker.ui.abs.presenter.MvpPresenter;
import com.dhair.light.locker.ui.abs.view.MvpView;

/**
 * Created by dengshengjin on 16/5/12.
 */
public abstract class AbsMvpActivity<P extends MvpPresenter> extends AbsActivity implements MvpView {
    private P mPresenter;

    @Override
    protected void onChildCreate(Bundle savedInstanceState) {
        super.onChildCreate(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter(this);
        }
        mPresenter.attachView(this);
        mPresenter.onCreate(getIntent().getExtras(), savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter.detachView(!isFinishing());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstance(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    protected P getPresenter() {
        return mPresenter;
    }

    @NonNull
    protected abstract P createPresenter(Context context);
}
