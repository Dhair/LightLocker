package com.dhair.light.locker.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dhair.light.locker.component.cp.PreferenceType;
import com.dhair.light.locker.component.cp.ProviderCursorUtil;
import com.dhair.light.locker.component.cp.UContentProvider;

/**
 * Created by dengshengjin on 16/5/12.
 */
public class MultiProcessConfigManager {
    private Context mContext;

    public MultiProcessConfigManager(Context context) {
        mContext = context;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String def) {
        Uri uri = getContentUri(key, PreferenceType.STRING_TYPE);
        if (uri == null) {
            return def;
        }
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        return ProviderCursorUtil.getStringValue(cursor, def);
    }

    public long getLong(String key) {
        return getLong(key, 0l);
    }

    public long getLong(String key, long def) {
        Uri uri = getContentUri(key, PreferenceType.LONG_TYPE);
        if (uri == null) {
            return def;
        }
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        return ProviderCursorUtil.getLongValue(cursor, def);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float def) {
        Uri uri = getContentUri(key, PreferenceType.FLOAT_TYPE);
        if (uri == null) {
            return def;
        }
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        return ProviderCursorUtil.getFloatValue(cursor, def);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        Uri uri = getContentUri(key, PreferenceType.BOOLEAN_TYPE);
        if (uri == null) {
            return def;
        }
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        return ProviderCursorUtil.getBooleanValue(cursor, def);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        Uri uri = getContentUri(key, PreferenceType.INT_TYPE);
        if (uri == null) {
            return def;
        }
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        return ProviderCursorUtil.getIntValue(cursor, def);
    }

    public void putString(String key, String value) {
        ContentValues values = new ContentValues();
        values.put(key, value);
        commit(values);
    }

    public void putLong(String key, long value) {
        ContentValues values = new ContentValues();
        values.put(key, value);
        commit(values);
    }

    public void putFloat(String key, float value) {
        ContentValues values = new ContentValues();
        values.put(key, value);
        commit(values);
    }

    public void putBoolean(String key, boolean value) {
        ContentValues values = new ContentValues();
        values.put(key, value);
        commit(values);
    }

    public void putInt(String key, int value) {
        ContentValues values = new ContentValues();
        values.put(key, value);
        commit(values);
    }

    private void commit(ContentValues values) {
        mContext.getContentResolver().insert(getContentUri(UContentProvider.UContentProviderIntent.KEY, UContentProvider.UContentProviderIntent.TYPE), values);
    }


    private static final Uri getContentUri(String key, String type) {
        if (UContentProvider.getUri() == null) {
            return null;
        }
        return UContentProvider.getUri().buildUpon().appendPath(UContentProvider.UContentProviderIntent.PREFERENCE).appendPath(key).appendPath(type).build();
    }

}
