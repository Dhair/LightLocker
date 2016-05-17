package com.dhair.light.locker.utils.process;

/**
 * Created by dengshengjin on 16/5/13.
 */
public class RuntimeUtils {
    private static final String SERVICE_PROCESS = ":service";
    private static boolean mIsServiceProcess = false;
    private static boolean mIsUIProcess = false;

    public static void updateProcess(String processName) {
        if (processName.endsWith(SERVICE_PROCESS)) {
            mIsServiceProcess = true;
        } else {
            mIsUIProcess = true;
        }
    }

    public static boolean isServiceProcess() {
        return mIsServiceProcess;
    }

    public static boolean isUIProcesss() {
        return mIsUIProcess;
    }
}
