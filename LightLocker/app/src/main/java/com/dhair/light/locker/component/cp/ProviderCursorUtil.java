package com.dhair.light.locker.component.cp;

import android.database.Cursor;


public class ProviderCursorUtil {

    public static String getStringValue(Cursor cursor, String def) {
        String ret = def;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ret = cursor.getString(0);
            }

            cursor.close();
        }

        return ret;
    }

    public static int getIntValue(Cursor cursor, int def) {
        int ret = def;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ret = cursor.getInt(0);
            }

            cursor.close();
        }

        return ret;
    }

    public static float getFloatValue(Cursor cursor, float def) {
        float ret = def;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ret = cursor.getFloat(0);
            }

            cursor.close();
        }

        return ret;
    }

    public static long getLongValue(Cursor cursor, long def) {
        long ret = def;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ret = cursor.getLong(0);
            }

            cursor.close();
        }

        return ret;
    }

    public static boolean getBooleanValue(Cursor cursor, boolean def) {
        boolean ret = def;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ret = cursor.getInt(0) > 0;
            }

            cursor.close();
        }

        return ret;
    }
}
