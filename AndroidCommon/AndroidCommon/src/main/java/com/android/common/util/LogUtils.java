package com.android.common.util;

import android.util.Log;

import com.android.common.library.BuildConfig;

public class LogUtils {

    private static final String TAG = "LogUtil";

    public static int VERBOSE = 1;
    public static int DEBUG = 2;
    public static int INFO = 3;
    public static int WARN = 4;
    public static int ERROR = 5;
    private static int level = VERBOSE;


    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isPrintLog() && level <= INFO)
            Log.i(tag, msg);
    }

    public static void i(String msg, Throwable tr) {
        i(TAG, msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isPrintLog() && level <= INFO)
            Log.i(tag, msg, tr);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isPrintLog() && level <= DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String msg, Throwable tr) {
        d(TAG, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isPrintLog() && level <= DEBUG)
            Log.d(tag, msg, tr);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isPrintLog() && level <= WARN)
            Log.w(tag, msg);
    }

    public static void w(String msg, Throwable tr) {
        w(TAG, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isPrintLog() && level <= WARN)
            Log.w(tag, msg, tr);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isPrintLog() && level <= ERROR)
            Log.e(tag, msg);
    }

    public static void e(String msg, Throwable tr) {
        e(TAG, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isPrintLog() && level <= ERROR)
            Log.e(tag, msg, tr);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isPrintLog() && level <= VERBOSE)
            Log.v(tag, msg);
    }

    public static void v(String msg, Throwable tr) {
        v(TAG, msg, tr);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isPrintLog() && level <= VERBOSE)
            Log.v(tag, msg, tr);
    }

    public static boolean isPrintLog() {
        return BuildConfig.DEBUG;
    }

}
