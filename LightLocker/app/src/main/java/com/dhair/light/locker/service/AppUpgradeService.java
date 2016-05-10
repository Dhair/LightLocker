package com.dhair.light.locker.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.dhair.light.locker.component.receiver.AbsReceiver;

/**
 * Created by i on 2016/5/10.
 */
public class AppUpgradeService {
    private AppUpgradeBroadcastReceiver mAppUpgradeBroadcastReceiver;
    private Context mContext;
    public static final String APP_UPGRADE_ACTION = AppUpgradeService.class.getSimpleName();

    public AppUpgradeService(Context context) {
        mContext = context;
    }

    public void registerReceiver() {
        if (mAppUpgradeBroadcastReceiver == null) {
            mAppUpgradeBroadcastReceiver = new AppUpgradeBroadcastReceiver();
        }
        IntentFilter finishIntent = new IntentFilter();
        finishIntent.addAction(APP_UPGRADE_ACTION);
        mContext.registerReceiver(mAppUpgradeBroadcastReceiver, finishIntent);
    }

    public void unregisterReceiver() {
        if (mAppUpgradeBroadcastReceiver != null) {
            mContext.unregisterReceiver(mAppUpgradeBroadcastReceiver);
        }
    }

    public void sendFinishBroadcast(Bundle bundle) {
        Intent intent = new Intent(APP_UPGRADE_ACTION);
        intent.setPackage(mContext.getPackageName());
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);
    }

    final static class AppUpgradeBroadcastReceiver extends AbsReceiver {

        @Override
        protected void onReceiveInter(Context context, Intent intent) {

        }

        @Override
        protected void onReceiveInterAsync(Context context, Intent intent) {

        }
    }
}
