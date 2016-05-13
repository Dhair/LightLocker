package com.dhair.light.locker.ui.abs.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.dhair.light.locker.ui.abs.view.MvpView;


public interface MvpPresenter<V extends MvpView> {
    void attachView(V view);

    void detachView(boolean retainInstance);

    void onCreate(Bundle extras, Bundle savedInstanceState);

    void onResume();

    void onPause();

    void onDestroy();

    void onSaveInstance(Bundle outState);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
