package com.dhair.light.locker.ui.home.presenter;

import android.content.Context;
import android.os.Bundle;

import com.dhair.light.locker.ui.abs.presenter.AbsMvpPresenter;
import com.dhair.light.locker.ui.home.view.HomeMvpView;

/**
 * Created by dengshengjin on 16/5/13.
 */
public class HomePresenter extends AbsMvpPresenter<HomeMvpView> {

    public HomePresenter(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle extras, Bundle savedInstanceState) {

    }


}