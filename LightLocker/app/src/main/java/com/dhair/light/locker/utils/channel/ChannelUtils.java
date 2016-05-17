package com.dhair.light.locker.utils.channel;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.dhair.light.locker.data.local.ServiceConfigManager;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by dengshengjin on 16/5/13.
 */
public class ChannelUtils {

    private static String mChannel;

    public static void initChannel(Context context) {
        getChannel(context);
    }

    public static String getChannel(Context context) {
        return getChannel(context, "default");
    }

    public static String getChannel(Context context, String defaultChannel) {
        if (!TextUtils.isEmpty(mChannel)) {
            return mChannel;
        }
        String localChannel = getChannelFromLocal(context);
        if (!TextUtils.isEmpty(localChannel)) {
            mChannel = localChannel;
            return localChannel;
        }
        String appChannel = getChannelFromApp(context);
        if (!TextUtils.isEmpty(appChannel)) {
            mChannel = appChannel;
            ServiceConfigManager.getInstance(context).setChannel(appChannel);
            return appChannel;
        }
        return defaultChannel;
    }

    private static String getChannelFromLocal(Context context) {
        return ServiceConfigManager.getInstance(context).getChannel();
    }

    private static String getChannelFromApp(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String sourceDir = applicationInfo.sourceDir;
        String key = "META-INF/channel";
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key) && !entryName.contains("default")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        String channel = "";
        if (split != null && split.length >= 2) {
            channel = ret.substring(split[0].length() + 1);
        }
        return channel;
    }
}
