package com.dhair.light.locker.data.local;

import android.content.Context;

/**
 * Created by dengshengjin on 16/5/12.
 */
public class UIConfigManager {
    private Context mContext;
    private static UIConfigManager mConfigManager;

    private UIConfigManager(Context context) {
        mContext = context;
    }

    public static UIConfigManager getInstance(Context context) {
        if (mConfigManager == null) {
            synchronized (UIConfigManager.class) {
                if (mConfigManager == null) {
                    mConfigManager = new UIConfigManager(context);
                }
            }
        }
        return mConfigManager;
    }



}
