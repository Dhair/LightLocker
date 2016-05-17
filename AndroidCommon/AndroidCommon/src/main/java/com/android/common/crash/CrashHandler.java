package com.android.common.crash;

import android.content.Context;

/**
 * Created by dengshengjin on 16/5/14.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private CrashReporter mCrashReporter;

    private CrashHandler(Context context) {
        mContext = context;
    }

    public void setCrashReporter(CrashReporter crashReporter) {
        mCrashReporter = crashReporter;
    }

    public static void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (mCrashReporter != null) {
            mCrashReporter.report(mContext);
        }
    }
}