package com.android.ui.utils;

import java.util.Calendar;

/**
 * Created by dengshengjin on 16/5/17.
 */
public class DoubleInvalidateUtils {
    private static final int MIN_INTERVAL_TIME = 15;
    private int mMinIntervalTime;
    private long mLastUpdateTime;

    public DoubleInvalidateUtils() {
        this(MIN_INTERVAL_TIME);
    }

    public DoubleInvalidateUtils(int minIntervalTime) {
        mMinIntervalTime = minIntervalTime;
    }

    public synchronized void update(boolean needForce) {
        if (needForce) {
            onInvalidate();
            return;
        }
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - mLastUpdateTime <= mMinIntervalTime) {
            return;
        }
        mLastUpdateTime = currentTime;
        onInvalidate();
    }

    protected void onInvalidate() {

    }
}
