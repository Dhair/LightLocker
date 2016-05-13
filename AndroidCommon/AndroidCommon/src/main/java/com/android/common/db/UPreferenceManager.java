package com.android.common.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

/**
 * Created by dengshengjin on 16/5/12.
 */
public class UPreferenceManager {
    private SharedPreferences mSharedPreferences;

    public UPreferenceManager(Context context) {
        this(context, getDefaultSharedPreferencesName(context));
    }

    public UPreferenceManager(Context context, String name) {
        this(context, name, getDefaultSharedPreferencesMode());
    }

    public UPreferenceManager(Context context, String name, int mode) {
        mSharedPreferences = context.getSharedPreferences(name, mode);
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return Context.MODE_PRIVATE;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        commit(mEditor);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        commit(mEditor);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putFloat(key, value);
        commit(mEditor);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int intValue) {
        return mSharedPreferences.getInt(key, intValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        commit(mEditor);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
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

    public void remove(String key) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        commit(mEditor);
    }

    private void commit(SharedPreferences.Editor mEditor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mEditor.apply();//开启了新线程
        } else {
            mEditor.commit();//开启了新线程
        }
    }

    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

}
