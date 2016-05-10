package com.dhair.light.locker.component.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dhair.light.locker.component.thread.SingleThreadExecutor;

/**
 * Created by i on 2016/5/10.
 */
public abstract class AbsReceiver extends BroadcastReceiver {
    @Override
    public final void onReceive(final Context context, final Intent intent) {
        if (!isValid()) {
            return;
        }
        onReceiveInter(context, intent);
        SingleThreadExecutor.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                onReceiveInterAsync(context, intent);
            }
        });
    }

    protected boolean isValid() {
        return true;
    }

    protected abstract void onReceiveInter(Context context, Intent intent);

    protected abstract void onReceiveInterAsync(Context context, Intent intent);
}
