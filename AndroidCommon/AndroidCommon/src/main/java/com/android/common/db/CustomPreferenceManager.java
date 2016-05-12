package com.android.common.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by dengshengjin on 16/5/12.
 */
public class CustomPreferenceManager {
    private SharedPreferences mSharedPreferences;

    public CustomPreferenceManager(Context context) {
        this(context, getDefaultSharedPreferencesName(context));
    }

    public CustomPreferenceManager(Context context, String name) {
        this(context, name, getDefaultSharedPreferencesMode());
    }

    public CustomPreferenceManager(Context context, String name, int mode) {
        mSharedPreferences = context.getSharedPreferences(name, mode);
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return Context.MODE_PRIVATE;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        commit(mEditor);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        commit(mEditor);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putFloat(key, value);
        commit(mEditor);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        commit(mEditor);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        commit(mEditor);
    }

    public void clear() {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        commit(mEditor);
    }

    public void removeKey(String key) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        commit(mEditor);
    }

    private void commit(SharedPreferences.Editor mEditor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mEditor.apply();
        } else {
            mEditor.commit();//开启了新线程
        }
    }

}
