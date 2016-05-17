package com.android.common.crash;

import android.content.Context;

/**
 * Created by dengshengjin on 16/5/17.
 */
public interface CrashReporter {
    void report(Context context);
}
