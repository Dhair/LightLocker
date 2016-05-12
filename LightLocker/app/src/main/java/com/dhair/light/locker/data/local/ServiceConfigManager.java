package com.dhair.light.locker.data.local;

import android.content.Context;

import com.dhair.light.locker.BuildConfig;

/**
 * Created by dengshengjin on 16/5/12.
 */
public class ServiceConfigManager {
    private static ServiceConfigManager mConfigManager;
    private MultiProcessConfigManager mUPreferenceManager;

    private static final String PRE_VERSION = "PreVersion";
    private static final String CURRENT_VERSION = "CurrVersion";

    private ServiceConfigManager(Context context) {
        mUPreferenceManager = new MultiProcessConfigManager(context);
    }

    public static ServiceConfigManager getInstance(Context context) {
        if (mConfigManager == null) {
            synchronized (UIConfigManager.class) {
                if (mConfigManager == null) {
                    mConfigManager = new ServiceConfigManager(context);
                }
            }
        }
        return mConfigManager;
    }

    public int getCurrentVersion() {
        return mUPreferenceManager.getInt(CURRENT_VERSION);
    }

    public void updateCurrentVersion() {
        mUPreferenceManager.putInt(CURRENT_VERSION, BuildConfig.VERSION_CODE);
    }

    public int getPreVersion() {
        return mUPreferenceManager.getInt(PRE_VERSION);
    }

    public void setPreVersion(int version) {
        mUPreferenceManager.putInt(PRE_VERSION, version);
    }
}
