package com.dhair.light.locker.ui.main.presenter;

import android.content.Context;
import android.os.Bundle;

import com.dhair.light.locker.ui.abs.presenter.AbsMvpPresenter;
import com.dhair.light.locker.ui.main.view.MainMvpView;

/**
 * Created by dengshengjin on 16/5/13.
 */
public class MainPresenter extends AbsMvpPresenter<MainMvpView> {

    public MainPresenter(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle extras, Bundle savedInstanceState) {

    }


}