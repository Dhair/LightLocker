package com.dhair.light.locker.component.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.dhair.light.locker.component.receiver.AbsReceiver;

import java.lang.ref.WeakReference;

/**
 * Created by i on 2016/5/10.
 */
public class ActivityFinishService {
    private WeakReference<Activity> mActivityWeakReference;
    private FinishBroadcastReceiver mFinishBroadcastReceiver;
    private Context mContext;
    public static final String ACTIVITY_FINISH_ACTION = ActivityFinishService.class.getSimpleName();

    public ActivityFinishService(Activity mActivity) {
        mContext = mActivity.getApplicationContext();
        mActivityWeakReference = new WeakReference<>(mActivity);
    }

    public void registerReceiver() {
        if (mFinishBroadcastReceiver == null) {
            mFinishBroadcastReceiver = new FinishBroadcastReceiver(mActivityWeakReference);
        }
        IntentFilter finishIntent = new IntentFilter();
        finishIntent.addAction(ACTIVITY_FINISH_ACTION);
        mContext.registerReceiver(mFinishBroadcastReceiver, finishIntent);
    }

    public void unregisterReceiver() {
        if (mFinishBroadcastReceiver != null) {
            mContext.unregisterReceiver(mFinishBroadcastReceiver);
        }
    }

    public void sendFinishBroadcast() {
        Intent intent = new Intent(ACTIVITY_FINISH_ACTION);
        intent.setPackage(mContext.getPackageName());
        mContext.sendBroadcast(intent);
    }

    final static class FinishBroadcastReceiver extends AbsReceiver {
        private WeakReference<Activity> mActivityWeakReference;

        public FinishBroadcastReceiver(WeakReference<Activity> activityWeakReference) {
            mActivityWeakReference = activityWeakReference;
        }

        @Override
        protected void onReceiveInter(Context context, Intent intent) {
            if (intent.getAction().equals(ActivityFinishService.ACTIVITY_FINISH_ACTION)) {
                Activity activity = mActivityWeakReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
        }

        @Override
        protected void onReceiveInterAsync(Context context, Intent intent) {

        }
    }
}
