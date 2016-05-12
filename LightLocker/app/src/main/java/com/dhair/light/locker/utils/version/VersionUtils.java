package com.dhair.light.locker.utils.version;

import android.content.Context;

import com.dhair.light.locker.BuildConfig;
import com.dhair.light.locker.data.local.ServiceConfigManager;

/**
 * Created by dengshengjin on 16/5/12.
 */
public class VersionUtils {
    public static void updateVersion(Context context) {
        ServiceConfigManager serviceConfigManager = ServiceConfigManager.getInstance(context);
        int currentVersion = serviceConfigManager.getCurrentVersion();
        if (currentVersion == 0) {//第一次安装
            serviceConfigManager.setPreVersion(0);
            serviceConfigManager.updateCurrentVersion();
        } else if (currentVersion != BuildConfig.VERSION_CODE) {//覆盖安装
            serviceConfigManager.setPreVersion(currentVersion);
            serviceConfigManager.updateCurrentVersion();
        }
    }
}
